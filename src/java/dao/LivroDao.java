
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.bean.Livro;

public class LivroDao {
    
    private Connection conn;

    public LivroDao() {        
    }
    
    public LivroDao(Connection conn) {
        this.conn = conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    
    
    public boolean create(Livro livro) {
        String sql = "INSERT INTO `tb_livro` (`tb_EDITOR_ID_EDITOR`, `tb_AUTOR_ID_AUTOR`, `titulo_LIVRO`, `subtitulo_LIVRO`, `edicao_LIVRO`, `datapublic_LIVRO`) VALUES (?, ?, ?, ?, ?, ?)";
        boolean result=false;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            long ID_Autor = new AutorDao(conn).create(livro.getAutor());
            long ID_Editora = new EditoraDao(conn).create(livro.getEditora());
            
            
            stmt.setLong(1, ID_Editora);
            stmt.setLong(2, ID_Autor);
            stmt.setString(3, livro.getTitulo());
            stmt.setString(4, livro.getSubtitulo());
            stmt.setString(5, livro.getEdicao());
            stmt.setDate(6, new Date(livro.getDatapublic().getTime()));
            
            int row = stmt.executeUpdate();
            result = (row > 0);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(LivroDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return result;
    }
    
    public List<Livro> getLista() {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM `tb_livro` JOIN tb_autor ON `tb_AUTOR_ID_AUTOR` = ID_AUTOR JOIN tb_editor ON `tb_EDITOR_ID_EDITOR` = ID_EDITOR";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Date dateSql = rs.getDate("datapublic_LIVRO");
                
                Livro livro = new Livro(
                        rs.getLong("ID_LIVRO"),
                        rs.getString("titulo_LIVRO"),
                        rs.getString("subtitulo_LIVRO"),
                        rs.getString("edicao_LIVRO"),
                        rs.getString("nome_AUTOR"),
                        rs.getString("nome_EDITOR"),
                        dateSql
                );
                
                lista.add(livro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LivroDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public Livro buscar(long id) {
        Livro livro = null;
        String sql = "SELECT * FROM `tb_livro` JOIN tb_autor ON `tb_AUTOR_ID_AUTOR` = ID_AUTOR JOIN tb_editor ON `tb_EDITOR_ID_EDITOR` = ID_EDITOR WHERE ID_LIVRO = ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.first()) {
                Date dateSql = rs.getDate("datapublic_LIVRO");
                
                livro = new Livro(
                        rs.getLong("ID_LIVRO"),
                        rs.getString("titulo_LIVRO"),
                        rs.getString("subtitulo_LIVRO"),
                        rs.getString("edicao_LIVRO"),
                        rs.getString("nome_AUTOR"),
                        rs.getString("nome_EDITOR"),
                        dateSql
                );
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(LivroDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return livro;
    }
    
    
    public boolean update(Livro livro) {
        String sql = "UPDATE `tb_livro` SET `titulo_LIVRO`=?,`subtitulo_LIVRO`=?,`edicao_LIVRO`=?,`tb_AUTOR_ID_AUTOR`=?,`tb_EDITOR_ID_EDITOR`=?,`datapublic_LIVRO`=? WHERE `ID_LIVRO` = ?";
        boolean result=false;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            long ID_Autor = new AutorDao(conn).create(livro.getAutor());
            long ID_Editora = new EditoraDao(conn).create(livro.getEditora());
            
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getSubtitulo());
            stmt.setString(3, livro.getEdicao());
            stmt.setLong(4, ID_Editora);
            stmt.setLong(5, ID_Autor);
            stmt.setDate(6, new Date(livro.getDatapublic().getTime()));
            stmt.setLong(7, livro.getID());
            
            stmt.execute();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(LivroDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public void delete(long id) {
        String sql = "DELETE FROM `tb_livro` WHERE `ID_LIVRO` = ? AND NOT EXISTS (SELECT 1 FROM `tb_exemplar` WHERE `tb_LIVRO_ID_LIVRO` = ?)";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.setLong(2, id);
            stmt.execute();
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
