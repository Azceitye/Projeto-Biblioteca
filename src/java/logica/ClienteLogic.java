/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package logica;

import dao.ClienteDao;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ConnectionFactory;
import model.bean.Cliente;


public class ClienteLogic extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        long result=0;
        String action = request.getParameter("action");
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            ClienteDao dao = new ClienteDao(new ConnectionFactory().getConnection());
            Cliente cliente = null;
            
            String nome = request.getParameter("clienteNome");
            String datanasc = request.getParameter("clienteDatenasc");
            
            switch(action) {
                case "create":
                    cliente = new Cliente(nome, formatDate.parse( datanasc));
                    result = dao.create(cliente);
                    break;
                case "update":
                    long ID = Long.parseLong(request.getParameter("clienteID"));
                    cliente = new Cliente(ID, nome, formatDate.parse( datanasc));
                    result = dao.update(cliente) ? 1 : 0;
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ClienteLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        if(result>0) {
            RequestDispatcher rd = request.getRequestDispatcher("/cliente/perfil-cliente.jsp?id=" + result);
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