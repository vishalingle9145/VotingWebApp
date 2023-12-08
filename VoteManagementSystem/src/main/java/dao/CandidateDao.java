package dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pojos.Candidate;

public interface CandidateDao {
	
	List<Candidate> getAllCandidates() throws SQLException;
	
	String incrementVotes(int id) throws SQLException;
	
	List<Candidate> topTwoCandidates() throws SQLException;
	
	Map<String, Integer> partyWiseVotes() throws SQLException;

}
