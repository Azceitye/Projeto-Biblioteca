
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Estante;

public class EstanteDao {
    private Connection conn;

    public EstanteDao(Connection conn) {
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
    
    public boolean buscarConjunto(long id) {
        String sql = "SELECT `ID_CONJUNTO`, `tb_CATEGORIAS_ID_CATEGORIAS` FROM `tb_conjunto` WHERE `ID_CONJUNTO` = ? ";
        boolean conjunto=false;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.first()) conjunto = true;
        } catch (SQLException ex) {
            Logger.getLogger(EstanteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conjunto;
    }
    
    public boolean create(Estante estante) {
        String sql = "INSERT INTO `tb_estante`(`tb_CONJUNTO_ID_CONJUNTO`, `desclocal_ESTANTE`) VALUES (?,?)";
        boolean sucess=false;
              
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            boolean isconj = buscarConjunto(estante.getConjuntoID());
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
}
