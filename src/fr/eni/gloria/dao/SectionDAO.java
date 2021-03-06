
/**
 * @author lvanhove2017
 * @date 4 août 2017
 * @version GloriaProject V1.0
 */
package fr.eni.gloria.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.eni.gloria.beans.Section;
import fr.eni.gloria.utils.AccessBase;
import fr.eni.gloria.utils.GloriaException;
import fr.eni.gloria.utils.GloriaLogger;

/**
 * @author lvanhove2017
 * @date 4 août 2017
 * @version GloriaProject V1.0
 */
public class SectionDAO implements ICrud<Section>{
	Logger logger = GloriaLogger.getLogger(this.getClass().getName());
	private static final String CALCULATE_TOTAL_SECTION =  "SELECT SUM(poids) as totalSection"
														+ "	FROM sections s"
														+ "	JOIN questions_selectionnees qs ON qs.idSection = s.id"
														+ " JOIN questions q ON q.id = qs.idQuestion"
														+ " WHERE idStagiaire = ?"
														+ " AND idTest = ?"
														+ " AND idSection = ?;";
	/**
	 * Méthode en charge de récupérer les sections du test 
	 * ont l'identifiant est donné en paramètreµ.
	 * 
	 * @param id Identifiant du test
	 *  
	 * @return La liste des sections de ce test.
	 * 
	 * @throws GloriaException 
	 */
	public List<Section> getSectionsByTestId(int idTest) throws GloriaException {
		List<Section> result =  new ArrayList<>();
		try(Connection cnx = AccessBase.getConnection()){
			CallableStatement rqt = cnx.prepareCall("{CALL LIST_SECTIONS_TEST(?)}");
			rqt.setInt(1, idTest);
			ResultSet rs = rqt.executeQuery();
			while(rs.next()){
				result.add(itemBuilder(rs));
			}
		} catch (SQLException e) {
			logger.severe(this.getClass().getName()+"#getSectionsByTestId : "+e.getMessage());
			throw new GloriaException("Erreur lors de la récupération de la liste des sections depuis la base de données.");
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see fr.eni.gloria.dao.ICrud#insert(java.lang.Object)
	 */
	@Override
	public boolean insert(Section data) throws GloriaException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see fr.eni.gloria.dao.ICrud#update(java.lang.Object)
	 */
	@Override
	public boolean update(Section data) throws GloriaException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see fr.eni.gloria.dao.ICrud#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(Section data) throws GloriaException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see fr.eni.gloria.dao.ICrud#selectById(int)
	 */
	@Override
	public Section selectById(int id) throws GloriaException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see fr.eni.gloria.dao.ICrud#selectAll()
	 */
	@Override
	public List<Section> selectAll() throws GloriaException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see fr.eni.gloria.dao.ICrud#itemBuilder(java.sql.ResultSet)
	 */
	@Override
	public Section itemBuilder(ResultSet rs) throws GloriaException {
		Section result = new Section();
		try{
			result.setId(rs.getInt("id"));
			result.setCaption(rs.getString("libelle"));
		}catch(SQLException e){
			logger.severe(this.getClass().getName()+"#itemBuilder : "+e.getMessage());
			throw new GloriaException("Erreur lors de la construction de la section depuis la base de données.");
		}
		return result;
	}

	/**
	 * Méthode en charge de calculer le total attendu pour une section dans test donné
	 * @param idTest 
	 * @param idStagiaire 
	 * @return le total pour une section
	 * @throws GloriaException 
	 */
	public int getTotalSection(int idCandidate, int idTest, int idSection) throws GloriaException {
		int result = 0;
		ResultSet rs = null;
		try(Connection cnx = AccessBase.getConnection()){
			PreparedStatement rqt = cnx.prepareStatement(CALCULATE_TOTAL_SECTION);
			rqt.setInt(1, idCandidate);
			rqt.setInt(2, idTest);
			rqt.setInt(3, idSection);
			rs = rqt.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}			
		} catch (SQLException e) {
			logger.severe(this.getClass().getName()+"#getTotalSection : "+e.getMessage());
			throw new GloriaException("Erreur lors du calcul du total attendu pour une section dans un test.");
		}
		return result;	
	}
}

