//49.234.233.193
package cn.edu.sdjzu.xg.bysj.controller.basic.school;

import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import util.Helper;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
//映射的url分两部分：实体名称(学院)和动作名称(列表),ctl说明这个url是一个controller(servlet)
@WebServlet("/school.ctl")
public class SchoolController extends HttpServlet {
    //49.234.233.193
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        String school_json = JsonUtil.getJson(request);
        School schoolToAdd = JSON.parseObject(school_json,School.class);
        JSONObject message = new JSONObject();
        try {
            SchoolService.getInstance().add(schoolToAdd);
            message.put("message","添加成功");
        } catch (SQLException e) {
            e.printStackTrace();
            message.put("message","数据库操作异常");
        } catch (Exception e){
            message.put("message","网络异常");
        }

        response.getWriter().println(message);

    }
    //49.234.233.193
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String id_str = request.getParameter("id");
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            if (id_str == null) {
                responseSchools(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseSchool(id, response);
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        JSONObject message = new JSONObject();
        try {
            SchoolService.getInstance().delete(id);
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
        String school_json = JsonUtil.getJson(request);
        School schoolToAdd = JSON.parseObject(school_json, School.class);
        JSONObject message = new JSONObject();
        try {
            SchoolService.getInstance().update(schoolToAdd);
            message.put("message", "更新成功");
        } catch (SQLException e) {
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        } catch (Exception e){
            message.put("message", "网络异常");
        }


        response.getWriter().println(message);
    }

    private void responseSchool(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        School school = SchoolService.getInstance().find(id);
        String school_json = JSON.toJSONString(school);
        response.getWriter().println(school_json);
    }
    private void responseSchools(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Collection<School> schools = SchoolService.getInstance().findAll();
        String schools_json = JSON.toJSONString(schools);

        response.getWriter().println(schools_json);
    }
}
