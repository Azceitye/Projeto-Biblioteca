
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Bibliotecaria;

public class BibliotecariaDao {
    private Connection conn;

    public BibliotecariaDao() {
    }
    
    public BibliotecariaDao(Connection conn) {
        this.conn = conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public boolean create(Bibliotecaria bibliotecaria) {
        String sql = "INSERT INTO `tb_bibliotecaria`(`tb_BIBLIOTECARIA_ID_BIBLIOTECARIA`, `tb_CONJUNTO_ID_CONJUNTO`, `nome_BIBLIOTECARIA`, `nivel_BIBLIOTECARIA`, `instituicao_BIBLIOTECARIA`) VALUES (?, ?, ?, ?, ?)";
        boolean result=false;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            if(bibliotecaria.getResponsavelID() != null) {
                stmt.setLong(1, bibliotecaria.getResponsavelID());
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }
            stmt.setLong(2, bibliotecaria.getConjuntoID());
            stmt.setString(3, bibliotecaria.getNome());
            stmt.setString(4, bibliotecaria.getNivel());
            stmt.setString(5, bibliotecaria.getInstituicao());
            
            result = stmt.executeUpdate() > 0;
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BibliotecariaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    
    public Bibliotecaria buscar(long id) {
        String sql = "SELECT `ID_BIBLIOTECARIA`, `tb_BIBLIOTECARIA_ID_BIBLIOTECARIA`, `tb_CONJUNTO_ID_CONJUNTO`, `nome_BIBLIOTECARIA`, `nivel_BIBLIOTECARIA`, `instituicao_BIBLIOTECARIA` FROM `tb_bibliotecaria` WHERE `ID_BIBLIOTECARIA` = ?";
        Bibliotecaria biblio = null;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.first()) {
                biblio = new Bibliotecaria(
                                rs.getLong("ID_BIBLIOTECARIA"),
                                rs.getLong("tb_BIBLIOTECARIA_ID_BIBLIOTECARIA"),
                                rs.getLong("tb_CONJUNTO_ID_CONJUNTO"),
                                rs.getString("nome_BIBLIOTECARIA"),
                                rs.getString("nivel_BIBLIOTECARIA"),
                                rs.getString("instituicao_BIBLIOTECARIA")
                        );
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BibliotecariaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return biblio;
    }
    
    public List<Bibliotecaria> list(String ignore) {
        List<Bibliotecaria> biblios = new ArrayList<>();
        String sql = "SELECT `ID_BIBLIOTECARIA`, `tb_BIBLIOTECARIA_ID_BIBLIOTECARIA`, `tb_CONJUNTO_ID_CONJUNTO`, `nome_BIBLIOTECARIA`, `nivel_BIBLIOTECARIA`, `instituicao_BIBLIOTECARIA` FROM `tb_bibliotecaria` ";
        if(!ignore.isBlank() && !ignore.isEmpty()) {
            sql += "WHERE UPPER(`nivel_BIBLIOTECARIA`) != ?";
        }
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            if(!ignore.isBlank() && !ignore.isEmpty()) {
                stmt.setString(1, ignore.toUpperCase());
            }
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                biblios.add(
                        new Bibliotecaria(
                                rs.getLong("ID_BIBLIOTECARIA"),
                                rs.getLong("tb_BIBLIOTECARIA_ID_BIBLIOTECARIA"),
                                rs.getLong("tb_CONJUNTO_ID_CONJUNTO"),
                                rs.getString("nome_BIBLIOTECARIA"),
                                rs.getString("nivel_BIBLIOTECARIA"),
                                rs.getString("instituicao_BIBLIOTECARIA")
                        )
                );
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BibliotecariaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return biblios;
    }
    
    public boolean update(Bibliotecaria bibliotecaria) {
        String sql = "UPDATE `tb_bibliotecaria` SET `tb_BIBLIOTECARIA_ID_BIBLIOTECARIA` = ?, `tb_CONJUNTO_ID_CONJUNTO` = ?, `nome_BIBLIOTECARIA` = ?, `nivel_BIBLIOTECARIA` = ?, `instituicao_BIBLIOTECARIA` = ? WHERE `ID_BIBLIOTECARIA` = ?";
        boolean result=false;
        
        try(PreparedStatement stmt = conn.prepareCall(sql)) {
            if (bibliotecaria.getResponsavelID() != null) {
                stmt.setLong(1, bibliotecaria.getResponsavelID());
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER); 
            }

            stmt.setLong(2, bibliotecaria.getConjuntoID());
            stmt.setString(3, bibliotecaria.getNome());
            stmt.setString(4, bibliotecaria.getNivel());
            stmt.setString(5, bibliotecaria.getInstituicao());
            stmt.setLong(6, bibliotecaria.getID());
            
            result = stmt.executeUpdate() > 0;
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BibliotecariaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
