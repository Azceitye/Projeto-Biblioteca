
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Autor;

public class AutorDao {
    
    private Connection conn;

    public AutorDao(Connection conn) {
        this.conn = conn;
    }
    
    
    
    public long create(String nome) {
        Autor aux = buscar(nome);
        long id=0;
        
        if(aux != null) 
            id = aux.getID();
        else {
            String sql = "INSERT INTO `tb_autor`(`nome_AUTOR`) VALUES (?)";
            
            try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nome);
                int rows = stmt.executeUpdate();
                if(rows > 0) {
                    id = stmt.getGeneratedKeys().getLong(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AutorDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }

    public Autor buscar(String nome) {
        String sql = "SELECT `ID_AUTOR`, `nome_AUTOR` FROM `tb_autor` WHERE UPPER(`nome_AUTOR`) = ?";
        Autor autor = null;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome.toUpperCase());
            ResultSet rs = stmt.executeQuery();
            
            if(rs.first()) {
                long ID = rs.getLong("ID_AUTOR");
                autor = new Autor(ID, nome);
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AutorDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return autor;
    }
    
    public void delete(long id) {
        String sql = "SELECT `nome_AUTOR` FROM `tb_autor` LEFT JOIN `tb_livro` ON `ID_AUTOR` = `tb_autor_ID_AUTOR` WHERE `ID_AUTOR` = 1";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(!rs.first()) {
                String dsql = "DELETE FROM `tb_autor` WHERE `ID_AUTOR` = ?";
                try(PreparedStatement dstmt = conn.prepareStatement(dsql)) {
                    dstmt.setLong(1, id);
                    dstmt.execute();
                    
                    dstmt.close();
                }
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
