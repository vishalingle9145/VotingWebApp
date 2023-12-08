package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pojos.Candidate;

import static utils.DBUtils.*;

public class CandidateDaoImpl implements CandidateDao{
	
	private Connection cn;
	private PreparedStatement pst1, pst2, pst3, pst4;
	
	public CandidateDaoImpl() throws SQLException{
		
		cn = openConnection();
		
		pst1 = cn.prepareStatement("select * from candidates"); //Display all table data
		
		pst2 = cn.prepareStatement("update candidates set votes=votes+1 where id=?"); //update votes
		
		pst3 = cn.prepareStatement("select * from candidates order by votes desc limit 2"); //top two candidates
		
		pst4 = cn.prepareStatement("select party, sum(votes) from candidates group by party"); //partywise votes
		
	}
	

	@Override
	public List<Candidate> getAllCandidates() throws SQLException {
		
		
		List<Candidate> list = new ArrayList<Candidate>();
		
		try(ResultSet rst = pst1.executeQuery()) {
			
			while(rst.next())
			{
				list.add(new Candidate(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
//	increment votes by id
	@Override
	public String incrementVotes(int id) throws SQLException {
		
		pst2.setInt(1, id);
		
		int rowCount=pst2.executeUpdate();
		
		if(rowCount == 1)
		{
			return "Result status updated...!";
		}
		
		return "Result status not updated...!";
	}
	
	
//	Top 2 candidates having max votes
	@Override
	public List<Candidate> topTwoCandidates() throws SQLException {
		
		List<Candidate> list = new ArrayList<Candidate>();
		
		try(ResultSet rst = pst3.executeQuery()) {
			
			while(rst.next())
			{
				list.add(new Candidate(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	@Override
	public Map<String, Integer> partyWiseVotes() throws SQLException {
		
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		
		try(ResultSet rst = pst4.executeQuery()) {
			
			while(rst.next())
			{
				map.put(rst.getString(1), rst.getInt(2));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}


	
	
//	Cleaning the open resources
	public void cleanUp() throws SQLException {
		
		if(pst1 != null)
		{
			pst1.close();
		}
		
		if(pst2 != null)
		{
			pst2.close();
		}

		if(pst3 != null)
		{
			pst3.close();
		}

		if(pst4 != null)
		{
			pst4.close();
		}
		
		closeConnection();
		
	}


	

	

	

}
