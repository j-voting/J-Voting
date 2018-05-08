
package io.github.oliviercailloux.y2018.j_voting;


import java.util.*;

import org.slf4j.*;



public class Borda implements SocialWelfareFunction{

	Logger LOGGER = LoggerFactory.getLogger(Borda.class.getName());	
	
	/***
	 * returns a StrictPreference with the alternatives sorted
	 */
	@Override
	public StrictPreference getSocietyStrictPreference(StrictProfile sProfile){
		LOGGER.debug("getSocietyStrictPreference\n");
		Objects.requireNonNull(sProfile);
		LOGGER.debug("parameter SProfile : {}\n", sProfile.toSOC());
		
		Map<Alternative,Integer> finalScores = getScores(sProfile);
		finalScores = descendingOrder(finalScores);
		
		LOGGER.debug("return AScores : {}\n", finalScores.toString());
		
		Iterable<Alternative> alternatives = finalScores.keySet();
		StrictPreference pref = new StrictPreference(alternatives);
		
		LOGGER.debug("return AScores : {}\n", pref.toString());
		return pref;
	}


	/***
	 * assigns a score to each alternative of a StrictPreference
	 * @param SPref
	 */

	public Map<Alternative, Integer> getScores(StrictPreference sPref){
		LOGGER.debug("getScorePref\n");
		Objects.requireNonNull(sPref);
		LOGGER.debug("parameter SPref : {}\n", sPref.toString());
		Map<Alternative,Integer> unsortedScores = new HashMap<>();
		int i;
		List<Alternative> Alternatives = sPref.getPreferences();
		for(i=0;i<Alternatives.size();i++){
			unsortedScores.put(Alternatives.get(i),Integer.valueOf(Alternatives.size()-i));
		}
		LOGGER.debug("return score : {}\n", unsortedScores.toString());
		return unsortedScores;
	}


	/***
	 * 	assigns a score to each alternative of a StrictProfile
	 * @param SProfile
	 * @return
	 */
	public Map<Alternative, Integer> getScores(StrictProfile sProfile){
		LOGGER.debug("getScoreProf\n");
		Objects.requireNonNull(sProfile);
		LOGGER.debug("parameter SProfile : {}\n", sProfile.toSOC());
		//int i,j;
		boolean notfirst = false;
		
		Iterable<Voter> allVoters  = sProfile.getAllVoters();
		Iterator<Voter> iteratorV = allVoters.iterator();
		Voter currentVoter;
		
		Map<Alternative,Integer> tempScores = new HashMap<>();
		Map<Alternative,Integer> unsortedScores = new HashMap<>();
		
		Iterable<Alternative> alternativesList = sProfile.getAlternativesComplete();
		Iterator<Alternative> iteratorA = alternativesList.iterator();
		Alternative currentAlternative;
		
		Integer score = 0;
		
		while(iteratorV.hasNext()){
			currentVoter = iteratorV.next();
			tempScores = getScores((sProfile.getPreference(currentVoter)));
			
			if (notfirst){
				while(iteratorA.hasNext()){
					currentAlternative = iteratorA.next();
					score = unsortedScores.get(currentAlternative) + tempScores.get(currentAlternative);
					unsortedScores.remove(currentAlternative);
					unsortedScores.put(currentAlternative, score);
					
				}
			}

			else{
				unsortedScores = tempScores;
			}
		}
		LOGGER.debug("return unsortedScores : {}\n", unsortedScores.toString());
		return unsortedScores;
	}

	/***
	 * Sorts by descending order
	 */
	public Map<Alternative, Integer> descendingOrder(Map<Alternative, Integer> unsortedScores){
		LOGGER.debug("descendingOrder\n");
		
		Map<Alternative,Integer> tempScores = unsortedScores;
		Alternative alternativeMax;
		
		Map<Alternative,Integer> finalScores = new HashMap<>();
		
		for(int i=0 ; i<unsortedScores.size();i++){
			alternativeMax = getMax(tempScores);
			finalScores.put(alternativeMax,tempScores.get(alternativeMax));
			tempScores.remove(alternativeMax);
		}
		
		
		LOGGER.debug("return sortedScores : {}\n", finalScores.toString());
		return finalScores;
	}

	/***
	 * get the alternative of the score max
	 * @param AScore
	 * @return
	 */
	public Alternative getMax(Map<Alternative, Integer> scores){
		LOGGER.debug("getMax\n");
		
		Iterable<Alternative> alternativesList = scores.keySet();
		Iterator<Alternative> iteratorA = alternativesList.iterator();
		Alternative currentAlternative;
		
		Alternative alternativeMax = new Alternative(0); 
		boolean first = true;
		
		while(iteratorA.hasNext()){
			currentAlternative = iteratorA.next();
			if (first){
				alternativeMax = currentAlternative;
				first = false;
			}
			else{
				if (scores.get(currentAlternative)>scores.get(alternativeMax)){
					alternativeMax = currentAlternative ;
				}
			}
		}
		
		LOGGER.debug("Max : {} \n", alternativeMax);
		return alternativeMax;
		
	}


	/***
	 * create a HashMap of Alternatives sorted by descending order of score
	 * @param SProfile
	 * @return
	 */
	public Map<Alternative,Integer> getSortedScores(StrictProfile sProfile){
		LOGGER.debug("getSortedScores\n");
		Objects.requireNonNull(sProfile);
		LOGGER.debug("parameter sProfile : {}\n", sProfile.toSOC());
		
		Map<Alternative,Integer> finalScores = getScores(sProfile);
		finalScores = descendingOrder(finalScores);
		LOGGER.debug("return AScores : {}\n", finalScores.toString());
		return finalScores;
	}


}
