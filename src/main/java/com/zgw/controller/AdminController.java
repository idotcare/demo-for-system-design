package com.zgw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zgw.mapper.*;
import com.zgw.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Auther: zgw
 * @Date: 2021/4/12 - 04 - 12 - 19:08
 * @Description: com.zgw.controller
 * @version: 1.0
 */

@Controller
public class AdminController {

    @Autowired
    private TeacherMapper tm;

    @Autowired
    private StudentMapper sm;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private StudyMapper studyMapper;

    @Autowired
    private TeachMapper teachMapper;


    //=========================教师增删查改=================================

    @RequestMapping("/selectTeacher")
    public String selectAllTeacher(Model model, @Param("id") String id) {

        int userid = Integer.parseInt(id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", userid);

        List<Teacher> ts = tm.selectByMap(map);
        //ts.forEach(System.out::println);//测试代码
        model.addAttribute("ts", ts);
        model.addAttribute("displace", "teacher");
        return "admin";
    }

    @RequestMapping("/toAddTeacherPages")
    public String toAddTeacherPage(@Param("aid") String aid, Model model) {
        model.addAttribute("aid", aid);
        return "addTeacher";
    }

    @RequestMapping("/addTeacher")
    public String addTeacher(@Param("id") String id,
                             @Param("name") String name,
                             @Param("sex") String sex,
                             @Param("phone") String phone,
                             @Param("major") String major,
                             @Param("depart") String depart,
                             @Param("pwd") String pwd,
                             @Param("aid") String aid,
                             Model model) {
        int tid = Integer.parseInt(id);
        int adid = Integer.parseInt(aid);
        Teacher teacher = new Teacher(tid, name, sex, phone, major, depart, pwd, adid);
        tm.insert(teacher);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", adid);

        List<Teacher> ts = tm.selectByMap(map);
        model.addAttribute("ts", ts);
        model.addAttribute("displace", "teacher");

        return "admin";
    }

    @RequestMapping("/deleteTeacher")
    public String deleteTeacher(@Param("tid") int tid,
                                @Param("aid") String aid,
                                Model model) {
        int adid = Integer.parseInt(aid);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("teacherid", tid);
        map1.put("adminid", adid);
        tm.deleteByMap(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("adminid", adid);
        List<Teacher> ts = tm.selectByMap(map2);

        model.addAttribute("ts", ts);
        model.addAttribute("displace", "teacher");

        return "admin";
    }

    @RequestMapping("/toupdateTeacherPages")
    public String toUpdateTeacherPage(HttpSession session,
                                      @Param("tid") int tid,
                                      @Param("aid") String aid) {

        int adid = Integer.parseInt(aid);

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper
                .eq("teacherid", tid)
                .eq("adminid", adid);
        Teacher teacher = tm.selectOne(wrapper);

        session.setAttribute("teacher", teacher);

        return "updateTeacher";
    }

    @RequestMapping("/updateTeacher")
    public String updateTeacher(@Param("id") String id,
                                @Param("name") String name,
                                @Param("sex") String sex,
                                @Param("phone") String phone,
                                @Param("major") String major,
                                @Param("depart") String depart,
                                @Param("pwd") String pwd,
                                @Param("aid") String aid,
                                Model model) {

        int tid = Integer.parseInt(id);
        int adid = Integer.parseInt(aid);
        Teacher teacher = new Teacher(tid, name, sex, phone, major, depart, pwd, adid);

        tm.updateById(teacher);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", adid);

        List<Teacher> ts = tm.selectByMap(map);
        model.addAttribute("ts", ts);
        model.addAttribute("displace", "teacher");

        return "admin";
    }


    //========================学生增删查改===============================

    @RequestMapping("/selectStudent")
    public String selectAllStudent(Model model, @Param("id") String id) {

        int userid = Integer.parseInt(id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", userid);

        List<Student> ss = sm.selectByMap(map);
        //ss.forEach(System.out::println);//测试代码
        model.addAttribute("ss", ss);

        model.addAttribute("displace", "student");

        return "admin";
    }

    @RequestMapping("/toAddStudentPages")
    public String toAddStudentPage(@Param("aid") String aid, Model model) {
        model.addAttribute("aid", aid);
        return "addStudent";
    }

    @RequestMapping("/addStudent")
    public String addStudent(@Param("id") String id,
                             @Param("name") String name,
                             @Param("sex") String sex,
                             @Param("idnum") String idnum,
                             @Param("grade") String grade,
                             @Param("pwd") String pwd,
                             @Param("aid") String aid,
                             Model model) {
        int sid = Integer.parseInt(id);
        int adid = Integer.parseInt(aid);

        Student student = new Student(sid, name, sex, idnum, grade, pwd, adid);

        sm.insert(student);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", adid);

        List<Student> ss = sm.selectByMap(map);
        model.addAttribute("ss", ss);
        model.addAttribute("displace", "student");

        return "admin";
    }

    @RequestMapping("/toupdateStudentPages")
    public String toupdateStudentPage(@Param("sid") int sid, @Param("aid") String aid, Model model) {

        int adid = Integer.parseInt(aid);

        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper
                .eq("studentid", sid)
                .eq("adminid", adid);
        Student student = sm.selectOne(wrapper);

        model.addAttribute("student", student);
        model.addAttribute("aid", aid);

        return "updateStudent";
    }

    @RequestMapping("/updateStudent")
    public String updateStudent(@Param("id") String id,
                                @Param("name") String name,
                                @Param("sex") String sex,
                                @Param("idnum") String idnum,
                                @Param("grade") String grade,
                                @Param("pwd") String pwd,
                                @Param("aid") String aid,
                                Model model) {
        int sid = Integer.parseInt(id);
        int adid = Integer.parseInt(aid);

        Student student = new Student(sid, name, sex, idnum, grade, pwd, adid);

        sm.updateById(student);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", adid);

        List<Student> ss = sm.selectByMap(map);
        model.addAttribute("ss", ss);
        model.addAttribute("displace", "student");

        return "admin";
    }

    @RequestMapping("/deleteStudent")
    public String deleteStudent(@Param("sid") int sid, @Param("aid") String aid, Model model) {
        int adid = Integer.parseInt(aid);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("studentid", sid);
        map1.put("adminid", adid);
        sm.deleteByMap(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("adminid", adid);
        List<Student> ss = sm.selectByMap(map2);

        model.addAttribute("ss", ss);
        model.addAttribute("displace", "student");

        return "admin";

    }

    //=========================课程表的增删查改=========================================


    @RequestMapping("/selectSubject")
    public String selectAllSubject(Model model, @Param("id") String id) {

        int aid = Integer.parseInt(id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", aid);

        List<Subject> subjects = subjectMapper.selectByMap(map);
        model.addAttribute("subjects", subjects);

        model.addAttribute("displace", "subject");

        return "admin";
    }

    @RequestMapping("/toAddSubjectPages")
    public String toAddSubjectPage(@Param("aid") String aid, Model model) {

        model.addAttribute("aid", aid);

        return "addSubject";
    }

    @RequestMapping("/addSubject")
    public String addStudent(@Param("id") String id,
                             @Param("name") String name,
                             @Param("aid") String aid,
                             Model model) {

        int subid = Integer.parseInt(id);
        int adid = Integer.parseInt(aid);

        Subject subject = new Subject(subid, name, adid);

        subjectMapper.insert(subject);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", adid);

        List<Subject> subjects = subjectMapper.selectByMap(map);
        model.addAttribute("subjects", subjects);

        model.addAttribute("displace", "subject");

        return "admin";
    }

    @RequestMapping("/toupdateSubjectPages")
    public String toupdateSubjectPage(@Param("subid") int subid,
                                      @Param("aid") String aid,
                                      Model model) {

        int adid = Integer.parseInt(aid);
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();

        wrapper
                .eq("subjectid", subid)
                .eq("adminid", adid);

        Subject subject = subjectMapper.selectOne(wrapper);
        model.addAttribute("subject", subject);
        return "updateSubject";
    }

    @RequestMapping("/updateSubject")
    public String updateSubject(@Param("id") String id,
                                @Param("name") String name,
                                @Param("aid") String aid,
                                Model model) {

        int subid = Integer.parseInt(id);
        int adid = Integer.parseInt(aid);
        Subject subject = new Subject(subid, name, adid);

        subjectMapper.updateById(subject);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", adid);

        List<Subject> subjects = subjectMapper.selectByMap(map);
        model.addAttribute("subjects", subjects);

        model.addAttribute("displace", "subject");

        return "admin";
    }

    @RequestMapping("/deleteSubject")
    public String deleteSubject(@Param("subid") int subid,
                                @Param("aid") String aid,
                                Model model) {
        int adid = Integer.parseInt(aid);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("subjectid", subid);
        map1.put("adminid", adid);
        subjectMapper.deleteByMap(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("adminid", adid);
        List<Subject> subjects = subjectMapper.selectByMap(map2);

        model.addAttribute("subjects", subjects);
        model.addAttribute("displace", "subject");

        return "admin";
    }

    //================维护学习表==========================================

    //查看某门课程的全部学生(按照课程号查找)

    /**
     * 查找
     * 1.输入课程号/课程号
     * 2.去课程表查找课程号
     * 3.使用课程号去学习表查找学生学号
     * 使用selectBatchIds查出全部学生显示在网页上
     * 添加学生
     * 1.输入学号/学生姓名
     * 2.去学生表查找学生
     * 3.确认学生信息正确后将学生学号、课程编号添加到学习表
     * 删除学生
     * 1.在上述查出的学生列表中点击删除按钮
     * 2.在学习表中查询到此学号、此课程编号并删除
     */

    //为此课程添加学生、删除学生

    //查看某个学生的全部课程(按照学号查找学生)
    //为此学生添加课程、删除课程
    @RequestMapping("/selectStudy")
    public String selectStudy(Model model) {

        model.addAttribute("displace", "study");
        return "admin";
    }

    @RequestMapping("/selectSubjectForTeacherStudents")
    public String selectSubjectForTeacherStudents(@Param("aid") String aid,
                                                  Model model){
        int adid = Integer.parseInt(aid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid",adid);
        List<Subject> subjects = subjectMapper.selectByMap(map);
        if (subjects.isEmpty()){
            model.addAttribute("subjectMsg","暂时没有课程可供查看，可先去管理课程信息处添加课程。");
            model.addAttribute("displace", "study");
            return "admin";
        }else {
            model.addAttribute("subjects",subjects);
            model.addAttribute("displaceteach","subject");
            model.addAttribute("displace", "study");
            return "admin";
        }

    }


    @RequestMapping("/selectSubjectTeacherBySubjectId")
    public String selectSubjectTeacherBySubjectId(@Param("subid") int subid,
                                                  @Param("aid") int aid,
                                                  Model model){

        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper
                .eq("subjectid", subid)
                .eq("adminid", aid);
        Subject subject = subjectMapper.selectOne(wrapper);
        if (null == subject) {
            model.addAttribute("displace", "study");
            model.addAttribute("subjectMsg", "查无此课程");
            return "admin";
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("subjectid", subid);
            map.put("adminid", aid);
            List<Teach> teaches = teachMapper.selectByMap(map);
            if (!teaches.isEmpty()) {
                ArrayList<Integer> list = new ArrayList<>();
                for (Teach teach : teaches) {
                    list.add(teach.getTeacherid());
                }
                List<Teacher> teachers = tm.selectBatchIds(list);
                model.addAttribute("teachers", teachers);
                model.addAttribute("subject", subject);
                model.addAttribute("displace", "study");
                model.addAttribute("displaceteach", "teacher");
                return "admin";
            } else {
                model.addAttribute("displace", "study");
                model.addAttribute("subjectMsg", "无此课程任课教师相关信息");
                return "admin";
            }

        }
    }

    @RequestMapping("/selectStudentForClass")
    public String selectStudentForClass(@Param("aid") String aid,
                                        Model model){
        int adid = Integer.parseInt(aid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid",adid);
        List<Student> students = sm.selectByMap(map);

        if (students.isEmpty()){
            model.addAttribute("subjectMsg", "暂时没有学生，可前往维护学生信息处添加学生");
        }else {
            model.addAttribute("displaceteach","student");
            model.addAttribute("students",students);
        }
        model.addAttribute("displace", "study");
        return "admin";
    }

    @RequestMapping("/selectTheStudentsClasses")
    public String selectTheStudentsClasses(@Param("aid") int aid,
                                           @Param("sid") int sid,
                                           Model model){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("studentid",sid);
        Student student = sm.selectOne(wrapper);
        model.addAttribute("student",student);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid",aid);
        map.put("studentid",sid);

        List<Study> studies = studyMapper.selectByMap(map);

        if (studies.isEmpty()){
            model.addAttribute("subjectMsg", "该学生暂时没有加入任何教学班");
        }else {
            ArrayList<StudyClass> studyClasses = new ArrayList<>();
            for (Study study : studies) {
                QueryWrapper<Teacher> wrapper1 = new QueryWrapper<>();
                QueryWrapper<Subject> wrapper2 = new QueryWrapper<>();
                wrapper1.eq("teacherid",study.getTeacherid());
                wrapper2.eq("subjectid",study.getSubjectid());
                Teacher teacher = tm.selectOne(wrapper1);
                Subject subject = subjectMapper.selectOne(wrapper2);
                StudyClass studyClass = new StudyClass(teacher, subject);
                studyClasses.add(studyClass);
            }
            model.addAttribute("studyClasses",studyClasses);
        }
        model.addAttribute("displaceteach","studyclass");
        model.addAttribute("displace", "study");
        return "admin";
    }

    @RequestMapping("/joinNewStudyClass")
    public String joinNewStudyClass(@Param("aid") int aid,
                                    @Param("sid") int sid,
                                    Model model){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("studentid",sid);
        Student student = sm.selectOne(wrapper);
        model.addAttribute("student",student);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid",aid);
        List<Teach> teaches = teachMapper.selectByMap(map);

        if (teaches.isEmpty()){
            model.addAttribute("subjectMsg", "暂时没有教学班，需要去管理教学信息处添加");
        }else {
            ArrayList<Integer> list = new ArrayList<>();
            for (Teach teach : teaches) {
                list.add(teach.getSubjectid());
            }
            List<Subject> subjects = subjectMapper.selectBatchIds(list);
            model.addAttribute("subjects",subjects);
            model.addAttribute("displaceteach","studySubject");
        }
        model.addAttribute("displace", "study");
        return "admin";
    }

    @RequestMapping("/selectStudyTeacher")
    public String selectStudyTeacher(@Param("aid") int aid,
                                     @Param("sid") int sid,
                                     @Param("subid") int subid,
                                     Model model){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("studentid",sid);
        Student student = sm.selectOne(wrapper);
        model.addAttribute("student",student);

        QueryWrapper<Subject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("subjectid",subid);
        Subject subject = subjectMapper.selectOne(wrapper1);
        model.addAttribute("subject",subject);

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid",subid);
        List<Teach> teaches = teachMapper.selectByMap(map);
        ArrayList<Integer> list = new ArrayList<>();
        for (Teach teach : teaches) {
            list.add(teach.getTeacherid());
        }
        List<Teacher> teachers = tm.selectBatchIds(list);
        model.addAttribute("teachers",teachers);
        model.addAttribute("displaceteach","studyTeacher");
        model.addAttribute("displace", "study");
        return "admin";
    }

    @RequestMapping("/quitTheStudyClass")
    public String quitTheStudyClass(int aid,
                                    int sid,
                                    int tid,
                                    int subid,
                                    Model model){
        QueryWrapper<Study> queryWrapper = new QueryWrapper<>();
        queryWrapper
                    .eq("adminid",aid)
                    .eq("subjectid",subid)
                    .eq("teacherid",tid)
                    .eq("studentid",sid);
        Study study1 = studyMapper.selectOne(queryWrapper);
        studyMapper.deleteById(study1.getStudyid());

        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("studentid",sid);
        Student student = sm.selectOne(wrapper);
        model.addAttribute("student",student);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid",aid);
        map.put("studentid",sid);

        List<Study> studies = studyMapper.selectByMap(map);

        if (studies.isEmpty()){
            model.addAttribute("subjectMsg", "该学生暂时没有加入任何教学班");
        }else {
            ArrayList<StudyClass> studyClasses = new ArrayList<>();
            for (Study study : studies) {
                QueryWrapper<Teacher> wrapper1 = new QueryWrapper<>();
                QueryWrapper<Subject> wrapper2 = new QueryWrapper<>();
                wrapper1.eq("teacherid",study.getTeacherid());
                wrapper2.eq("subjectid",study.getSubjectid());
                Teacher teacher = tm.selectOne(wrapper1);
                Subject subject = subjectMapper.selectOne(wrapper2);
                StudyClass studyClass = new StudyClass(teacher, subject);
                studyClasses.add(studyClass);
            }
            model.addAttribute("studyClasses",studyClasses);
        }
        model.addAttribute("displaceteach","studyclass");
        model.addAttribute("displace", "study");
        return "admin";
    }

    @RequestMapping("/joinThisNewStudyClass")
    public String joinThisNewStudyClass(@Param("aid") int aid,
                                        @Param("tid") int tid,
                                        @Param("subid") int subid,
                                        @Param("sid") int sid,
                                        Model model){
        QueryWrapper<Study> wrapper3 = new QueryWrapper<>();
        wrapper3
                .eq("adminid",aid)
                .eq("subjectid",subid)
                .eq("teacherid",tid)
                .eq("studentid",sid);
        Study study = studyMapper.selectOne(wrapper3);
        if (null==study){
            Study study1 = new Study();
            study1.setAdminid(aid);
            study1.setSubjectid(subid);
            study1.setTeacherid(tid);
            study1.setStudentid(sid);
            studyMapper.insert(study1);
        }
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("studentid",sid);
        Student student = sm.selectOne(wrapper);
        model.addAttribute("student",student);

        QueryWrapper<Subject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("subjectid",subid);
        Subject subject = subjectMapper.selectOne(wrapper1);
        model.addAttribute("subject",subject);

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid",subid);
        List<Teach> teaches = teachMapper.selectByMap(map);
        ArrayList<Integer> list = new ArrayList<>();
        for (Teach teach : teaches) {
            list.add(teach.getTeacherid());
        }
        List<Teacher> teachers = tm.selectBatchIds(list);
        model.addAttribute("teachers",teachers);
        model.addAttribute("displaceteach","studyTeacher");
        model.addAttribute("displace", "study");
        return "admin";
    }


    @RequestMapping("/selectSubjectTeacherStudents")
    public String selectSubjectTeacherStudents(@Param("aid") String aid,
                                               @Param("subid") int subid,
                                               @Param("tid") int tid,
                                               Model model){
        int adid = Integer.parseInt(aid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid",adid);
        map.put("subjectid",subid);
        map.put("teacherid",tid);

        List<Study> studies = studyMapper.selectByMap(map);

        if(!studies.isEmpty()){

            ArrayList<Integer> list = new ArrayList<>();

            for (Study study : studies) {
                list.add(study.getStudentid());
            }

            List<Student> students = sm.selectBatchIds(list);

            model.addAttribute("students",students);
        }

        model.addAttribute("aid",adid);
        model.addAttribute("subid",subid);
        model.addAttribute("tid",tid);
        return "allSubjectTeacherStudents";
    }

    @RequestMapping("/toAddSubjectTeachersStudentPages")
    public String toAddSubjectTeachersStudentPages(@Param("aid") int aid,
                                                   @Param("subid") int subid,
                                                   @Param("tid") int tid,
                                                   Model model){
        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid",aid);

        List<Student> students = sm.selectByMap(map);

        model.addAttribute("students",students);
        model.addAttribute("aid",aid);
        model.addAttribute("subid",subid);
        model.addAttribute("tid",tid);
        return "addSubjectTeachersStudent";
    }

    @RequestMapping("/addThisStudentToTheClass")
    public String addThisStudentToTheClass(@Param("aid") int aid,
                                           @Param("subid") int subid,
                                           @Param("tid") int tid,
                                           @Param("sid") int sid,
                                           Model model){
        QueryWrapper<Study> wrapper = new QueryWrapper<>();
        wrapper
                .eq("adminid",aid)
                .eq("subjectid",subid)
                .eq("teacherid",tid)
                .eq("studentid",sid);

        Study study = studyMapper.selectOne(wrapper);

        if (null==study){
            Study study1 = new Study();
            study1.setAdminid(aid);
            study1.setSubjectid(subid);
            study1.setTeacherid(tid);
            study1.setStudentid(sid);
            studyMapper.insert(study1);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid",aid);

        List<Student> students = sm.selectByMap(map);

        model.addAttribute("students",students);
        model.addAttribute("aid",aid);
        model.addAttribute("subid",subid);
        model.addAttribute("tid",tid);
        return "addSubjectTeachersStudent";
    }

    @RequestMapping("/removeSubjectTeacherStudent")
    public String removeSubjectTeacherStudent(int aid,
                                              int subid,
                                              int tid,
                                              int sid,
                                              Model model){
        QueryWrapper<Study> wrapper = new QueryWrapper<>();
        wrapper
                .eq("adminid",aid)
                .eq("subjectid",subid)
                .eq("teacherid",tid)
                .eq("studentid",sid);

        Study study1 = studyMapper.selectOne(wrapper);

        studyMapper.deleteById(study1.getStudyid());

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid",aid);
        map.put("subjectid",subid);
        map.put("teacherid",tid);

        List<Study> studies = studyMapper.selectByMap(map);

        if(!studies.isEmpty()){

            ArrayList<Integer> list = new ArrayList<>();

            for (Study study : studies) {
                list.add(study.getStudentid());
            }

            List<Student> students = sm.selectBatchIds(list);

            model.addAttribute("students",students);
        }

        model.addAttribute("aid",aid);
        model.addAttribute("subid",subid);
        model.addAttribute("tid",tid);
        return "allSubjectTeacherStudents";
    }


    //===============维护教学表================================================
    //查看某门课程的全部教师(按照课程号查找)

    /**
     * 查找
     * 1.输入课程号/课程名
     * 2.去课程表查找课程号
     * 3.使用课程号去教学表查找教师工号
     * 4.使用selectBatchIds查出全部教师显示在网页上
     * 添加教师
     * 1.输入工号/教师姓名
     * 2.去教师表查找学生
     * 3.确认教师信息正确后将教师工号、课程编号添加到教学表
     * 删除教师
     * 1.在上述查出的教师列表中点击删除按钮
     * 2.在学习表中查询到此工号、此课程编号并删除
     */

    @RequestMapping("/selectSubjectForTeachers")
    public String selectSubjectForTeachers(@Param("id") String id,
                                           Model model){
        int aid = Integer.parseInt(id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", aid);

        List<Subject> subjects = subjectMapper.selectByMap(map);
        if (!subjects.isEmpty()){
            model.addAttribute("subjects", subjects);
            model.addAttribute("displaceteach","subject");
        }else {
            model.addAttribute("subjectMsg","暂无课程可管理");
        }

        model.addAttribute("displace", "teach");

        return "admin";
    }

    @RequestMapping("/selectTeacherForSubjects")
    public String selectTeacherForSubjects(@Param("id") String id,
                                           Model model){
        int userid = Integer.parseInt(id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", userid);
        List<Teacher> ts = tm.selectByMap(map);
        if(!ts.isEmpty()){
            model.addAttribute("ts", ts);
            model.addAttribute("displaceteach","teacher1");
        }else {
            model.addAttribute("subjectMsg","暂无教师可管理");
        }

        model.addAttribute("displace", "teach");

        return "admin";
    }

    //为此课程添加教师、删除教师

    //查看某个教师的全部课程(按照工号查找教师)
    //为此教师添加课程、删除课程
    @RequestMapping("/selectTeach")
    public String selectTeach(Model model) {

        model.addAttribute("displace", "teach");
        return "admin";
    }

    /**
     * 查找
     * 1.输入课程号/课程名
     * 2.去课程表查找课程号
     * 3.使用课程号去教学表查找教师工号
     * 4.使用selectBatchIds查出全部教师显示在网页上
     */
    @RequestMapping("/selectTeacherBySubjectId")
    public String selectTeacherBySubjectId(@Param("subid") int subid,
                                           @Param("aid") int aid,
                                           Model model) {
        /*int subjectid = Integer.parseInt(subid);
        int adid = Integer.parseInt(aid);*/

        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper
                .eq("subjectid", subid)
                .eq("adminid", aid);
        Subject subject = subjectMapper.selectOne(wrapper);

        if (null == subject) {
            model.addAttribute("displace", "teach");
            model.addAttribute("subjectMsg", "查无此课程");
            return "admin";
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("subjectid", subid);
            map.put("adminid", aid);
            List<Teach> teaches = teachMapper.selectByMap(map);
            if (!teaches.isEmpty()) {
                ArrayList<Integer> list = new ArrayList<>();
                for (Teach teach : teaches) {
                    list.add(teach.getTeacherid());
                }
                List<Teacher> teachers = tm.selectBatchIds(list);
                model.addAttribute("teachers", teachers);
                model.addAttribute("subject", subject);
                model.addAttribute("displace", "teach");
                model.addAttribute("displaceteach", "teacher");
                return "admin";
            } else {
                model.addAttribute("displace", "teach");
                model.addAttribute("displaceteach", "teacher");
                model.addAttribute("subject", subject);
                model.addAttribute("subjectMsg", "无此课程任课教师相关信息");
                return "admin";
            }


        }

    }

    @RequestMapping("/selectSubjectByTeacherId")
    public String selectSubjectByTeacherId(@Param("tid") int tid,
                                           @Param("aid") int aid,
                                           Model model){
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();

        wrapper
                .eq("teacherid",tid);
        Teacher teacher = tm.selectOne(wrapper);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", aid);
        map.put("teacherid",tid);

        List<Teach> teaches = teachMapper.selectByMap(map);
        if (!teaches.isEmpty()){
            ArrayList<Integer> list = new ArrayList<>();
            for (Teach teach : teaches) {
                list.add(teach.getSubjectid());
            }
            List<Subject> subjects = subjectMapper.selectBatchIds(list);
            model.addAttribute("subjects",subjects);
            model.addAttribute("displace", "teach");
            model.addAttribute("teacher",teacher);
            model.addAttribute("displaceteach", "subject1");
            return "admin";
        }else {
            model.addAttribute("displace", "teach");
            model.addAttribute("displaceteach", "subject1");
            model.addAttribute("teacher",teacher);
            model.addAttribute("subjectMsg", "无此教师教授课程相关信息");
            return "admin";
        }

    }

    @RequestMapping("/toAddSubjectTeacherPages")
    public String toAddSubjectTeacherPage(@Param("aid") String aid,
                                          @Param("subid") int subid,
                                          Model model) {
        int adid = Integer.parseInt(aid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", adid);

        List<Teacher> ts = tm.selectByMap(map);
        model.addAttribute("ts", ts);
        model.addAttribute("aid", aid);
        model.addAttribute("subid", subid);

        return "addSubjectTeacherPage";
    }

    @RequestMapping("/addSubjectTeacher")
    public String addSubjectTeacher(@Param("aid") String aid,
                                    @Param("subid") int subid,
                                    @Param("tid") int tid,
                                    Model model) {
        int adid = Integer.parseInt(aid);

        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid",adid);
        map.put("subjectid",subid);
        map.put("teacherid",tid);
        List<Teach> teaches = teachMapper.selectByMap(map);
        if(!teaches.isEmpty()){
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("adminid",adid);
            map1.put("subjectid",subid);
            List<Teach> teaches1 = teachMapper.selectByMap(map1);
            ArrayList<Integer> list = new ArrayList<>();
            for (Teach teach : teaches1) {
                list.add(teach.getTeacherid());
            }
            List<Teacher> teachers = tm.selectBatchIds(list);
            QueryWrapper<Subject> wrapper = new QueryWrapper<>();
            wrapper
                    .eq("subjectid", subid)
                    .eq("adminid", adid);
            Subject subject = subjectMapper.selectOne(wrapper);
            model.addAttribute("teachers", teachers);
            model.addAttribute("subject", subject);
            model.addAttribute("displace", "teach");
            model.addAttribute("displaceteach", "teacher");
            return "admin";
        }

        Teach teach1 = new Teach();
        teach1.setAdminid(adid);
        teach1.setSubjectid(subid);
        teach1.setTeacherid(tid);

        teachMapper.insert(teach1);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("adminid",adid);
        map1.put("subjectid",subid);
        List<Teach> teaches1 = teachMapper.selectByMap(map1);
        ArrayList<Integer> list = new ArrayList<>();
        for (Teach teach : teaches1) {
            list.add(teach.getTeacherid());
        }
        List<Teacher> teachers = tm.selectBatchIds(list);
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper
                .eq("subjectid", subid)
                .eq("adminid", adid);
        Subject subject = subjectMapper.selectOne(wrapper);
        model.addAttribute("teachers", teachers);
        model.addAttribute("subject", subject);
        model.addAttribute("displace", "teach");
        model.addAttribute("displaceteach", "teacher");
        return "admin";
    }

    @RequestMapping("/addTeacherSubject")
    public String addTeacherSubject(@Param("aid") String aid,
                                    @Param("subid") int subid,
                                    @Param("tid") int tid,
                                    Model model){
        int adid = Integer.parseInt(aid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid",adid);
        map.put("subjectid",subid);
        map.put("teacherid",tid);
        List<Teach> teaches = teachMapper.selectByMap(map);
        if(!teaches.isEmpty()){
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("adminid",adid);
            map1.put("teacherid",tid);
            List<Teach> teaches1 = teachMapper.selectByMap(map1);
            ArrayList<Integer> list = new ArrayList<>();
            for (Teach teach : teaches1) {
                list.add(teach.getSubjectid());
            }
            List<Subject> subjects = subjectMapper.selectBatchIds(list);
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            wrapper
                    .eq("teacherid",tid);
            Teacher teacher = tm.selectOne(wrapper);
            model.addAttribute("teacher", teacher);
            model.addAttribute("subjects", subjects);
            model.addAttribute("displace", "teach");
            model.addAttribute("displaceteach", "subject1");
            return "admin";
        }
        Teach teach1 = new Teach();
        teach1.setAdminid(adid);
        teach1.setSubjectid(subid);
        teach1.setTeacherid(tid);
        teachMapper.insert(teach1);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("adminid",adid);
        map1.put("teacherid",tid);
        List<Teach> teaches1 = teachMapper.selectByMap(map1);
        ArrayList<Integer> list = new ArrayList<>();
        for (Teach teach : teaches1) {
            list.add(teach.getSubjectid());
        }
        List<Subject> subjects = subjectMapper.selectBatchIds(list);
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper
                .eq("teacherid",tid);
        Teacher teacher = tm.selectOne(wrapper);
        model.addAttribute("teacher", teacher);
        model.addAttribute("subjects", subjects);
        model.addAttribute("displace", "teach");
        model.addAttribute("displaceteach", "subject1");

        return "admin";
    }

    @RequestMapping("/deleteSubjectTeacher")
    public String deleteSubjectTeacher(@Param("tid") int tid,
                                       @Param("subid") int subid,
                                       @Param("aid") String aid,
                                       Model model){

        int adid = Integer.parseInt(aid);

        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherid",tid);
        map.put("subjectid",subid);
        map.put("adminid",adid);
        teachMapper.deleteByMap(map);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("adminid",adid);
        map1.put("subjectid",subid);
        List<Teach> teaches1 = teachMapper.selectByMap(map1);
        if(!teaches1.isEmpty()){
            ArrayList<Integer> list = new ArrayList<>();
            for (Teach teach : teaches1) {
                list.add(teach.getTeacherid());
            }
            List<Teacher> teachers = tm.selectBatchIds(list);
            model.addAttribute("teachers", teachers);
        }else {
            model.addAttribute("subjectMsg", "无此课程任课教师相关信息");
        }

        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper
                .eq("subjectid", subid)
                .eq("adminid", adid);
        Subject subject = subjectMapper.selectOne(wrapper);

        model.addAttribute("subject", subject);
        model.addAttribute("displace", "teach");
        model.addAttribute("displaceteach", "teacher");
        return "admin";
    }

    @RequestMapping("/delectTeacherSubject")
    public String delectTeacherSubject(@Param("tid") int tid,
                                       @Param("subid") int subid,
                                       @Param("aid") String aid,
                                       Model model){

        int adid = Integer.parseInt(aid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherid",tid);
        map.put("subjectid",subid);
        map.put("adminid",adid);
        teachMapper.deleteByMap(map);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("adminid",adid);
        map1.put("teacherid",tid);
        List<Teach> teaches1 = teachMapper.selectByMap(map1);

        if (!teaches1.isEmpty()){
            ArrayList<Integer> list = new ArrayList<>();
            for (Teach teach : teaches1) {
                list.add(teach.getSubjectid());
            }
            List<Subject> subjects = subjectMapper.selectBatchIds(list);
            model.addAttribute("subjects", subjects);
        }else {
            model.addAttribute("subjectMsg","无此教师教授课程相关信息");
        }
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper
                .eq("teacherid",tid);
        Teacher teacher = tm.selectOne(wrapper);
        model.addAttribute("teacher", teacher);
        model.addAttribute("displace", "teach");
        model.addAttribute("displaceteach", "subject1");
        return "admin";
    }

    @RequestMapping("/toAddTeacherSubjectPages")
    public String toAddTeacherSubjectPages(@Param("tid") int tid,
                                           @Param("aid") String aid,
                                           Model model){
        int adid = Integer.parseInt(aid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("adminid", adid);

        List<Subject> subjects = subjectMapper.selectByMap(map);
        model.addAttribute("subjects",subjects);
        model.addAttribute("aid",aid);
        model.addAttribute("tid",tid);
        return "addTeacherSubjectPage";
    }
    @Autowired
    private AdminMapper adminMapper;

    @RequestMapping("/updateAdinfo")
    public String updateAdinfo(@Param("id") String id,
                               Model model){
        Administrator administrator = adminMapper.selectById(Integer.parseInt(id));
        model.addAttribute("adinfo",administrator);
        model.addAttribute("displace","updateInfo");
        return "admin";
    }

    @RequestMapping("/updateAd")
    public String updateAd(@Param("id") int id,
                           @Param("pwd") String pwd,
                           @Param("phone") String phone,
                           Model model){
        Administrator administrator = adminMapper.selectById(id);
        administrator.setAdminpassword(pwd);
        administrator.setAdminphone(phone);
        adminMapper.updateById(administrator);
        Administrator administrator1 = adminMapper.selectById(id);
        model.addAttribute("adinfo",administrator1);
        model.addAttribute("displace","updateInfo");
        return "admin";
    }




}




















