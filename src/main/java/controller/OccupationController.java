package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Client;
import beans.Creneau;
import beans.Occupation;
import beans.Salle;
import service.ClientService;
import service.CreneauService;
import service.OccupationService;
import service.SalleService;

/**
 * Servlet implementation class SalleController
 */
@WebServlet(name = "OccupationController", urlPatterns = { "/admin/OccupationController",
		"/client/OccupationController" })
public class OccupationController extends HttpServlet {

	OccupationService os = new OccupationService();
	ClientService cs = new ClientService();
	SalleService ss = new SalleService();
	CreneauService cns = new CreneauService();

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
				Date date = new Date(request.getParameter("date").replace("-", "/"));
				Client c = cs.findById(Integer.parseInt(request.getParameter("idClient")));
				Salle s = ss.findById(Integer.parseInt(request.getParameter("idSalle")));
				Creneau cn = cns.findById(Integer.parseInt(request.getParameter("idCreneau")));
				String etat = "En attente de validation";
				os.create(new Occupation(date, c, s, cn, etat));
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Occupation> occupations = os.findBySalle(s.getId());
				response.getWriter().write(json.toJson(occupations));
			} else if (request.getParameter("op").equals("insertAdmin")) {
				Date date = new Date(request.getParameter("date").replace("-", "/"));
				Client c = cs.findById(Integer.parseInt(request.getParameter("idClient")));
				Salle s = ss.findById(Integer.parseInt(request.getParameter("idSalle")));
				Creneau cn = cns.findById(Integer.parseInt(request.getParameter("idCreneau")));
				String etat = "Réservé";
				os.create(new Occupation(date, c, s, cn, etat));
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Occupation> occupations = os.findBySalle(s.getId());
				response.getWriter().write(json.toJson(occupations));
			} else if (request.getParameter("op").equals("update")) {
				int id = Integer.parseInt(request.getParameter("id"));
				Date date = new Date(request.getParameter("date"));
				Client c = cs.findById(Integer.parseInt(request.getParameter("idClient")));
				Salle s = ss.findById(Integer.parseInt(request.getParameter("idSalle")));
				Creneau cn = cns.findById(Integer.parseInt(request.getParameter("idCreneau")));
				String etat = "Attente";
				Occupation o = os.findById(id);
				o.setDate(date);
				o.setClient(c);
				o.setSalle(s);
				o.setCreneau(cn);
				o.setEtat(etat);
				os.update(o);
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Occupation> occupations = os.findBySalle(s.getId());
				response.getWriter().write(json.toJson(occupations));
			} else if (request.getParameter("op").equals("delete")) {
				int id = Integer.parseInt(request.getParameter("id"));
				Salle s = ss.findById(Integer.parseInt(request.getParameter("idSalle")));
				os.delete(os.findById(id));
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Occupation> occupations = os.findBySalle(s.getId());
				response.getWriter().write(json.toJson(occupations));
			} else if (request.getParameter("op").equals("findAll")) {
				List<Occupation> occupations = os.findAll();
				response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(occupations));
			} else if (request.getParameter("op").equals("findBySalle")) {
				int idSalle = Integer.parseInt(request.getParameter("idSalle"));
				List<Occupation> occupations = os.findBySalle(idSalle);
				response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(occupations));
			} else if (request.getParameter("op").equals("search")) {
				String champ = request.getParameter("champ");
				String recherche = request.getParameter("recherche");
				List<Occupation> occupations = new ArrayList<>();
				if (recherche.isEmpty()) {
					occupations = os.findAll();
				} else {
					occupations = os.search(champ, recherche);
				}
				response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(occupations));
			} else if (request.getParameter("op").equals("validate")) {
				int id = Integer.parseInt(request.getParameter("id"));
				Salle s = ss.findById(Integer.parseInt(request.getParameter("idSalle")));
				Occupation o = os.findById(id);
				o.setEtat("Réservé");
				os.update(o);
				response.setContentType("application/json");
				Gson json = new Gson();
				List<Occupation> occupations = os.findBySalle(s.getId());
				response.getWriter().write(json.toJson(occupations));
			} else if (request.getParameter("op").equals("mostReserved")) {
				response.setContentType("application/json");
				Gson json = new Gson();
				Map<String, Integer> salles = os.findMostReserved();
				response.getWriter().write(json.toJson(salles));
			} else if (request.getParameter("op").equals("monthReserved")) {
				response.setContentType("application/json");
				Gson json = new Gson();
				Map<String, Integer> months = os.findMonthReservation();
				response.getWriter().write(json.toJson(months));
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
