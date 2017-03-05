/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ErrorException;
import modelo.Estudiante;
import modelo.Modalidad;
import modelo.Propuesta;
import modelo.TrabajoGrado;

/**
 *
 * @author daniel
 */
public class RegistrarPropuestaServlet extends HttpServlet {

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
            throws ServletException, IOException {
       int modalidad = Integer.parseInt(request.getParameter("selModalidad"));
       String tematica = request.getParameter("txtTematica"),
               archivo = request.getParameter("filePropuesta");
       ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
       int i = 1;
       String cod = request.getParameter("txtCodigo"+i);
       Servicios s = new Servicios();
       while(cod!=null){
           estudiantes.add(new Estudiante(Long.parseLong(cod)));
           i++;
           cod = request.getParameter("txtCodigo"+i);
       }
       Modalidad m = s.buscarModalidad(modalidad);
       TrabajoGrado trabajo = new TrabajoGrado(s.nextIdTrabajo(), m, tematica);
       Propuesta propuesta = new Propuesta(trabajo, new File(archivo));
       propuesta.setEstudiantes(estudiantes);
       try{
           Integer agrego=s.agregarPropuesta(propuesta);
           request.setAttribute("agrego", agrego);
       }catch(ErrorException e){
           request.setAttribute("error", e.getMessage());
       }
        RequestDispatcher dispatcher = request.getRequestDispatcher("PrepararRegistrarPropuesta");
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
