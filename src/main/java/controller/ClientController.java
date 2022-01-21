package controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.Client;
import service.ClientService;

/**
 * Servlet implementation class SalleController
 */
@WebServlet(name = "ClientController", urlPatterns = { "/client/ClientController","/admin/ClientController" })
public class ClientController extends HttpServlet {

	ClientService cs = new ClientService();

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
    
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("op") != null) {
			if (request.getParameter("op").equals("login")) {
				String cin = request.getParameter("cin");
				String password = request.getParameter("password");
				Client c = cs.findByCin(cin);
				response.setContentType("text/plain");
				if (c != null) {
					if (c.getPassword().equals(password)) {
						HttpSession session = request.getSession(false);
						session.setAttribute("client", c.getId());
						response.getWriter().write("1");
					} else {
						response.getWriter().write("2");
					}
				} else {
					response.getWriter().write("2");
				}
			} else if (request.getParameter("op").equals("insert")) {
				String nom = request.getParameter("nom");
				String prenom = request.getParameter("prenom");
				String cin = request.getParameter("cin");
				String password = request.getParameter("password");
				cs.create(new Client(nom, prenom, cin, password));
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Client> clients = cs.findAll();
				response.getWriter().write(json.toJson(clients));
			} else if (request.getParameter("op").equals("update")) {
				int id = Integer.parseInt(request.getParameter("id"));
				String nom = request.getParameter("nom");
				String prenom = request.getParameter("prenom");
				String cin = request.getParameter("cin");
				String password = request.getParameter("password");
				Client c = cs.findById(id);
				c.setNom(nom);
				c.setPrenom(prenom);
				c.setCin(cin);
				c.setPassword(password);
				cs.update(c);
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Client> clients = cs.findAll();
				response.getWriter().write(json.toJson(clients));
			} else if (request.getParameter("op").equals("delete")) {
				int id = Integer.parseInt(request.getParameter("id"));
				cs.delete(cs.findById(id));
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Client> clients = cs.findAll();
				response.getWriter().write(json.toJson(clients));
			} else if (request.getParameter("op").equals("findAll")) {
				List<Client> clients = cs.findAll();
				response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(clients));
			} else if (request.getParameter("op").equals("search")) {
				String champ = request.getParameter("champ");
				String recherche = request.getParameter("recherche");
				List<Client> clients = new ArrayList<>();
				if (recherche.isEmpty()) {
					clients = cs.findAll();
				} else {
					clients = cs.search(champ, recherche);
				}
				response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(clients));
			} else if (request.getParameter("op").equals("findWithoutReservation")) {
				List<Client> clients = cs.findWithoutReservation();
				response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(clients));
			} else if (request.getParameter("op").equals("disconnect")) {
            	request.getSession(false).invalidate();
            	response.setContentType("text/plain");
            	response.getWriter().write("1");
            } else if (request.getParameter("op").equals("display")) {
            	Client c = cs.findById((int) request.getSession(false).getAttribute("client"));
            	response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(c));
            }
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
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
