
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Exemplar;

public class ExemplarDao {
    private Connection conn;
    
    public ExemplarDao() {}
    
    public ExemplarDao(Connection conn) {
        this.conn = conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public boolean create(Exemplar exemplar) {
        String sql = "INSERT INTO `tb_exemplar`(`ISBN_EXEMPLAR`, `tb_ESTANTE_ID_ESTANTE`, `tb_LIVRO_ID_LIVRO`, `status_LIVRO`) VALUES (?,?,?,?)";
        boolean sucess=false;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, exemplar.getISBN());
            stmt.setLong(2, exemplar.getEstanteID());
            stmt.setLong(3, exemplar.getLivroID());
            stmt.setString(4, exemplar.getStatus());
            
            sucess = stmt.executeUpdate() > 0;
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sucess;
    }
    
    public List<Exemplar> getLista(long LivroID) {
        String sql = "SELECT `ISBN_EXEMPLAR`, `tb_ESTANTE_ID_ESTANTE`, `tb_LIVRO_ID_LIVRO`, `status_LIVRO` FROM `tb_exemplar` WHERE `tb_LIVRO_ID_LIVRO` = ?";
        List<Exemplar> exemplares = new ArrayList<>();
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, LivroID);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                exemplares.add(
                        new Exemplar(
                            rs.getString("ISBN_EXEMPLAR"),
                            rs.getLong("tb_ESTANTE_ID_ESTANTE"),
                            LivroID,
                            rs.getString("status_LIVRO")
                    )
                );
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return exemplares;
    }
    
    
  
    
    public List<Exemplar> list(String status) {
        String sql = "SELECT `ISBN_EXEMPLAR`, `tb_ESTANTE_ID_ESTANTE`, `tb_LIVRO_ID_LIVRO`, `status_LIVRO` FROM `tb_exemplar` WHERE UPPER(`status_LIVRO`) = ? ";
        List<Exemplar> exemplares = new ArrayList<>();
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.toUpperCase());
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                exemplares.add(
                        new Exemplar(
                            rs.getString("ISBN_EXEMPLAR"),
                            rs.getLong("tb_ESTANTE_ID_ESTANTE"),
                            rs.getLong("tb_LIVRO_ID_LIVRO"),
                            rs.getString("status_LIVRO")
                    )
                );
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return exemplares;
    }
    
    public Exemplar buscar(String ISBN) {
        String sql = "SELECT `tb_ESTANTE_ID_ESTANTE`, `tb_LIVRO_ID_LIVRO`, `status_LIVRO` FROM `tb_exemplar` WHERE `ISBN_EXEMPLAR` = ?";
        Exemplar exemplar = null;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ISBN);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.first()) {
                exemplar = new Exemplar(
                        ISBN,
                        rs.getLong("tb_ESTANTE_ID_ESTANTE"),
                        rs.getLong("tb_LIVRO_ID_LIVRO"),
                        rs.getString("status_LIVRO")
                );
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exemplar;
    }
    
    
    public boolean update(String isbn, Exemplar exemplar) {
        String sql = "UPDATE tb_exemplar SET ISBN_EXEMPLAR = ?, tb_ESTANTE_ID_ESTANTE = ?, tb_LIVRO_ID_LIVRO = ?, status_LIVRO = ? WHERE ISBN_EXEMPLAR = ?";
        boolean result=false;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, exemplar.getISBN());
            stmt.setLong(2, exemplar.getEstanteID());
            stmt.setLong(3, exemplar.getLivroID());
            stmt.setString(4, exemplar.getStatus());
            stmt.setString(5, isbn);
            
            result = stmt.executeUpdate() > 0;            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    
    private void deleteLivro(long id) {
        String sql = "SELECT `ISBN_EXEMPLAR` FROM `tb_livro` LEFT JOIN `tb_exemplar` ON `ID_LIVRO` = `tb_LIVRO_ID_LIVRO` WHERE `ID_LIVRO` = ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.first()) {
                String dsql = "DELETE FROM `tb_livro` WHERE `ID_LIVRO` = ?";
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
    
    
    
    public boolean delete(String ISBN, long LivroID) {
        String sql = "DELETE FROM `tb_exemplar` WHERE `ISBN_EXEMPLAR` = ?";
        boolean result=false;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ISBN);
            result = stmt.execute();
            
            LivroDao daoLivro = new LivroDao(conn);
            daoLivro.delete(LivroID);
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
