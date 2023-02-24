package com.zgw.controller;

import com.zgw.mapper.AdminMapper;
import com.zgw.mapper.StudentMapper;
import com.zgw.mapper.TeacherMapper;
import com.zgw.pojo.Administrator;
import com.zgw.pojo.Student;
import com.zgw.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: zgw
 * @Date: 2021/4/12 - 04 - 12 - 16:21
 * @Description: com.zgw.controller
 * @version: 1.0
 */

@Controller
public class LoginController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @Param("ident") String ident,
                        Model model,
                        HttpSession session){


        int userid = Integer.parseInt(username);

        if(ident.equals("student")){
            HashMap<String, Object> map = new HashMap<>();
            map.put("studentid",userid);
            map.put("studentpassword",password);
            List<Student> students = studentMapper.selectByMap(map);

            if(!students.isEmpty()){

                session.setAttribute("loginUser",students.get(0).getStudentname());
                String sid = Integer.toString(students.get(0).getStudentid());
                session.setAttribute("userid",sid);
                model.addAttribute("displace","");

                return "student";
            }else {
                model.addAttribute("logintMsg","请正确填写学生信息");
                return "index";
            }

        }else if(ident.equals("teacher")){
            HashMap<String, Object> map = new HashMap<>();
            map.put("teacherid",userid);
            map.put("teacherpassword",password);

            List<Teacher> teachers = teacherMapper.selectByMap(map);

            if(!teachers.isEmpty()){
                session.setAttribute("loginUser",teachers.get(0).getTeachername());
                String tid = Integer.toString(teachers.get(0).getTeacherid());
                session.setAttribute("userid",tid);
                model.addAttribute("displace","");
                return "teacher";
            }else {
                model.addAttribute("logintMsg","请正确填写教师信息");
                return "index";
            }



        }else if(ident.equals("admin")){
            HashMap<String, Object> map = new HashMap<>();
            map.put("adminid",userid);
            map.put("adminpassword",password);

            List<Administrator> ads = adminMapper.selectByMap(map);

            if(!ads.isEmpty()){
                session.setAttribute("loginUser",ads.get(0).getAdminname());
                String aid = Integer.toString(ads.get(0).getAdminid());
                session.setAttribute("userid",aid);
                model.addAttribute("displace","");
                return "admin";
            }else{
                model.addAttribute("logintMsg","请正确填写管理员信息");
                return "index";
            }


        }else {
            model.addAttribute("logintMsg","请正确填写信息");
            return "index";
        }

        /*if(!StringUtils.isEmpty(username) && "123456".equals(password)){
            session.setAttribute("loginUser",username);
            return "admin";
        }else{
            //告诉用户，登录失败
            model.addAttribute("msg","用户名或密码错误");
            return "index";
        }*/

    }
}
