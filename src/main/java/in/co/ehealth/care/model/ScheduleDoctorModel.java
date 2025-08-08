package in.co.ehealth.care.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import in.co.ehealth.care.bean.ScheduleDoctorBean;
import in.co.ehealth.care.bean.DoctorBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DatabaseException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.util.JDBCDataSource;

/**
 * JDBC Implementation of ScheduleDoctorModel
 */

public class ScheduleDoctorModel {
	private static Logger log = Logger.getLogger(ScheduleDoctorModel.class.getName());
	

	public Integer nextPK() throws DatabaseException {
		log.info("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ScheduleDoctor");
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
	public long add(ScheduleDoctorBean bean) throws ApplicationException, DuplicateRecordException {
		
		Connection conn = null;
		int pk = 0;

		
		DoctorBean wBean=new DoctorModel().findByPK(bean.getDoctorId());

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ScheduleDoctor VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getDoctorId());
			pstmt.setString(3, wBean.getFirstName()+" "+wBean.getLastName());
			pstmt.setString(4, bean.getTime());
			pstmt.setDate(5, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(6, bean.getAddress());
			pstmt.setString(7, bean.getCity());
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
			throw new ApplicationException("Exception : Exception in add ScheduleDoctor");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}

	
	public void delete(ScheduleDoctorBean bean) throws ApplicationException {
		
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 			
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ScheduleDoctor WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete ScheduleDoctor");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}



	@SuppressWarnings("unused")
	public ScheduleDoctorBean findByDoctorName(String doctorName) throws ApplicationException {
		log.info("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ScheduleDoctor WHERE doctorName=?");
		ScheduleDoctorBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, doctorName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ScheduleDoctorBean();
				bean.setId(rs.getLong(1));
				bean.setDoctorId(rs.getLong(2));
				bean.setDoctorName(rs.getString(3));
				bean.setTime(rs.getString(4));
				bean.setDate(rs.getDate(5));
				bean.setAddress(rs.getString(6));
				bean.setCity(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting ScheduleDoctor by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByLogin End");
		return bean;
	}


	@SuppressWarnings("unused")
	public ScheduleDoctorBean findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ScheduleDoctor WHERE ID=?");
		ScheduleDoctorBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ScheduleDoctorBean();
				bean.setId(rs.getLong(1));
				bean.setDoctorId(rs.getLong(2));
				bean.setDoctorName(rs.getString(3));
				bean.setTime(rs.getString(4));
				bean.setDate(rs.getDate(5));
				bean.setAddress(rs.getString(6));
				bean.setCity(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting ScheduleDoctor by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByPK End");
		return bean;
	}

	

	@SuppressWarnings("deprecation")
	public void update(ScheduleDoctorBean bean) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Connection conn = null;

		/*
		 * ScheduleDoctorBean beanExist =
		 * findByScheduleDoctorNo(bean.getScheduleDoctorNo()); if (beanExist != null &&
		 * !(beanExist.getId() == bean.getId())) { throw new
		 * DuplicateRecordException("ScheduleDoctorNo is already exist"); }
		 */
		DoctorBean wBean=new DoctorModel().findByPK(bean.getDoctorId());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ScheduleDoctor SET doctorId=?,DoctorName=?,time=?,date=?,address=?,city=?,"
					+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1, bean.getDoctorId());
			pstmt.setString(2, wBean.getFirstName()+" "+wBean.getLastName());
			pstmt.setString(3, bean.getTime());
			pstmt.setDate(4, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(5, bean.getAddress());
			pstmt.setString(6, bean.getCity());
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
			throw new ApplicationException("Exception in updating ScheduleDoctor ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model update End");
	}

	

	public List<ScheduleDoctorBean> search(ScheduleDoctorBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}


	@SuppressWarnings("unused")
	public List<ScheduleDoctorBean> search(ScheduleDoctorBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ScheduleDoctor WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getDoctorName()!= null && bean.getDoctorName().length() > 0) {
				sql.append(" AND DoctorName like '" + bean.getDoctorName() + "%'");
			}
			
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList<ScheduleDoctorBean> list = new ArrayList<ScheduleDoctorBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ScheduleDoctorBean();
				bean.setId(rs.getLong(1));
				bean.setDoctorId(rs.getLong(2));
				bean.setDoctorName(rs.getString(3));
				bean.setTime(rs.getString(4));
				bean.setDate(rs.getDate(5));
				bean.setAddress(rs.getString(6));
				bean.setCity(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search ScheduleDoctor");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model search End");
		return list;
	}

	

	public List<ScheduleDoctorBean> list() throws ApplicationException {
		return list(0, 0);
	}



	@SuppressWarnings("unused")
	public List<ScheduleDoctorBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.info("Model list Started");
		ArrayList<ScheduleDoctorBean> list = new ArrayList<ScheduleDoctorBean>();
		StringBuffer sql = new StringBuffer("select * from ScheduleDoctor");
	
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
				ScheduleDoctorBean bean = new ScheduleDoctorBean();
				bean.setId(rs.getLong(1));
				bean.setId(rs.getLong(1));
				bean.setDoctorId(rs.getLong(2));
				bean.setDoctorName(rs.getString(3));
				bean.setTime(rs.getString(4));
				bean.setDate(rs.getDate(5));
				bean.setAddress(rs.getString(6));
				bean.setCity(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of ScheduleDoctor");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.info("Model list End");
		return list;

	}



}
