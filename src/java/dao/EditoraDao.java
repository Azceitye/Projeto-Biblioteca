
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Editora;


public class EditoraDao {
    private Connection conn;

    public EditoraDao(Connection conn) {
        this.conn = conn;
    }

    public long create(String nome) {
        Editora aux = buscar(nome);
        long id=0;
        
        if(aux != null) 
            id = aux.getID();
        else {
            String sql = "INSERT INTO `tb_editor`(`nome_EDITOR`) VALUES (?)";
            
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
    
    public Editora buscar(String nome) {
        String sql = "SELECT `ID_EDITOR`, `nome_EDITOR` FROM `tb_editor` WHERE `nome_EDITOR` = ?";
        Editora edit = null;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.first()) {
                long ID = rs.getLong("ID_EDITOR");
                edit = new Editora(ID, nome);
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AutorDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return edit;
    }
}
