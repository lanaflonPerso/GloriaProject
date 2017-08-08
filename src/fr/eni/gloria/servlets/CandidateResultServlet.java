package fr.eni.gloria.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.gloria.beans.Answer;
import fr.eni.gloria.beans.Candidate;
import fr.eni.gloria.beans.Question;
import fr.eni.gloria.beans.Section;
import fr.eni.gloria.beans.Test;
import fr.eni.gloria.services.AnswerService;
import fr.eni.gloria.services.ResultService;
import fr.eni.gloria.services.TestService;
import fr.eni.gloria.utils.GloriaException;

/**
 * Servlet implementation class CandidateResultServlet
 */
public class CandidateResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CandidateResultServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Récupération des parametres de session (stagiaire, test)
		HttpSession session = request.getSession();
		Test test = (Test) session.getAttribute("requestedTest");
		Candidate stagiaire = ((Candidate) session.getAttribute("user"));
		RequestDispatcher rd =  request.getRequestDispatcher("/WEB-INF/jsp/candidate/testResult.jsp");
		
		// 1. Récupération du total attendu pour le test courant:
		int total = 0;
		try {
			total = TestService.getTotal(stagiaire, test);
		} catch (GloriaException ge) {
			request.setAttribute("error", ge.getMessage());
		}
		System.out.println(total);

		// 2. Calcul du résultat obtenu
		int resultatCandidat = 0;	
			// 2.1. Parcours des questions
			for (Section section : test.getSections()) {
				for (Question question : section.getQuestions()) {
					try {
						List<Answer> rightAnswers = AnswerService.getRightAnswers(question);
						List<Answer> givenAnswers = ResultService.getGivenAnswers(stagiaire, test, section, question);
						System.out.println(rightAnswers);
						System.out.println(givenAnswers);

			// 2.2. Comparaison des listes de réponses attendues et obtenues
							if ((rightAnswers.size() == givenAnswers.size()) && givenAnswers.containsAll(rightAnswers)) {
			// 2.3. Si les listes sont égales, alors on ajoute le poids de la question au résultat du candidat
									resultatCandidat += question.getWeight();
									System.out.println("Les réponses à la question "+question.getId() + " sont correctes");
							} else {
								System.out.println("Réponses erronées.");
							}						
					} catch (GloriaException e) {
						request.setAttribute("error", e.getMessage());
					}
				}
			}
		System.out.println(resultatCandidat);
		
		// 3. Calcul du pourcentage de réussite:
		int score = (int)(resultatCandidat*100/total);
		System.out.println(score);
		session.setAttribute("score", score);
		// 4. Ajout du score dans la base de données:
		try {
			ResultService.addResultCandidate(score, stagiaire, test);
		} catch (GloriaException e) {
			request.setAttribute("error", e.getMessage());
		}
		
		String bilan = null;
		// 5. Comparaison du score avec les seuils du test:
		if(score >= test.getSuccessTreshold()){
			bilan = "Acquis";
			System.out.println("test acquis");
		} else if(score>= test.getSemiSuccessTreshold()){
			bilan = "En cours d'acquisition";
			System.out.println("test en cours d'acquisition");
		} else {
			bilan = "Non acquis";
			System.out.println("test non acquis");
		}
		session.setAttribute("bilan", bilan);

		rd.forward(request, response);
	}
}
