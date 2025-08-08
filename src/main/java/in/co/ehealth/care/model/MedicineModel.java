package in.co.ehealth.care.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import in.co.ehealth.care.bean.MedicineBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DatabaseException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.util.JDBCDataSource;



/**
 * JDBC Implementation of MedicineModel
 */

public class MedicineModel {
	private static Logger log = Logger.getLogger(MedicineModel.class.getName());
	

	public Integer nextPK() throws DatabaseException {
		log.info("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM Medicine");
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

	
	public long add(MedicineBean bean) throws ApplicationException, DuplicateRecordException {
		
		Connection conn = null;
		int pk = 0;

		MedicineBean existbean = findByName(bean.getName());

		if (existbean != null) {
			throw new DuplicateRecordException("MedicineName is already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Medicine VALUES(?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getCompanyName());
			pstmt.setString(4, bean.getPrice());
			pstmt.setString(5, bean.getStatus());
			pstmt.setString(6, bean.getDescription());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
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
			throw new ApplicationException("Exception : Exception in add Medicine");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}

	
	public void delete(MedicineBean bean) throws ApplicationException {
		
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Medicine WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete Medicine");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}


	public MedicineBean findByName(String name) throws ApplicationException {
		log.info("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Medicine WHERE NAME=?");
		MedicineBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MedicineBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCompanyName(rs.getString(3));
				bean.setPrice(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setDescription(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Medicine by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByLogin End");
		return bean;
	}

	public MedicineBean findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Medicine WHERE ID=?");
		MedicineBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MedicineBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCompanyName(rs.getString(3));
				bean.setPrice(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setDescription(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Medicine by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByPK End");
		return bean;
	}

	

	public void update(MedicineBean bean) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Connection conn = null;

		MedicineBean beanExist = findByName(bean.getName());
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("MedicineName is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE Medicine SET NAME=?,CompanyName=?,price=?,status=?,Description=?,"
					+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getCompanyName());
			pstmt.setString(3, bean.getPrice());
			pstmt.setString(4, bean.getStatus());
			pstmt.setString(5, bean.getDescription());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());
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
			throw new ApplicationException("Exception in updating Medicine ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model update End");
	}

	

	public List<MedicineBean> search(MedicineBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}


	public List<MedicineBean> search(MedicineBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Medicine WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			
			if (bean.getCompanyName() != null && bean.getCompanyName().length() > 0) {
				sql.append(" AND CompanyName like '" + bean.getCompanyName() + "%'");
			}
			
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList<MedicineBean> list = new ArrayList<MedicineBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MedicineBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCompanyName(rs.getString(3));
				bean.setPrice(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setDescription(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Medicine");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model search End");
		return list;
	}

	

	public List<MedicineBean> list() throws ApplicationException {
		return list(0, 0);
	}


	public List<MedicineBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.info("Model list Started");
		ArrayList<MedicineBean> list = new ArrayList<MedicineBean>();
		StringBuffer sql = new StringBuffer("select * from Medicine");
	
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
				MedicineBean bean = new MedicineBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCompanyName(rs.getString(3));
				bean.setPrice(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setDescription(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting list of Medicine");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.info("Model list End");
		return list;

	}



}
