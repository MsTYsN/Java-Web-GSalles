package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Creneau;
import service.CreneauService;

/**
 * Servlet implementation class SalleController
 */
@WebServlet(name = "CreneauController", urlPatterns = { "/admin/CreneauController", "/client/CreneauController" })
public class CreneauController extends HttpServlet {

	CreneauService cs = new CreneauService();

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
			if (request.getParameter("op").equals("insert")) {
				String heureDebut = request.getParameter("heureDebut");
				String heureFin = request.getParameter("heureFin");
				cs.create(new Creneau(heureDebut, heureFin));
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Creneau> creneaux = cs.findAll();
				response.getWriter().write(json.toJson(creneaux));
			} else if (request.getParameter("op").equals("update")) {
				int id = Integer.parseInt(request.getParameter("id"));
				String heureDebut = request.getParameter("heureDebut");
				String heureFin = request.getParameter("heureFin");
				Creneau c = cs.findById(id);
				c.setHeureDebut(heureDebut);
				c.setHeureFin(heureFin);
				cs.update(c);
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Creneau> clients = cs.findAll();
				response.getWriter().write(json.toJson(clients));
			} else if (request.getParameter("op").equals("delete")) {
				int id = Integer.parseInt(request.getParameter("id"));
				cs.delete(cs.findById(id));
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Creneau> creneaux = cs.findAll();
				response.getWriter().write(json.toJson(creneaux));
			} else if (request.getParameter("op").equals("findAll")) {
				List<Creneau> creneaux = cs.findAll();
				response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(creneaux));
			} else if (request.getParameter("op").equals("search")) {
				String champ = request.getParameter("champ");
				String recherche = request.getParameter("recherche");
				List<Creneau> creneaux = new ArrayList<>();
				if (recherche.isEmpty()) {
					creneaux = cs.findAll();
				} else {
					creneaux = cs.search(champ, recherche);
				}
				response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(creneaux));
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
