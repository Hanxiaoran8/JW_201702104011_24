//49.234.233.193
package cn.edu.sdjzu.xg.bysj.controller.basic.degree;

import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.DegreeService;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/degree.ctl")
public class DegreeController extends HttpServlet {
    //49.234.233.193
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String id_str = request.getParameter("id");
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            if (id_str == null) {
                responseDegrees(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseDegree(id, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        String degree_json = JsonUtil.getJson(request);
        Degree degreeToAdd = JSON.parseObject(degree_json, Degree.class);
        JSONObject message = new JSONObject();
        try {
            DegreeService.getInstance().add(degreeToAdd);
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        JSONObject message = new JSONObject();
        try {
            DegreeService.getInstance().delete(id);
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
        String degree_json = JsonUtil.getJson(request);
        Degree degreeToAdd = JSON.parseObject(degree_json, Degree.class);
        JSONObject message = new JSONObject();
        try {
            DegreeService.getInstance().update(degreeToAdd);
            message.put("message", "更新成功");
        } catch (SQLException e) {
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        } catch (Exception e){
            message.put("message", "网络异常");
        }


        response.getWriter().println(message);
    }
    //49.234.233.193
    private void responseDegree(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Degree degree = DegreeService.getInstance().find(id);
        String degree_json = JSON.toJSONString(degree);
        response.getWriter().println(degree_json);
    }
    private void responseDegrees(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Collection<Degree> degrees = DegreeService.getInstance().findAll();
        String degrees_json = JSON.toJSONString(degrees);

        response.getWriter().println(degrees_json);
    }
}
