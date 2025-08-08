package in.co.ehealth.care.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import in.co.ehealth.care.bean.StaffTimingBean;
import in.co.ehealth.care.bean.DoctorBean;
import in.co.ehealth.care.bean.StaffBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DatabaseException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.util.JDBCDataSource;

/**
 * JDBC Implementation of StaffTimingModel
 */

public class StaffTimingModel {
	private static Logger log = Logger.getLogger(StaffTimingModel.class.getName());
	

	public Integer nextPK() throws DatabaseException {
		log.info("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM StaffTiming");
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
	public long add(StaffTimingBean bean) throws ApplicationException, DuplicateRecordException {
		
		Connection conn = null;
		int pk = 0;

		
		StaffBean wBean=new StaffModel().findByPK(bean.getStaffId());

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StaffTiming VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getStaffId());
			pstmt.setString(3, wBean.getFirstName()+" "+wBean.getLastName());
			pstmt.setString(4, bean.getTiming());
			pstmt.setDate(5, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(6, bean.getWardName());
			pstmt.setString(7, bean.getRoomNo());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
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
			throw new ApplicationException("Exception : Exception in add StaffTiming");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}

	
	public void delete(StaffTimingBean bean) throws ApplicationException {
		
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 			
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StaffTiming WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); 
			pstmt.close();

		} catch (Exception e) {
		
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete StaffTiming");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}



	@SuppressWarnings("unused")
	public StaffTimingBean findByDoctorName(String doctorName) throws ApplicationException {
		log.info("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM StaffTiming WHERE doctorName=?");
		StaffTimingBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, doctorName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StaffTimingBean();
				bean.setId(rs.getLong(1));
				bean.setStaffId(rs.getLong(2));
				bean.setStaffName(rs.getString(3));
				bean.setTiming(rs.getString(4));
				bean.setDate(rs.getDate(5));
				bean.setWardName(rs.getString(6));
				bean.setRoomNo(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting StaffTiming by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByLogin End");
		return bean;
	}


	@SuppressWarnings("unused")
	public StaffTimingBean findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM StaffTiming WHERE ID=?");
		StaffTimingBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StaffTimingBean();
				bean.setId(rs.getLong(1));
				bean.setStaffId(rs.getLong(2));
				bean.setStaffName(rs.getString(3));
				bean.setTiming(rs.getString(4));
				bean.setDate(rs.getDate(5));
				bean.setWardName(rs.getString(6));
				bean.setRoomNo(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting StaffTiming by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByPK End");
		return bean;
	}

	

	@SuppressWarnings("deprecation")
	public void update(StaffTimingBean bean) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Connection conn = null;

		/*
		 * StaffTimingBean beanExist =
		 * findByStaffTimingNo(bean.getStaffTimingNo()); if (beanExist != null &&
		 * !(beanExist.getId() == bean.getId())) { throw new
		 * DuplicateRecordException("StaffTimingNo is already exist"); }
		 */
		StaffBean wBean=new StaffModel().findByPK(bean.getStaffId());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE StaffTiming SET staffId=?,staffName=?,timing=?,date=?,wardName=?,roomNo=?,"
					+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1, bean.getStaffId());
			pstmt.setString(2, wBean.getFirstName()+" "+wBean.getLastName());
			pstmt.setString(3, bean.getTiming());
			pstmt.setDate(4, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(5, bean.getWardName());
			pstmt.setString(6, bean.getRoomNo());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.setLong(11, bean.getId());
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
			throw new ApplicationException("Exception in updating StaffTiming ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model update End");
	}

	

	public List<StaffTimingBean> search(StaffTimingBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}


	@SuppressWarnings("unused")
	public List<StaffTimingBean> search(StaffTimingBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM StaffTiming WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			
			if (bean.getStaffId() > 0) {
				sql.append(" AND staffId = " + bean.getStaffId());
			}
			
			if (bean.getStaffName()!= null && bean.getStaffName().length() > 0) {
				sql.append(" AND StaffName like '" + bean.getStaffName() + "%'");
			}
			
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList<StaffTimingBean> list = new ArrayList<StaffTimingBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StaffTimingBean();
				bean.setId(rs.getLong(1));
				bean.setStaffId(rs.getLong(2));
				bean.setStaffName(rs.getString(3));
				bean.setTiming(rs.getString(4));
				bean.setDate(rs.getDate(5));
				bean.setWardName(rs.getString(6));
				bean.setRoomNo(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search StaffTiming");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model search End");
		return list;
	}

	

	public List<StaffTimingBean> list() throws ApplicationException {
		return list(0, 0);
	}



	@SuppressWarnings("unused")
	public List<StaffTimingBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.info("Model list Started");
		ArrayList<StaffTimingBean> list = new ArrayList<StaffTimingBean>();
		StringBuffer sql = new StringBuffer("select * from StaffTiming");
	
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
				StaffTimingBean bean = new StaffTimingBean();
				bean.setId(rs.getLong(1));
				bean.setId(rs.getLong(1));
				bean.setStaffId(rs.getLong(2));
				bean.setStaffName(rs.getString(3));
				bean.setTiming(rs.getString(4));
				bean.setDate(rs.getDate(5));
				bean.setWardName(rs.getString(6));
				bean.setRoomNo(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of StaffTiming");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.info("Model list End");
		return list;

	}



}
