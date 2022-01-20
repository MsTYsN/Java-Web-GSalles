package controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Admin;
import service.AdminService;

/**
 * Servlet implementation class AdminController
 */
@WebServlet(name = "AdminController", urlPatterns = {"/admin/AdminController"})
public class AdminController extends HttpServlet {

    AdminService as = new AdminService();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger bi = new BigInteger(1, md.digest(s.getBytes()));
            return bi.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("op") != null) {
            if (request.getParameter("op").equals("signup")) {
                String email = request.getParameter("email");
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String password = request.getParameter("password");
                Date dateNaissance = new Date(request.getParameter("dateNaissance").replace("-", "/"));
                as.create(new Admin(email, password, nom, prenom, dateNaissance));
                response.setContentType("text/plain");
                response.getWriter().write("1");
            } else if (request.getParameter("op").equals("login")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                Admin a = as.findByEmail(email);
                response.setContentType("text/plain");
                if (a != null) {
                    if (a.getPassword().equals(MD5(password))) {
                    	HttpSession session = request.getSession(false);
                    	session.setAttribute("admin", a.getId());
                        response.getWriter().write("1");
                    }else {
                        response.getWriter().write("2");
                    }
                }else {
                    response.getWriter().write("2");
                }
            } else if (request.getParameter("op").equals("disconnect")) {
            	request.getSession(false).invalidate();
            	response.setContentType("text/plain");
            	response.getWriter().write("1");
            }
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
