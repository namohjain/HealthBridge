package in.co.ehealth.care.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import in.co.ehealth.care.bean.StaffBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DatabaseException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.util.JDBCDataSource;



/**
 * JDBC Implementation of StaffModel
 */

public class StaffModel {
	private static Logger log = Logger.getLogger(StaffModel.class.getName());
	

	public Integer nextPK() throws DatabaseException {
		log.info("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM Staff");
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

	
	@SuppressWarnings("deprecation")
	public long add(StaffBean bean) throws ApplicationException, DuplicateRecordException {
		
		Connection conn = null;
		int pk = 0;

		StaffBean existbean = findByUserName(bean.getUserName());

		if (existbean != null) {
			throw new DuplicateRecordException("UserName is already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Staff VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getUserName());
			pstmt.setString(5, bean.getPassword());
			pstmt.setString(6, bean.getContactNo());
			pstmt.setDate(7, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(8, bean.getEmail());
			pstmt.setString(9, bean.getQualification());
			pstmt.setString(10, bean.getExperience());
			pstmt.setLong(11, bean.getUserId());
			pstmt.setString(12, bean.getCreatedBy());
			pstmt.setString(13, bean.getModifiedBy());
			pstmt.setTimestamp(14, bean.getCreatedDatetime());
			pstmt.setTimestamp(15, bean.getModifiedDatetime());
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
			throw new ApplicationException("Exception : Exception in add Staff");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}

	
	public void delete(StaffBean bean) throws ApplicationException {
		
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Staff WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete Staff");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}



	@SuppressWarnings("unused")
	public StaffBean findByUserName(String userName) throws ApplicationException {
		log.info("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Staff WHERE USERNAME=?");
		StaffBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StaffBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setUserName(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setEmail(rs.getString(8));
				bean.setQualification(rs.getString(9));
				bean.setExperience(rs.getString(10));
				bean.setUserId(rs.getLong(11));
				bean.setCreatedBy(rs.getString(12));
				bean.setModifiedBy(rs.getString(13));
				bean.setCreatedDatetime(rs.getTimestamp(14));
				bean.setModifiedDatetime(rs.getTimestamp(15));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Staff by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByLogin End");
		return bean;
	}


	@SuppressWarnings("unused")
	public StaffBean findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Staff WHERE ID=?");
		StaffBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StaffBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setUserName(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setEmail(rs.getString(8));
				bean.setQualification(rs.getString(9));
				bean.setExperience(rs.getString(10));
				bean.setUserId(rs.getLong(11));
				bean.setCreatedBy(rs.getString(12));
				bean.setModifiedBy(rs.getString(13));
				bean.setCreatedDatetime(rs.getTimestamp(14));
				bean.setModifiedDatetime(rs.getTimestamp(15));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Staff by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByPK End");
		return bean;
	}
	
	@SuppressWarnings("unused")
	public StaffBean findByUserId(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Staff WHERE UserId=?");
		StaffBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StaffBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setUserName(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setEmail(rs.getString(8));
				bean.setQualification(rs.getString(9));
				bean.setExperience(rs.getString(10));
				bean.setUserId(rs.getLong(11));
				bean.setCreatedBy(rs.getString(12));
				bean.setModifiedBy(rs.getString(13));
				bean.setCreatedDatetime(rs.getTimestamp(14));
				bean.setModifiedDatetime(rs.getTimestamp(15));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Staff by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByPK End");
		return bean;
	}

	

	@SuppressWarnings("deprecation")
	public void update(StaffBean bean) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Connection conn = null;

		StaffBean beanExist = findByUserName(bean.getUserName());
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("UserName is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE Staff SET FIRSTNAME=?,LASTNAME=?,UserNAME=?,PASSWORD=?,CONTACTNO=?,DOB=?,Email=?,Qualification=?,experience=?,userId=?,"
					+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getUserName());
			pstmt.setString(4, bean.getPassword());
			pstmt.setString(5, bean.getContactNo());
			pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(7, bean.getEmail());
			pstmt.setString(8, bean.getQualification());
			pstmt.setString(9, bean.getExperience());
			pstmt.setLong(10, bean.getUserId());
			pstmt.setString(11, bean.getCreatedBy());
			pstmt.setString(12, bean.getModifiedBy());
			pstmt.setTimestamp(13, bean.getCreatedDatetime());
			pstmt.setTimestamp(14, bean.getModifiedDatetime());
			pstmt.setLong(15, bean.getId());
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
			throw new ApplicationException("Exception in updating Staff ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model update End");
	}

	

	public List<StaffBean> search(StaffBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}


	@SuppressWarnings("unused")
	public List<StaffBean> search(StaffBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Staff WHERE 1=1");

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
		
			
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList<StaffBean> list = new ArrayList<StaffBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StaffBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setUserName(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setEmail(rs.getString(8));
				bean.setQualification(rs.getString(9));
				bean.setExperience(rs.getString(10));
				bean.setUserId(rs.getLong(11));
				bean.setCreatedBy(rs.getString(12));
				bean.setModifiedBy(rs.getString(13));
				bean.setCreatedDatetime(rs.getTimestamp(14));
				bean.setModifiedDatetime(rs.getTimestamp(15));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Staff");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model search End");
		return list;
	}

	

	public List<StaffBean> list() throws ApplicationException {
		return list(0, 0);
	}



	@SuppressWarnings("unused")
	public List<StaffBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.info("Model list Started");
		ArrayList<StaffBean> list = new ArrayList<StaffBean>();
		StringBuffer sql = new StringBuffer("select * from Staff");
	
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
				StaffBean bean = new StaffBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setUserName(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setEmail(rs.getString(8));
				bean.setQualification(rs.getString(9));
				bean.setExperience(rs.getString(10));
				bean.setUserId(rs.getLong(11));
				bean.setCreatedBy(rs.getString(12));
				bean.setModifiedBy(rs.getString(13));
				bean.setCreatedDatetime(rs.getTimestamp(14));
				bean.setModifiedDatetime(rs.getTimestamp(15));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of Staff");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.info("Model list End");
		return list;

	}



}
