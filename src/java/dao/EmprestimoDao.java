
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Emprestimo;

public class EmprestimoDao {
    private Connection conn;
    
    public EmprestimoDao() {}
    
    public EmprestimoDao(Connection conn) {
        this.conn = conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    
    public void createItem(long id, String ISBN) {
        String sql = "INSERT INTO `tb_emprestimo_item`(`tb_EMPRESTIMO_ID_EMPRESTIMO`, `tb_EXEMPLAR_ISBN_EXEMPLAR`) VALUES (?, ?)";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.setString(2,ISBN);
            int row = stmt.executeUpdate();
            
            if(row > 0) {
                String lsql = "UPDATE `tb_exemplar` SET `status_LIVRO`=? WHERE `ISBN_EXEMPLAR`=?";
                try(PreparedStatement lstmt = conn.prepareStatement(lsql)) {
                    lstmt.setString(1, "Indisponivel");
                    lstmt.setString(2, sql);
                    lstmt.execute();
                    lstmt.close();
                }
            }
            
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean create(Emprestimo emprestimo) {
        String sql = "INSERT INTO `tb_emprestimo`(`tb_CLIENTE_ID_CLIENTE`, `tb_BIBLIOTECARIA_ID_BIBLIOTECARIA`, `datadevolucao_EMPRESTIMO`, `valdiamulta_EMPRESTIMO`, `status_EMPRESTIMO`) VALUES (?, ?, ?, ?, ?)";
        boolean result=false;
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, emprestimo.getClienteID());
            stmt.setLong(2, emprestimo.getBiblioID());
            stmt.setDate(3, new Date(emprestimo.getDatadevolucao().getTime()));
            stmt.setDouble(4, emprestimo.getValmultadiaria());
            stmt.setString(5, emprestimo.getStatus());
            int rows = stmt.executeUpdate();
            if(rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    long id = rs.getLong(1);
                    for(String isbn : emprestimo.getlistItensISBN()) {
                        createItem(id, isbn);
                    }
                    result=true;
                }
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    
    public List<String> listItens(long empID) {
        String sql = "SELECT `tb_EXEMPLAR_ISBN_EXEMPLAR` FROM `tb_emprestimo_item` WHERE `tb_EMPRESTIMO_ID_EMPRESTIMO` = ?";
        List<String> exemplares = new ArrayList<>();
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, empID);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                exemplares.add(rs.getString("tb_EXEMPLAR_ISBN_EXEMPLAR"));
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exemplares;
    }
    
    public List<Emprestimo> list(long cliID) {
        String sql = "SELECT `ID_EMPRESTIMO`, `tb_BIBLIOTECARIA_ID_BIBLIOTECARIA`, `datadevolucao_EMPRESTIMO`, `datareg_EMPRESTIMO`, `valdiamulta_EMPRESTIMO`, `status_EMPRESTIMO` FROM `tb_emprestimo` WHERE `tb_CLIENTE_ID_CLIENTE` = ?";
        List<Emprestimo> emp = new ArrayList<>();
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, cliID);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                long idEmp = rs.getLong("ID_EMPRESTIMO");
                List<String> exemplares = listItens(idEmp);
                emp.add( new Emprestimo(
                        idEmp,
                        cliID,
                        rs.getLong("tb_BIBLIOTECARIA_ID_BIBLIOTECARIA"),
                        rs.getDate("datadevolucao_EMPRESTIMO"),
                        rs.getDate("datareg_EMPRESTIMO"),
                        rs.getDouble("valdiamulta_EMPRESTIMO"),
                        rs.getString("status_EMPRESTIMO"),
                        exemplares
                ));
            }
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return emp;
    }
    
    public Emprestimo buscar(long id) {
        String sql = "SELECT `tb_CLIENTE_ID_CLIENTE`, `tb_BIBLIOTECARIA_ID_BIBLIOTECARIA`, `datadevolucao_EMPRESTIMO`, `datareg_EMPRESTIMO`, `valdiamulta_EMPRESTIMO`, `status_EMPRESTIMO` FROM `tb_emprestimo` WHERE `ID_EMPRESTIMO` = ?";
        Emprestimo emp = null;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.first()) {
                List<String> exemplares = listItens(id);
                emp = new Emprestimo(
                        id,
                        rs.getLong("tb_CLIENTE_ID_CLIENTE"),
                        rs.getLong("tb_BIBLIOTECARIA_ID_BIBLIOTECARIA"),
                        rs.getDate("datadevolucao_EMPRESTIMO"),
                        rs.getDate("datareg_EMPRESTIMO"),
                        rs.getDouble("valdiamulta_EMPRESTIMO"),
                        rs.getString("status_EMPRESTIMO"),
                        exemplares
                );
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return emp;
    }
    
    public boolean update(Emprestimo emp) {
        String sql = "UPDATE `tb_emprestimo` SET `datadevolucao_EMPRESTIMO` = ?, `valdiamulta_EMPRESTIMO` = ?, `status_EMPRESTIMO` = ? WHERE `ID_EMPRESTIMO` = ?";
        boolean result=false;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new Date(emp.getDatadevolucao().getTime()));
            stmt.setDouble(2, emp.getValmultadiaria());
            stmt.setString(3, emp.getStatus());
            stmt.setLong(4, emp.getID());
            if(!emp.getlistItensISBN().isEmpty() && emp.getlistItensISBN() != null) {
                for(String isbn : emp.getlistItensISBN()) {
                    createItem(emp.getID(), isbn);
                }
            }
            result = stmt.executeUpdate() > 0;
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    
    public boolean deleteRow(String ISBN, long empID) {
        String sql = "DELETE FROM `tb_emprestimo_item` WHERE `tb_EMPRESTIMO_ID_EMPRESTIMO` = ? AND `tb_EXEMPLAR_ISBN_EXEMPLAR` = ?";
        boolean result=false;
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, empID);
            stmt.setString(2, ISBN);
            result = stmt.execute();
            
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
