/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Estudiante;
import modelo.Persona;
import modelo.Programa;

/**
 *
 * @author daniel
 */
public class AgregarEstudianteServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            System.out.println("'" + request.getParameter("txtCodigo") + "'");

            Long codigo = Long.parseLong(request.getParameter("txtCodigo")),
                    identificacion = Long.parseLong(request.getParameter("txtIdentificacion"));
            String pNombre = request.getParameter("txtPNombre"),
                    sNombre = request.getParameter("txtSNombre"),
                    pApellido = request.getParameter("txtPApellido"),
                    sApellido = request.getParameter("txtSApellido"),
                    email = request.getParameter("txtEmail"),
                    programa = request.getParameter("selPrograma");

            Estudiante e = null;
            if (sNombre.isEmpty()) {
                e = new Estudiante(codigo, new Persona(identificacion, pNombre, pApellido, sApellido, email), new Programa(programa));
            } else {
                e = new Estudiante(codigo, new Persona(identificacion, pNombre, sNombre, pApellido, sApellido, email), new Programa(programa));
            }
            Servicios s = new Servicios();

            int agrego = s.agregarEstudiante(e);
            request.setAttribute("agrego", agrego);
        } catch (Exception e) {
            
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("PrepararAgregarEstudiante");
        dispatcher.forward(request, response);
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
