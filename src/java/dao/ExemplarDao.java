
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Exemplar;

public class ExemplarDao {
    private Connection conn;
    
    public ExemplarDao() {}
    
    public ExemplarDao(Connection conn) {
        this.conn = conn;
    }
    
    public boolean create(Exemplar exemplar) {
        String sql = "INSERT INTO `tb_exemplar`(`ISBN_EXEMPLAR`, `tb_ESTANTE_ID_ESTANTE`, `tb_LIVRO_ID_LIVRO`, `status_LIVRO`) VALUES (?,?,?,?')";
        boolean sucess=false;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, exemplar.getISBN());
            stmt.setLong(2, exemplar.getEstanteID());
            stmt.setLong(3, exemplar.getLivroID());
            stmt.setString(4, exemplar.getStatus());
            
            sucess = stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sucess;
    }
}
