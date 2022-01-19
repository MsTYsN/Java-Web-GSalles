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

import beans.Salle;
import service.SalleService;

/**
 * Servlet implementation class SalleController
 */
@WebServlet(name = "SalleController", urlPatterns = { "/admin/SalleController", "/client/SalleController" })
public class SalleController extends HttpServlet {

	SalleService ss = new SalleService();

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
				String code = request.getParameter("code");
				int capacite = Integer.parseInt(request.getParameter("capacite"));
				String type = request.getParameter("type");
				ss.create(new Salle(code, capacite, type));
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Salle> salles = ss.findAll();
				response.getWriter().write(json.toJson(salles));
			} else if (request.getParameter("op").equals("update")) {
				int id = Integer.parseInt(request.getParameter("id"));
				String code = request.getParameter("code");
				int capacite = Integer.parseInt(request.getParameter("capacite"));
				String type = request.getParameter("type");
				Salle s = ss.findById(id);
				s.setCode(code);
				s.setCapacite(capacite);
				s.setType(type);
				ss.update(s);
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Salle> salles = ss.findAll();
				response.getWriter().write(json.toJson(salles));
			} else if (request.getParameter("op").equals("delete")) {
				int id = Integer.parseInt(request.getParameter("id"));
				ss.delete(ss.findById(id));
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Salle> salles = ss.findAll();
				response.getWriter().write(json.toJson(salles));
			} else if (request.getParameter("op").equals("findAll")) {
				List<Salle> salles = ss.findAll();
				response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(salles));
			} else if (request.getParameter("op").equals("search")) {
				String champ = request.getParameter("champ");
				String recherche = request.getParameter("recherche");
				List<Salle> salles = new ArrayList<>();
				if(recherche.isEmpty()) {
					salles = ss.findAll();
				}else {
					salles = ss.search(champ, recherche);
				}
				response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(salles));
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
