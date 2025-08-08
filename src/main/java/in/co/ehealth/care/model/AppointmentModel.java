package in.co.ehealth.care.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import in.co.ehealth.care.bean.AppointmentBean;
import in.co.ehealth.care.bean.DoctorBean;
import in.co.ehealth.care.bean.PatientBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DatabaseException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.util.JDBCDataSource;

public class AppointmentModel {
	private static Logger log = Logger.getLogger(AppointmentModel.class.getName());
	

	public Integer nextPK() throws DatabaseException {
		log.info("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM Appointment");
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

	public long add(AppointmentBean bean) throws ApplicationException, DuplicateRecordException {
		
		Connection conn = null;
		int pk = 0;

		
		DoctorBean wBean=new DoctorModel().findByPK(bean.getDoctorId());
		PatientBean pBean=new PatientModel().findByPK(bean.getPatientId());

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Appointment VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getPatientId());
			pstmt.setString(3, pBean.getFirstName()+" "+pBean.getLastName());
			pstmt.setLong(4, bean.getDoctorId());
			pstmt.setString(5, wBean.getFirstName()+" "+wBean.getLastName());
			pstmt.setLong(6, bean.getScheduleId());
			pstmt.setDate(7, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(8, bean.getAllergy());
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
			throw new ApplicationException("Exception : Exception in add Appointment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}

	
	public void delete(AppointmentBean bean) throws ApplicationException {
		
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 			
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Appointment WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete Appointment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}


	public AppointmentBean findByDoctorName(String doctorName) throws ApplicationException {
		log.info("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Appointment WHERE doctorName=?");
		AppointmentBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, doctorName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new AppointmentBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setScheduleId(rs.getLong(6));
				bean.setDate(rs.getDate(7));
				bean.setAllergy(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Appointment by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByLogin End");
		return bean;
	}

	public AppointmentBean findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Appointment WHERE ID=?");
		AppointmentBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new AppointmentBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setScheduleId(rs.getLong(6));
				bean.setDate(rs.getDate(7));
				bean.setAllergy(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Appointment by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByPK End");
		return bean;
	}

	
	public void update(AppointmentBean bean) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Connection conn = null;

		/*
		 * AppointmentBean beanExist =
		 * findByAppointmentNo(bean.getAppointmentNo()); if (beanExist != null &&
		 * !(beanExist.getId() == bean.getId())) { throw new
		 * DuplicateRecordException("AppointmentNo is already exist"); }
		 */
		DoctorBean wBean=new DoctorModel().findByPK(bean.getDoctorId());
		PatientBean pBean=new PatientModel().findByPK(bean.getPatientId());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE Appointment SET patientId=?,patientName=?,doctorId=?,DoctorName=?,scheduleId=?,date=?,allergy=?,"
					+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1, bean.getPatientId());
			pstmt.setString(2, pBean.getFirstName()+" "+pBean.getLastName());
			pstmt.setLong(3, bean.getDoctorId());
			pstmt.setString(4, wBean.getFirstName()+" "+wBean.getLastName());
			pstmt.setLong(5, bean.getScheduleId());
			pstmt.setDate(6, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(7, bean.getAllergy());
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
			throw new ApplicationException("Exception in updating Appointment ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model update End");
	}

	

	public List<AppointmentBean> search(AppointmentBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}


	public List<AppointmentBean> search(AppointmentBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Appointment WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getPatientId() > 0) {
				sql.append(" AND PatientId = " + bean.getPatientId());
			}
			
			if (bean.getDoctorId() > 0) {
				sql.append(" AND DoctorId = " + bean.getDoctorId());
			}
			
			if (bean.getScheduleId() > 0) {
				sql.append(" AND ScheduleId = " + bean.getScheduleId());
			}
			
			if (bean.getDoctorName()!= null && bean.getDoctorName().length() > 0) {
				sql.append(" AND DoctorName like '" + bean.getDoctorName() + "%'");
			}
			if (bean.getPatientName()!= null && bean.getPatientName().length() > 0) {
				sql.append(" AND PatientName like '" + bean.getPatientName() + "%'");
			}
			
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList<AppointmentBean> list = new ArrayList<AppointmentBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new AppointmentBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setScheduleId(rs.getLong(6));
				bean.setDate(rs.getDate(7));
				bean.setAllergy(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search Appointment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model search End");
		return list;
	}

	

	public List<AppointmentBean> list() throws ApplicationException {
		return list(0, 0);
	}


	public List<AppointmentBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.info("Model list Started");
		ArrayList<AppointmentBean> list = new ArrayList<AppointmentBean>();
		StringBuffer sql = new StringBuffer("select * from Appointment");
	
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
				AppointmentBean bean = new AppointmentBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setScheduleId(rs.getLong(6));
				bean.setDate(rs.getDate(7));
				bean.setAllergy(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of Appointment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.info("Model list End");
		return list;

	}



}
