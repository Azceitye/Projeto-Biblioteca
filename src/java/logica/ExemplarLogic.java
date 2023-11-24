/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package logica;

import dao.ExemplarDao;
import dao.LivroDao;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ConnectionFactory;
import model.bean.Exemplar;

/**
 *
 * @author telva
 */
public class ExemplarLogic extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
        try {
            final Connection conn = new ConnectionFactory().getConnection();
            ExemplarDao dao = new ExemplarDao(conn);
            LivroDao daoLivro = new LivroDao(conn);
            boolean result = false;
            
            Exemplar exemplar = null;
            long livroID = Long.parseLong(request.getParameter("livroIDExemplar"));
            String ISBN = request.getParameter("isbnExemplar");
            long estante = Long.parseLong(request.getParameter("estanteExemplar"));
            String status = request.getParameter("statusExemplar");
            
            
            
            switch(action) {
                case "create":
                    if(daoLivro.buscar(livroID) != null) {
                        System.out.println(estante);
                        exemplar = new Exemplar(ISBN, estante, livroID, status);
                        result = dao.create(exemplar);
                    }
                    break;
            }
            
            if(result) {
                RequestDispatcher rd = request.getRequestDispatcher("/resultado.jsp");
                rd.forward(request,response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
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
