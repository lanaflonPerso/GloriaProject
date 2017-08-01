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
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = GloriaLogger.getLogger(this.getClass().getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Récupération des paramètres login et mot de passe
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		HttpSession session = request.getSession(true);
		//Appel de la Service pour checker l'identification :
		
		
		//Code bouchon !! A SUPPRIMER
		if ("gloria".equals(login) && "gloria".equals(password)) {
			//Login ok ! 
			Candidate user = new Candidate();
			user.setFirstName("Gloria");
			user.setLastName("Gloria");
			session.setAttribute("user", user);
			request.getRequestDispatcher("/Home").forward(request, response);
		}else{
			System.out.println("login erroné");
			request.setAttribute("error", "Login et/ou mot de passe incorrect(s)");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
	}


	
}
