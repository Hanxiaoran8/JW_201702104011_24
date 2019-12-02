//49.234.233.193
package cn.edu.sdjzu.xg.bysj.controller.basic.department;


import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.School;

import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/department.ctl")
public class DepartmentController extends HttpServlet {
    //49.234.233.193
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        String department_json = JsonUtil.getJson(request);
        JSONObject jsonObject = JSONObject.parseObject(department_json);
        Department departmentToAdd = JSON.parseObject(department_json, Department.class);
        int schoolId = Integer.parseInt(jsonObject.getString("school_id"));
        System.out.println(schoolId);
        School school = null;
        try {
            school = SchoolService.getInstance().find(schoolId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        departmentToAdd.setSchool(school);
        JSONObject message = new JSONObject();
        try {
            DepartmentService.getInstance().add(departmentToAdd);
            message.put("message","添加成功");
        } catch (SQLException e) {
            message.put("message","数据库操作异常");
        } catch (Exception e){
            message.put("message","网络异常");
        }
        response.getWriter().println(message);
    }

    //49.234.233.193
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String id_str = request.getParameter("id");
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            if (id_str == null) {
                responseDepartments(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseDepartment(id, response);
            }
            message.put("message", "查询成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
        response.getWriter().println(message);
    }
    //49.234.233.193
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        JSONObject message = new JSONObject();
        try {
            DepartmentService.getInstance().delete(id);
            message.put("message", "删除成功");
        } catch (SQLException e) {
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        } catch (Exception e){
            message.put("message", "网络异常");
        }
        response.getWriter().println(message);
    }
    //49.234.233.193
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String department_json = JsonUtil.getJson(request);
        Department departmentToAdd = JSON.parseObject(department_json, Department.class);
        JSONObject message = new JSONObject();
        try {
            DepartmentService.getInstance().update(departmentToAdd);
            message.put("message", "更新成功");
        } catch (SQLException e) {
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        } catch (Exception e){
            message.put("message", "网络异常");
        }
    }
    private void responseDepartment(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Department department = DepartmentService.getInstance().find(id);
        String department_json = JSON.toJSONString(department);
        response.getWriter().println(department_json);
    }
    private void responseDepartments(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Collection<Department> departments = DepartmentService.getInstance().getAll();
        String departments_json = JSON.toJSONString(departments);

        response.getWriter().println(departments_json);
    }
}
