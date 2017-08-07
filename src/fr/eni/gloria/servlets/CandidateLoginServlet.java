package fr.eni.gloria.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.gloria.beans.Candidate;
import fr.eni.gloria.utils.GloriaLogger;

/**
 * Servlet implementation class AuthentificationServlet
 */
public class CandidateLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = GloriaLogger.getLogger(this.getClass().getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CandidateLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/candidate/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.entering(this.getClass().getName(), "doPost");
		//Récupération des paramètres login et mot de passe
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		System.out.println(login+" "+password);
		HttpSession session = request.getSession(true);
		//Appel de la Service pour checker l'identification :
		
		
		//Code bouchon !! A SUPPRIMER
		if ("gloria".equals(login) && "gloria".equals(password)) {
			
			Candidate user = new Candidate();
			user.setFirstName("Gloria");
			user.setLastName("Gloria");
			session.setAttribute("user", user);
			request.getRequestDispatcher("/Candidate").forward(request, response);
		}else{
			System.out.println("login nok");
			request.setAttribute("error", "Login et/ou mot de passe incorrect(s)");
			request.getRequestDispatcher("/WEB-INF/jsp/candidate/login.jsp").forward(request, response);
		}
		
		
		
	}


	
}
