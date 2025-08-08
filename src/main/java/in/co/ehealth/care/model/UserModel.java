package in.co.ehealth.care.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import in.co.ehealth.care.bean.UserBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DatabaseException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.exception.RecordNotFoundException;
import in.co.ehealth.care.util.JDBCDataSource;



/**
 * JDBC Implementation of UserModel
 */

public class UserModel {
	private static Logger log = Logger.getLogger(UserModel.class.getName());
	

	public Integer nextPK() throws DatabaseException {
		log.info("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM USER");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model nextPK End");
		return pk + 1;
	}

	
	public long add(UserBean bean) throws ApplicationException, DuplicateRecordException {
		
		Connection conn = null;
		int pk = 0;

		UserBean existbean = findByUserName(bean.getUserName());

		if (existbean != null) {
			throw new DuplicateRecordException("UserName is already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getUserName());
			pstmt.setString(5, bean.getPassword());
			pstmt.setString(6, bean.getContactNo());
			pstmt.setLong(7, bean.getRoleId());
			pstmt.setString(8, bean.getRoleName());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}

	
	public void delete(UserBean bean) throws ApplicationException {
		
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM USER WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
		
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}



	@SuppressWarnings("unused")
	public UserBean findByUserName(String userName) throws ApplicationException {
		log.info("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM USER WHERE USERNAME=?");
		UserBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setUserName(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setRoleId(rs.getLong(7));
				bean.setRoleName(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByLogin End");
		return bean;
	}


	@SuppressWarnings("unused")
	public UserBean findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM USER WHERE ID=?");
		UserBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setUserName(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setRoleId(rs.getLong(7));
				bean.setRoleName(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByPK End");
		return bean;
	}

	

	public void update(UserBean bean) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Connection conn = null;

		UserBean beanExist = findByUserName(bean.getUserName());
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("UserName is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE USER SET FIRSTNAME=?,LASTNAME=?,USERNAME=?,PASSWORD=?,CONTACTNO=?,ROLEID=?,ROLENAME=?,"
					+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getUserName());
			pstmt.setString(4, bean.getPassword());
			pstmt.setString(5, bean.getContactNo());
			pstmt.setLong(6, bean.getRoleId());
			pstmt.setString(7, bean.getRoleName());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.setLong(12, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model update End");
	}

	

	public List<UserBean> search(UserBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}


	@SuppressWarnings("unused")
	public List<UserBean> search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM USER WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRSTNAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LASTNAME like '" + bean.getLastName() + "%'");
			}
			
			if (bean.getUserName() != null && bean.getUserName().length() > 0) {
				sql.append(" AND USERNAME like '" + bean.getUserName() + "%'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
			}
			
			if (bean.getContactNo() != null && bean.getContactNo().length() > 0) {
				sql.append(" AND CONTACTNO = " + bean.getContactNo());
			}
			if (bean.getRoleId() > 0) {
				sql.append(" AND ROLEID = " + bean.getRoleId());
			}
			
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList<UserBean> list = new ArrayList<UserBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setUserName(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setRoleId(rs.getLong(7));
				bean.setRoleName(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model search End");
		return list;
	}

	

	public List<UserBean> list() throws ApplicationException {
		return list(0, 0);
	}



	@SuppressWarnings("unused")
	public List<UserBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.info("Model list Started");
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		StringBuffer sql = new StringBuffer("select * from USER");
	
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				UserBean bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setUserName(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setRoleId(rs.getLong(7));
				bean.setRoleName(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.info("Model list End");
		return list;

	}

	
	@SuppressWarnings("unused")
	public UserBean authenticate(String userName, String password) throws ApplicationException {
		log.info("Model authenticate Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?");
		UserBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setUserName(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setRoleId(rs.getLong(7));
				bean.setRoleName(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.info("Model authenticate End");
		return bean;
	}



		public boolean changePassword(Long id, String oldPassword, String newPassword)
				throws RecordNotFoundException, ApplicationException {

			log.info("model changePassword Started");
			
			boolean flag = false;
			
			UserBean beanExist = null;

			beanExist = findByPK(id);
			
			if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
				beanExist.setPassword(newPassword);
				try {
					update(beanExist);
				} catch (DuplicateRecordException e) {
					throw new ApplicationException("LoginId is already exist");
				}
				flag = true;
			} else {
				throw new RecordNotFoundException("Old password is Invalid");
			}

			

			log.info("Model changePassword End");
			return flag;

		}

	public UserBean updateAccess(UserBean bean) throws ApplicationException {
		return null;
	}

	public long registerUser(UserBean bean)
			throws ApplicationException, DuplicateRecordException {

		log.info("Model add Started");

		long pk = add(bean);

		/*
		 * HashMap<String, String> map = new HashMap<String, String>(); map.put("login",
		 * bean.getUserName()); map.put("password", bean.getPassword());
		 * map.put("firstName", bean.getName());
		 * 
		 * String message = EmailBuilder.getUserRegistrationMessage(map);
		 * 
		 * EmailMessage msg = new EmailMessage();
		 * 
		 * msg.setTo(bean.getEmail());
		 * msg.setSubject("Train Reservation System Registration Successfully!!!");
		 * msg.setMessage(message); msg.setMessageType(EmailMessage.HTML_MSG);
		 * 
		 * EmailUtility.sendMail(msg);
		 */
		
		return pk;
	}

	

	
}
