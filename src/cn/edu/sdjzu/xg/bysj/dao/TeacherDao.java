package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import cn.edu.sdjzu.xg.bysj.service.DegreeService;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import util.JdbcHelper;
import util.ShowTable;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class TeacherDao {
	private static TeacherDao teacherDao=new TeacherDao();
	private TeacherDao(){}
	public static TeacherDao getInstance(){
		return teacherDao;
	}

	public Collection<Teacher> findAll() throws SQLException {
		Collection<Teacher> teachers = new TreeSet<Teacher>();
		Connection connection = JdbcHelper.getConn();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from teacher");
		while(resultSet.next()){
			System.out.print("id = " + resultSet.getInt("id"));
			System.out.print(",");
			System.out.print("name = " + resultSet.getString("name"));
			System.out.print(",");
			System.out.print("proftitle_id = " + resultSet.getString("proftitle_id"));
			System.out.print(",");
			System.out.print("degree_id = " + resultSet.getString("degree_id"));
			System.out.print(",");
			System.out.print("department_id = " + resultSet.getString("department_id"));
			System.out.println(".");
			Teacher teacher = new Teacher(
					resultSet.getInt("id"),
					resultSet.getString("name"),
					ProfTitleService.getInstance().find(resultSet.getInt("proftitle_id")),
					DegreeService.getInstance().find(resultSet.getInt("degree_id")),
					DepartmentService.getInstance().find(resultSet.getInt("department_id")));
			teachers.add(teacher);
		}
		return teachers;
	}
	
	public Teacher find(Integer id) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "SELECT * FROM teacher where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		resultSet.next();

		Teacher teacher = new Teacher(
				resultSet.getInt("id"),
				resultSet.getString("name"),
				ProfTitleService.getInstance().find(resultSet.getInt("proftitle_id")),
				DegreeService.getInstance().find(resultSet.getInt("degree_id")),
				DepartmentService.getInstance().find(resultSet.getInt("department_id"))
		);
		return teacher;
	}
	
	public void update(Teacher teacher) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "UPDATE teacher SET name=?where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setString(1,teacher.getName());

		pstmt.setInt(2,teacher.getId());
		pstmt.executeUpdate();
		JdbcHelper.close(pstmt,connection);
	}
	
	public void add(Teacher teacher) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "INSERT INTO teacher(id,name,proftitle_id,degree_id,department_id) VALUES" +
				" (?,?,?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);
		pstmt.setInt(1,teacher.getId());
		pstmt.setString(2, teacher.getName());
		pstmt.setInt(3,teacher.getTitle().getId());
		pstmt.setInt(4,teacher.getDegree().getId());
		pstmt.setInt(5,teacher.getDepartment().getId());

		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("添加了 " + affectedRowNum +" 行记录");

		JdbcHelper.close(pstmt,connection);	}

	public void delete(Integer id) throws SQLException {
		Teacher teacher = this.find(id);
		this.delete(teacher);
	}
	
	public void delete(Teacher teacher) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "DELETE FROM teacher WHERE id = ?";
		PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);
		pstmt.setInt(1,teacher.getId());
		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("删除了 " + affectedRowNum +" 行记录");

		JdbcHelper.close(pstmt,connection);
	}
	
	
	
}
