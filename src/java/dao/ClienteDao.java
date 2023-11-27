
package dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.bean.Cliente;

public class ClienteDao {
    private Connection conn;

    public ClienteDao() {
    }

    public ClienteDao(Connection conn) {
        this.conn = conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    
    public long create(Cliente cliente) {
        String sql = "INSERT INTO `tb_cliente`(`nome_CLIENTE`, `datanasc_CLIENTE`) VALUES (?, ?)";
        long result=0;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getNome());
            stmt.setDate(2, new Date(cliente.getDatanasc().getTime()));
            
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                result = rs.getLong(1);
                System.out.println("ID : " + result);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public boolean update(Cliente cliente) {
        String sql = "UPDATE `tb_cliente` SET `nome_CLIENTE`=?,`datanasc_CLIENTE`=? WHERE `ID_CLIENTE` = ?";
        boolean result=false;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setDate(2, new Date(cliente.getDatanasc().getTime()));
            stmt.setLong(3, cliente.getID());
            
            result = stmt.executeUpdate() > 0;
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    
    public Cliente buscar(long id) {
        String sql = "SELECT `ID_CLIENTE`, `nome_CLIENTE`, `datanasc_CLIENTE` FROM `tb_cliente` WHERE `ID_CLIENTE` = ?";
        Cliente cliente = null;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.first()) {
                cliente = new Cliente(
                        id,
                        rs.getString("nome_CLIENTE"),
                        rs.getDate("datanasc_CLIENTE")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cliente;
    }
    
    
    public List<Cliente> list() {
        String sql = "SELECT `ID_CLIENTE`, `nome_CLIENTE`, `datanasc_CLIENTE` FROM `tb_cliente`";
        List<Cliente> clientes = new ArrayList<>();
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                clientes.add( 
                    new Cliente(
                        rs.getLong("ID_CLIENTE"),
                        rs.getString("nome_CLIENTE"),
                        rs.getDate("datanasc_CLIENTE")
                    )
                );
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clientes;
    } 
}
