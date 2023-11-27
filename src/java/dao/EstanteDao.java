
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Estante;

public class EstanteDao {
    private Connection conn;

    public EstanteDao(Connection conn) {
        this.conn = conn;
    }

    public EstanteDao() {
    }
    
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    
    
    
    public void createConjunto(long id) {
        String sql = "INSERT INTO `tb_conjunto`(`ID_CONJUNTO`) VALUES (?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EstanteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String buscarConjunto(long id) {
        String sql = "SELECT `ID_CONJUNTO`, `nome_CATEGORIAS` FROM `tb_conjunto` LEFT JOIN `tb_categorias` ON `tb_CATEGORIAS_ID_CATEGORIAS` = `ID_CATEGORIAS` WHERE `ID_CONJUNTO` = ?";
        String conjCategorias=null;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.first()) {
                conjCategorias = rs.getString("nome_CATEGORIAS");
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstanteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conjCategorias;
    }
    
    
    public Map<Long, String> listarConjunto() {
        String sql = "SELECT `ID_CONJUNTO`, `nome_CATEGORIAS` FROM `tb_conjunto` LEFT JOIN `tb_categorias` ON `tb_CATEGORIAS_ID_CATEGORIAS` = `ID_CATEGORIAS`";
        Map<Long, String> conjunto = new HashMap<>();
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                conjunto.put(rs.getLong("ID_CONJUNTO"), rs.getString("nome_CATEGORIAS"));
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EstanteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conjunto;
    }
    
    
    public boolean create(Estante estante) {
        String sql = "INSERT INTO `tb_estante`(`tb_CONJUNTO_ID_CONJUNTO`, `desclocal_ESTANTE`) VALUES (?,?)";
        boolean sucess=false;
              
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            boolean isconj = buscarConjunto(estante.getConjuntoID()) != null;
            if(!isconj) createConjunto(estante.getConjuntoID());
            
            stmt.setLong(1, estante.getConjuntoID());
            stmt.setString(2, estante.getLocal());
            sucess = stmt.executeUpdate() > 0;
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EstanteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sucess;
    }    
    
    public Estante buscar(long id) {
        String sql = "SELECT `tb_CONJUNTO_ID_CONJUNTO`, `desclocal_ESTANTE` FROM `tb_estante` WHERE `ID_ESTANTE` = ?";
        Estante estante = null;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.first()) {
                estante = new Estante(
                        id, 
                        rs.getLong("tb_CONJUNTO_ID_CONJUNTO"),
                        rs.getString("desclocal_ESTANTE")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return estante;
    }
    
    
    public List<Estante> list() {
        String sql = "SELECT `ID_ESTANTE`, `tb_CONJUNTO_ID_CONJUNTO`, `desclocal_ESTANTE` FROM `tb_estante`";
        List<Estante> lista = new ArrayList<>();
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Estante estante = new Estante(
                        rs.getLong("ID_ESTANTE"), 
                        rs.getLong("tb_CONJUNTO_ID_CONJUNTO"),
                        rs.getString("desclocal_ESTANTE")
                );
                
                lista.add(estante);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstanteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    
    public boolean update(Estante estante) {
        String sql = "UPDATE `tb_estante` SET `tb_CONJUNTO_ID_CONJUNTO`=?,`desclocal_ESTANTE`=? WHERE `ID_ESTANTE` = ?";
        boolean result=false;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, estante.getConjuntoID());
            stmt.setString(2, estante.getLocal());
            stmt.setLong(3, estante.getID());
            
            result = stmt.executeUpdate() > 0;
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EstanteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
