package in.co.ehealth.care.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import in.co.ehealth.care.bean.PrescriptionBean;
import in.co.ehealth.care.bean.DoctorBean;
import in.co.ehealth.care.bean.PatientBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DatabaseException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.util.JDBCDataSource;

/**
 * JDBC Implementation of PrescriptionModel
 */

public class PrescriptionModel {
	private static Logger log = Logger.getLogger(PrescriptionModel.class.getName());
	

	public Integer nextPK() throws DatabaseException {
		log.info("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM Prescription");
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
	public long add(PrescriptionBean bean) throws ApplicationException, DuplicateRecordException {
		
		Connection conn = null;
		int pk = 0;

		
		DoctorBean wBean=new DoctorModel().findByPK(bean.getDoctorId());
		PatientBean pBean=new PatientModel().findByPK(bean.getPatientId());

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Prescription VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getPatientId());
			pstmt.setString(3, pBean.getFirstName()+" "+pBean.getLastName());
			pstmt.setLong(4, bean.getDoctorId());
			pstmt.setString(5, wBean.getFirstName()+" "+wBean.getLastName());
			pstmt.setDate(6, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(7, bean.getPrescription());
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
			throw new ApplicationException("Exception : Exception in add Prescription");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}

	
	public void delete(PrescriptionBean bean) throws ApplicationException {
		
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 			
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Prescription WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete Prescription");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}



	@SuppressWarnings("unused")
	public PrescriptionBean findByDoctorName(String doctorName) throws ApplicationException {
		log.info("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Prescription WHERE doctorName=?");
		PrescriptionBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, doctorName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PrescriptionBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setDate(rs.getDate(6));
				bean.setPrescription(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Prescription by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByLogin End");
		return bean;
	}


	@SuppressWarnings("unused")
	public PrescriptionBean findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Prescription WHERE ID=?");
		PrescriptionBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PrescriptionBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setDate(rs.getDate(6));
				bean.setPrescription(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Prescription by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByPK End");
		return bean;
	}

	

	@SuppressWarnings("deprecation")
	public void update(PrescriptionBean bean) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Connection conn = null;

		/*
		 * PrescriptionBean beanExist =
		 * findByPrescriptionNo(bean.getPrescriptionNo()); if (beanExist != null &&
		 * !(beanExist.getId() == bean.getId())) { throw new
		 * DuplicateRecordException("PrescriptionNo is already exist"); }
		 */
		DoctorBean wBean=new DoctorModel().findByPK(bean.getDoctorId());
		PatientBean pBean=new PatientModel().findByPK(bean.getPatientId());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE Prescription SET patientId=?,patientName=?,doctorId=?,DoctorName=?,date=?,prescripion=?,"
					+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1, bean.getPatientId());
			pstmt.setString(2, pBean.getFirstName()+" "+pBean.getLastName());
			pstmt.setLong(3, bean.getDoctorId());
			pstmt.setString(4, wBean.getFirstName()+" "+wBean.getLastName());
			pstmt.setDate(5, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(6, bean.getPrescription());
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
			throw new ApplicationException("Exception in updating Prescription ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model update End");
	}

	

	public List<PrescriptionBean> search(PrescriptionBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}


	@SuppressWarnings("unused")
	public List<PrescriptionBean> search(PrescriptionBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Prescription WHERE 1=1");

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

		ArrayList<PrescriptionBean> list = new ArrayList<PrescriptionBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PrescriptionBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setDate(rs.getDate(6));
				bean.setPrescription(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search Prescription");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model search End");
		return list;
	}

	

	public List<PrescriptionBean> list() throws ApplicationException {
		return list(0, 0);
	}



	@SuppressWarnings("unused")
	public List<PrescriptionBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.info("Model list Started");
		ArrayList<PrescriptionBean> list = new ArrayList<PrescriptionBean>();
		StringBuffer sql = new StringBuffer("select * from Prescription");
	
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
				PrescriptionBean bean = new PrescriptionBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setDate(rs.getDate(6));
				bean.setPrescription(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of Prescription");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.info("Model list End");
		return list;

	}



}
