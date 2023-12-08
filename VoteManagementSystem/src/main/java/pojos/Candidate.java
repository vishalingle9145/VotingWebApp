package pojos;

public class Candidate {
	
	private int candidateID;
	private String name;
	private String party;
	private int votes;
	
	public Candidate() {
		//not required in JDBC  but required in hibernate
	}

	public Candidate(int candidateID, String name, String party, int votes) {
		super();
		this.candidateID = candidateID;
		this.name = name;
		this.party = party;
		this.votes = votes;
	}

	public int getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(int candidateID) {
		this.candidateID = candidateID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "Candidate [candidateID=" + candidateID + ", name=" + name + ", party=" + party + ", votes=" + votes
				+ "]";
	}
	
	

}
