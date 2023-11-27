/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package logica;

import dao.EmprestimoDao;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ConnectionFactory;
import model.bean.Emprestimo;


public class EmprestimoLogic extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            EmprestimoDao dao = new EmprestimoDao(new ConnectionFactory().getConnection());
            Emprestimo emprestimo = null;            
            boolean result = false;
            
            Long empID = null;
            String empIDSTR = request.getParameter("empID");
            if(empIDSTR != null) empID = Long.valueOf(empIDSTR);
            
            Long clienteID = null;
            String clienteIDSTR = request.getParameter("clienteEmprestimo");
            if(clienteIDSTR != null) clienteID = Long.valueOf(clienteIDSTR);
            
            Long biblioID = null;
            String biblioIDSTR = request.getParameter("bibliotecariaEmprestimo");
            if(biblioIDSTR != null) biblioID = Long.valueOf(biblioIDSTR);
            
            String datedevolucao = request.getParameter("datedevolucEmprestimo");
            
            String valSTR = request.getParameter("valmultaEmprestimo");
            Double valmultadiaria = null;
            if(valSTR != null) valmultadiaria = Double.valueOf(valSTR);
            
            String status = request.getParameter("statusEmprestimo");
            String[] exemplaresArray = request.getParameterValues("exemplarEmprestimo");
            List<String> exemplaresISBN = exemplaresArray != null ? Arrays.asList(exemplaresArray) : new ArrayList<>();
            
            
            
            switch(action) {
                case "create":
                    emprestimo = new Emprestimo(
                            clienteID,
                            biblioID,
                            formatDate.parse(datedevolucao),
                            valmultadiaria,
                            status,
                            exemplaresISBN
                    );
                    
                    dao.create(emprestimo);
                    break;
                case "update":
                    String datareg = request.getParameter("datareg");
                    
                    emprestimo = new Emprestimo(
                            empID,
                            clienteID,
                            biblioID,
                            formatDate.parse(datedevolucao),
                            formatDate.parse(datareg),
                            valmultadiaria,
                            status,
                            exemplaresISBN
                    );
                    
                    dao.update(emprestimo);
                    break;
                case "delete-row":
                    dao.deleteRow(request.getParameter("isbn"), empID);
            }
            
            if(result) {
                RequestDispatcher rd = request.getRequestDispatcher("/resultado.jsp");
                rd.forward(request,response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(EmprestimoLogic.class.getName()).log(Level.SEVERE, null, ex);
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
