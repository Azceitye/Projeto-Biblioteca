
package logica;

import dao.BibliotecariaDao;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ConnectionFactory;
import model.bean.Bibliotecaria;


public class BibliotecariaLogic extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
        boolean result=false;
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            BibliotecariaDao dao = new BibliotecariaDao(new ConnectionFactory().getConnection());
            Bibliotecaria bibliotecaria = null;
            
            String nome = request.getParameter("biblioNome");
            String instituicao = request.getParameter("biblioInstituicao");
            long conjuntoID = Long.parseLong(request.getParameter("biblioConjunto"));
            String nivel = request.getParameter("biblioNivel");
            Long responsavel = null;
            if(nivel.toUpperCase().equals("ESTAGIO")) 
                responsavel = Long.valueOf(request.getParameter("biblioResp"));
            
            switch(action) {
                case "create":
                    bibliotecaria = new Bibliotecaria(
                            responsavel,
                            conjuntoID,
                            nome,
                            nivel,
                            instituicao
                    );
                    
                    result = dao.create(bibliotecaria);
                    break;
                case "update":
                    long ID = Long.parseLong(request.getParameter("biblioID"));
                    bibliotecaria = new Bibliotecaria(
                            ID,
                            responsavel,
                            conjuntoID,
                            nome,
                            nivel,
                            instituicao
                    );
                    
                    result = dao.update(bibliotecaria);
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } 
        
        if(result) {
            RequestDispatcher rd = request.getRequestDispatcher("/resultado.jsp");
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
