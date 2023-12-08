package dao;

import java.sql.*;
import static utils.DBUtils.*;

import pojos.User;

public class UserDaoImpl implements UserDao {
	// state
	private Connection cn;
	private PreparedStatement pst1, pst2, pst3, pst4;

	// def ctor
	public UserDaoImpl() throws SQLException {

		cn = openConnection(); // establish connection

		pst1 = cn.prepareStatement("select * from users where email=? and password=?"); // for signin

		pst2 = cn.prepareStatement("insert into users values(default,?,?,?,?,?,?,?)"); // signup

		pst3 = cn.prepareStatement("update users set status=? where id=?"); // update

		pst4 = cn.prepareStatement("delete from users where email=?"); // delete

		System.out.println("user dao created !");

	}

	@Override
	public User authenticateUser(String email, String password) throws SQLException {

		pst1.setString(1, email);
		pst1.setString(2, password);

		try (ResultSet rst = pst1.executeQuery()) {

			if (rst.next()) {
				return new User(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5),
						rst.getDate(6), rst.getBoolean(7), rst.getString(8));
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * first_name | last_name | email | password | dob | status | role
	 */
	@Override
	public int registerNewVoter(User voter) throws SQLException {
		// set IN params
		pst2.setString(1, voter.getFirstName());
		pst2.setString(2, voter.getLastName());
		pst2.setString(3, voter.getEmail());
		pst2.setString(4, voter.getPassword());
		pst2.setDate(5, voter.getDob());
		pst2.setBoolean(6, false);
		pst2.setString(7, "voter");

		int status = pst2.executeUpdate();

		if (status > 0) {
			System.out.println("Inserted...!"); // for console
		} else {
			System.out.println("Not inserted...!");
		}

		return status;
	}

	@Override
	public String updateVotingStatus(int voterId) throws SQLException {
		// set IN params
		pst3.setBoolean(1, true);
		pst3.setInt(2, voterId);
		// exec update
		int rowCount = pst3.executeUpdate();
		if (rowCount == 1)
			return "voting status updated!";
		return "Status updation failed!!!!!";
	}

	@Override
	public String deleteVoterDetails(String email) throws SQLException {
		// set IN param
		pst4.setString(1, email);
		// exec update : DML
		int rowCount = pst4.executeUpdate();
		if (rowCount == 1)
			return "voter details deleted....";
		return "Deletion failed!!!!!";
	}

	// add a cleanup method : to clean up the resources
	public void cleanUp() throws SQLException {
		if (pst1 != null)
			pst1.close();
		if (pst2 != null)
			pst2.close();
		if (pst3 != null)
			pst3.close();
		if (pst4 != null)
			pst4.close();
		closeConnection();
		System.out.println("user dao cleaned up....");
	}

}
