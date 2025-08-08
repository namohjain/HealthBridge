package in.co.ehealth.care.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import in.co.ehealth.care.bean.DoctorBean;
import in.co.ehealth.care.bean.PatientBean;
import in.co.ehealth.care.bean.TestReportBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DatabaseException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.util.JDBCDataSource;


public class TestReportModel {
	private static Logger log = Logger.getLogger(TestReportModel.class.getName());
	

	public Integer nextPK() throws DatabaseException {
		log.info("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM TestReport");
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

	
	public long add(TestReportBean bean) throws ApplicationException, DuplicateRecordException {
		
		Connection conn = null;
		int pk = 0;

		
		DoctorBean wBean=new DoctorModel().findByPK(bean.getDoctorId());
		PatientBean pBean=new PatientModel().findByPK(bean.getPatientId());

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TestReport VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getPatientId());
			pstmt.setString(3, pBean.getFirstName()+" "+pBean.getLastName());
			pstmt.setLong(4, bean.getDoctorId());
			pstmt.setString(5, wBean.getFirstName()+" "+wBean.getLastName());
			pstmt.setString(6, bean.getFileName());
			pstmt.setString(7, bean.getDescription());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.setString(12, bean.getFileType());
			pstmt.setBytes(13, bean.getReportFile());
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
			throw new ApplicationException("Exception : Exception in add TestReport");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}

	
	public void delete(TestReportBean bean) throws ApplicationException {
		
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 			
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM TestReport WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete TestReport");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}



	@SuppressWarnings("unused")
	public TestReportBean findByDoctorName(String doctorName) throws ApplicationException {
		log.info("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM TestReport WHERE doctorName=?");
		TestReportBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, doctorName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TestReportBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setFileName(rs.getString(6));
				bean.setDescription(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting TestReport by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByLogin End");
		return bean;
	}


	@SuppressWarnings("unused")
	public TestReportBean findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM TestReport WHERE ID=?");
		TestReportBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TestReportBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setFileName(rs.getString(6));
				bean.setDescription(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				bean.setFileType(rs.getString(12));
				bean.setReportFile(rs.getBytes(13));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting TestReport by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByPK End");
		return bean;
	}

	

	public void update(TestReportBean bean) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Connection conn = null;

		/*
		 * TestReportBean beanExist =
		 * findByTestReportNo(bean.getTestReportNo()); if (beanExist != null &&
		 * !(beanExist.getId() == bean.getId())) { throw new
		 * DuplicateRecordException("TestReportNo is already exist"); }
		 */
		DoctorBean wBean=new DoctorModel().findByPK(bean.getDoctorId());
		PatientBean pBean=new PatientModel().findByPK(bean.getPatientId());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE TestReport SET patientId=?,patientName=?,doctorId=?,DoctorName=?,fileName=?,description=?,"
					+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1, bean.getPatientId());
			pstmt.setString(2, pBean.getFirstName()+" "+pBean.getLastName());
			pstmt.setLong(3, bean.getDoctorId());
			pstmt.setString(4, wBean.getFirstName()+" "+wBean.getLastName());
			pstmt.setString(5, bean.getFileName());
			pstmt.setString(6, bean.getDescription());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.setLong(11, bean.getId());
			pstmt.setString(12, bean.getFileType());
			pstmt.setBytes(13, bean.getReportFile());
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
			throw new ApplicationException("Exception in updating TestReport ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model update End");
	}

	

	public List<TestReportBean> search(TestReportBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}


	@SuppressWarnings("unused")
	public List<TestReportBean> search(TestReportBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
	 
		StringBuffer sql = new StringBuffer("SELECT * FROM TestReport WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getPatientId() > 0) {
				sql.append(" AND patientId = " + bean.getPatientId());
			}
			
			if (bean.getDoctorId() > 0) {
				sql.append(" AND doctorId = " + bean.getDoctorId());
			}
			
			
			if (bean.getDoctorName()!= null && bean.getDoctorName().length() > 0) {
				sql.append(" AND doctorName like '" + bean.getDoctorName() + "%'");
			}
			if (bean.getPatientName()!= null && bean.getPatientName().length() > 0) {
				sql.append(" AND PatientName like '" + bean.getPatientName() + "%'");
			}
			
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList<TestReportBean> list = new ArrayList<TestReportBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TestReportBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setFileName(rs.getString(6));
				bean.setDescription(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search TestReport");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model search End");
		return list;
	}

	

	public List<TestReportBean> list() throws ApplicationException {
		return list(0, 0);
	}



	@SuppressWarnings("unused")
	public List<TestReportBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.info("Model list Started");
		ArrayList<TestReportBean> list = new ArrayList<TestReportBean>();
		StringBuffer sql = new StringBuffer("select * from TestReport ");
	
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
				TestReportBean bean = new TestReportBean();
				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setFileName(rs.getString(6));
				bean.setDescription(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of TestReport");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.info("Model list End");
		return list;

	}



}
