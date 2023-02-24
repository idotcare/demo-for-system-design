package com.zgw.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zgw.mapper.*;
import com.zgw.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: zgw
 * @Date: 2021/4/17 - 04 - 17 - 9:27
 * @Description: com.zgw.controller
 * @version: 1.0
 */

@Controller
public class TeacherController {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private ChoiceMapper choiceMapper;

    @Autowired
    private JudgeMapper judgeMapper;

    @Autowired
    private CompletionMapper completionMapper;

    @Autowired
    private ShortanswerquestionMapper saqMapper;

    @Autowired
    private TeachMapper teachMapper;

    @Autowired
    private StudyMapper studyMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private TestpaperMapper testpaperMapper;

    @Autowired
    private Formation1Mapper formation1Mapper;

    @Autowired
    private Formation2Mapper formation2Mapper;

    @Autowired
    private Formation3Mapper formation3Mapper;

    @Autowired
    private Formation4Mapper formation4Mapper;

    @Autowired
    private Answer3Mapper answer3Mapper;

    @Autowired
    private Answer4Mapper answer4Mapper;

    //==============试题管理=================================================================

    @RequestMapping("/addQuestions")
    public String selectSubject(@Param("tid") String tid,
                                Model model) {

        int ttid = Integer.parseInt(tid);

        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherid", ttid);
        List<Teach> teaches = teachMapper.selectByMap(map);

        if (!teaches.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Teach teach : teaches) {
                list.add(teach.getSubjectid());
            }

            List<Subject> subjects = subjectMapper.selectBatchIds(list);
            model.addAttribute("subjects", subjects);
            model.addAttribute("displace", "subject");
            return "teacher";
        } else {
            model.addAttribute("submsg", "暂时没有课程可以进行试题管理");
            return "teacher";
        }

    }

    //=================选择题=====================================
    @RequestMapping("/selectSubjectChoicePages")
    public String selectSubjectChoicePage(@Param("subid") int subid,
                                          Model model) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Choice> choices = choiceMapper.selectByMap(map);

        model.addAttribute("subid", subid);
        model.addAttribute("choices", choices);

        return "allSubjectChoice";
    }

    @RequestMapping("/toaddChoicePage")
    public String toaddChoicePage(@Param("subid") int subid,
                                  Model model) {

        model.addAttribute("subid", subid);
        return "addChoice";
    }

    @RequestMapping("/addChoice")
    public String addChoice(@Param("question") String question,
                            @Param("optiona") String optiona,
                            @Param("optionb") String optionb,
                            @Param("optionc") String optionc,
                            @Param("optiond") String optiond,
                            @Param("answer") String answer,
                            @Param("analysis") String analysis,
                            @Param("subid") int subid,
                            Model model) {

        Choice choice = new Choice();
        choice.setChoicequestion(question);
        choice.setChoiceoptiona(optiona);
        choice.setChoiceoptionb(optionb);
        choice.setChoiceoptionc(optionc);
        choice.setChoiceoptiond(optiond);
        choice.setChoiceanswer(answer);
        choice.setChoiceanalysis(analysis);
        choice.setSubjectid(subid);

        choiceMapper.insert(choice);

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Choice> choices = choiceMapper.selectByMap(map);

        model.addAttribute("choices", choices);

        return "allSubjectChoice";
    }

    @RequestMapping("/toUpdateChoicePage")
    public String toUpdateChoicePage(@Param("cid") int cid,
                                     @Param("subid") int subid,
                                     Model model) {
        QueryWrapper<Choice> wrapper = new QueryWrapper<>();
        wrapper
                .eq("choiceid", cid)
                .eq("subjectid", subid);
        Choice choice = choiceMapper.selectOne(wrapper);
        model.addAttribute("subid", subid);
        model.addAttribute("choice", choice);
        return "updateChoice";
    }

    @RequestMapping("/updateChoice")
    public String updateChoice(@Param("question") String question,
                               @Param("optiona") String optiona,
                               @Param("optionb") String optionb,
                               @Param("optionc") String optionc,
                               @Param("optiond") String optiond,
                               @Param("answer") String answer,
                               @Param("analysis") String analysis,
                               @Param("subid") int subid,
                               @Param("choiceid") int choiceid,
                               Model model) {

        Choice choice = new Choice(choiceid, question, optiona, optionb, optionc, optiond, answer, analysis, subid);
        choiceMapper.updateById(choice);

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Choice> choices = choiceMapper.selectByMap(map);

        model.addAttribute("choices", choices);

        return "allSubjectChoice";
    }


    @RequestMapping("/deleteChoice")
    public String deleteChoice(@Param("cid") int cid,
                               @Param("subid") int subid,
                               Model model) {
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("choiceid", cid);
        choiceMapper.deleteByMap(map1);

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Choice> choices = choiceMapper.selectByMap(map);

        model.addAttribute("choices", choices);

        return "allSubjectChoice";

    }


    //=========================判断题============================

    @RequestMapping("/selectSubjectJudgePages")
    public String selectSubjectJudgePages(@Param("subid") int subid,
                                          Model model) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Judge> judges = judgeMapper.selectByMap(map);

        model.addAttribute("judges", judges);
        model.addAttribute("subid", subid);

        return "allSubjectJudge";
    }

    @RequestMapping("/toaddJudgePage")
    public String toaddJudgePage(@Param("subid") int subid,
                                 Model model) {
        model.addAttribute("subid", subid);
        return "addJudge";
    }

    @RequestMapping("/addJudge")
    public String addJudge(@Param("question") String question,
                           @Param("optiona") String optiona,
                           @Param("optionb") String optionb,
                           @Param("answer") String answer,
                           @Param("analysis") String analysis,
                           @Param("subid") int subid,
                           Model model) {

        Judge judge = new Judge();
        judge.setJudgequestion(question);
        judge.setJudgeoptiona(optiona);
        judge.setJudegoptionb(optionb);
        judge.setJudgeanswer(answer);
        judge.setJudgeanalysis(analysis);
        judge.setSubjectid(subid);

        judgeMapper.insert(judge);

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Judge> judges = judgeMapper.selectByMap(map);

        model.addAttribute("judges", judges);
        model.addAttribute("subid", subid);

        return "allSubjectJudge";
    }

    @RequestMapping("/toUpdateJudgePage")
    public String toUpdateJudgePage(@Param("jid") int jid,
                                    @Param("subid") int subid,
                                    Model model) {

        QueryWrapper<Judge> wrapper = new QueryWrapper<>();

        wrapper
                .eq("judgeid", jid)
                .eq("subjectid", subid);
        Judge judge = judgeMapper.selectOne(wrapper);
        model.addAttribute("subid", subid);
        model.addAttribute("judge", judge);
        return "updateJudge";
    }

    @RequestMapping("/updateJudge")
    public String updateIudge(@Param("question") String question,
                              @Param("optiona") String optiona,
                              @Param("optionb") String optionb,
                              @Param("answer") String answer,
                              @Param("analysis") String analysis,
                              @Param("subid") int subid,
                              @Param("judgeid") int judgeid,
                              Model model) {

        Judge judge = new Judge(judgeid, question, optiona, optionb, answer, analysis, subid);
        judgeMapper.updateById(judge);

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Judge> judges = judgeMapper.selectByMap(map);

        model.addAttribute("judges", judges);
        model.addAttribute("subid", subid);

        return "allSubjectJudge";
    }

    @RequestMapping("/deleteJudge")
    public String deleteJudge(int jid,
                              int subid,
                              Model model) {
        judgeMapper.deleteById(jid);

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Judge> judges = judgeMapper.selectByMap(map);

        model.addAttribute("judges", judges);
        model.addAttribute("subid", subid);

        return "allSubjectJudge";
    }


    //=========================填空题============================

    @RequestMapping("/selectSubjectCompletionPages")
    public String selectSubjectCompletionPages(@Param("subid") int subid,
                                               Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Completion> completions = completionMapper.selectByMap(map);

        model.addAttribute("completions", completions);
        model.addAttribute("subid", subid);

        return "allSubjectCompletion";
    }

    @RequestMapping("/toaddCompletionPage")
    public String toaddCompletionPage(@Param("subid") int subid,
                                      Model model) {

        model.addAttribute("subid", subid);
        return "addCompletion";
    }

    @RequestMapping("/addCompletion")
    public String addCompletion(@Param("question") String question,
                                @Param("answer") String answer,
                                @Param("analysis") String analysis,
                                @Param("subid") int subid,
                                Model model) {

        Completion completion = new Completion();
        completion.setCompletquestion(question);
        completion.setCompletanswer(answer);
        completion.setCompletanalysis(analysis);
        completion.setSubjectid(subid);

        completionMapper.insert(completion);

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Completion> completions = completionMapper.selectByMap(map);

        model.addAttribute("completions", completions);
        model.addAttribute("subid", subid);

        return "allSubjectCompletion";
    }

    @RequestMapping("/toUpdateCompletionPage")
    public String toUpdateCompletionPage(@Param("coid") int coid,
                                         @Param("subid") int subid,
                                         Model model) {
        QueryWrapper<Completion> wrapper = new QueryWrapper<>();

        wrapper
                .eq("completid", coid)
                .eq("subjectid", subid);
        Completion completion = completionMapper.selectOne(wrapper);

        model.addAttribute("completion", completion);
        model.addAttribute("subid", subid);

        return "updateCompletion";
    }

    @RequestMapping("/updateCompletion")
    public String updateCompletion(@Param("question") String question,
                                   @Param("answer") String answer,
                                   @Param("analysis") String analysis,
                                   @Param("subid") int subid,
                                   @Param("coid") int coid,
                                   Model model) {
        Completion completion = new Completion(coid, question, answer, analysis, subid);
        completionMapper.updateById(completion);

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Completion> completions = completionMapper.selectByMap(map);

        model.addAttribute("completions", completions);
        model.addAttribute("subid", subid);

        return "allSubjectCompletion";
    }

    @RequestMapping("/deleteCompletion")
    public String deleteCompletion(int coid,
                                   int subid,
                                   Model model) {

        completionMapper.deleteById(coid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Completion> completions = completionMapper.selectByMap(map);

        model.addAttribute("completions", completions);
        model.addAttribute("subid", subid);

        return "allSubjectCompletion";

    }


    //=========================简答题============================

    @RequestMapping("/selectSubjectShortAnswerQuestionPages")
    public String selectSubjectShortAnswerQuestionPages(@Param("subid") int subid,
                                                        Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Shortanswerquestion> shortanswerquestions = saqMapper.selectByMap(map);

        model.addAttribute("shortanswerquestions", shortanswerquestions);
        model.addAttribute("subid", subid);

        return "allSubjectShortAnswerQuestion";
    }

    @RequestMapping("/toaddSAQPage")
    public String toaddSAQPage(@Param("subid") int subid,
                               Model model) {
        model.addAttribute("subid", subid);
        return "addSAQ";
    }

    @RequestMapping("/addSAQ")
    public String addSAQ(@Param("question") String question,
                         @Param("answer") String answer,
                         @Param("analysis") String analysis,
                         @Param("subid") int subid,
                         Model model) {
        Shortanswerquestion saq = new Shortanswerquestion();
        saq.setSaquestion(question);
        saq.setSaqanswer(answer);
        saq.setSaqanalysis(analysis);
        saq.setSubjectid(subid);

        saqMapper.insert(saq);
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Shortanswerquestion> shortanswerquestions = saqMapper.selectByMap(map);

        model.addAttribute("shortanswerquestions", shortanswerquestions);
        model.addAttribute("subid", subid);

        return "allSubjectShortAnswerQuestion";
    }

    @RequestMapping("/toUpdateSAQPage")
    public String toUpdateSAQPage(int saqid,
                                  int subid,
                                  Model model) {
        QueryWrapper<Shortanswerquestion> wrapper = new QueryWrapper<>();

        wrapper
                .eq("saqid", saqid)
                .eq("subjectid", subid);
        Shortanswerquestion saq = saqMapper.selectOne(wrapper);

        model.addAttribute("saq", saq);
        model.addAttribute("subid", subid);

        return "updateSAQ";
    }

    @RequestMapping("/updateSAQ")
    public String updateSAQ(@Param("question") String question,
                            @Param("answer") String answer,
                            @Param("analysis") String analysis,
                            @Param("subid") int subid,
                            @Param("saqid") int saqid,
                            Model model) {
        Shortanswerquestion saq = new Shortanswerquestion(saqid, question, answer, analysis, subid);
        saqMapper.updateById(saq);
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Shortanswerquestion> shortanswerquestions = saqMapper.selectByMap(map);

        model.addAttribute("shortanswerquestions", shortanswerquestions);
        model.addAttribute("subid", subid);

        return "allSubjectShortAnswerQuestion";
    }

    @RequestMapping("/deleteSAQ")
    public String deleteSAQ(@Param("saqid") int saqid,
                            @Param("subid") int subid,
                            Model model) {

        saqMapper.deleteById(saqid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Shortanswerquestion> shortanswerquestions = saqMapper.selectByMap(map);

        model.addAttribute("shortanswerquestions", shortanswerquestions);
        model.addAttribute("subid", subid);

        return "allSubjectShortAnswerQuestion";
    }


    //==============试卷=========================================
    @RequestMapping("/addPaper")
    public String selectSubject1(@Param("tid") String tid,
                                 Model model) {

        int ttid = Integer.parseInt(tid);

        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherid", ttid);
        List<Teach> teaches = teachMapper.selectByMap(map);

        if (!teaches.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Teach teach : teaches) {
                list.add(teach.getSubjectid());
            }

            List<Subject> subjects = subjectMapper.selectBatchIds(list);
            model.addAttribute("subjects", subjects);
            model.addAttribute("displace", "subject1");
            return "teacher";
        } else {
            model.addAttribute("submsg", "暂时没有课程可以进行试卷管理");
            return "teacher";
        }

    }

    @RequestMapping("/selectSubjectPaper")
    public String selectSubjectPaper(@Param("subid") int subid,
                                     @Param("tid") String tid,
                                     Model model) {
        int ttid = Integer.parseInt(tid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherid", ttid);
        map.put("subjectid", subid);

        List<Testpaper> testpapers = testpaperMapper.selectByMap(map);

        model.addAttribute("testpapers", testpapers);
        model.addAttribute("subid", subid);
        model.addAttribute("tid", ttid);

        return "allPaper";
    }

    @RequestMapping("/toaddPaperPage")
    public String toaddPaperPage(@Param("subid") int subid,
                                 @Param("tid") int tid,
                                 Model model) {
        model.addAttribute("subid", subid);
        model.addAttribute("tid", tid);
        return "addPaper";
    }

    @RequestMapping("/addNewPaper")
    public String addNewPaper(@Param("tpapername") String tpapername,
                              @Param("totalscore") String totalscore,
                              @Param("totalminutes") String totalminutes,
                              @Param("starttime") String starttime,
                              @Param("endtime") String endtime,
                              @Param("subid") int subid,
                              @Param("tid") int tid,
                              @Param("students") String students,
                              Model model) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        try {
            start = simpleDateFormat.parse(starttime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date end = null;
        try {
            end = simpleDateFormat.parse(endtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Testpaper testpaper = new Testpaper();
        testpaper.setTpapername(tpapername);
        testpaper.setTotalscore(Integer.parseInt(totalscore));
        testpaper.setTotalminutes(Integer.parseInt(totalminutes));
        testpaper.setStarttime(start);
        testpaper.setEndtime(end);
        testpaper.setSubjectid(subid);
        testpaper.setTeacherid(tid);

        testpaperMapper.insert(testpaper);

        QueryWrapper<Testpaper> wrapper = new QueryWrapper<>();

        wrapper.eq("tpapername", tpapername)
                .eq("totalscore", Integer.parseInt(totalscore))
                .eq("totalminutes", totalminutes)
                .eq("starttime", start)
                .eq("endtime", end)
                .eq("subjectid", subid)
                .eq("teacherid", tid);

        Testpaper testpaper1 = testpaperMapper.selectOne(wrapper);

        Test test = new Test();
        test.setIstest(false);
        test.setSubjectid(subid);
        test.setTpaperid(testpaper1.getTpaperid());

        if (students.equals("class")) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("subjectid", subid);
            map.put("teacherid", tid);

            List<Study> studies = studyMapper.selectByMap(map);

            for (Study study : studies) {
                test.setStudentid(study.getStudentid());
                testMapper.insert(test);
            }


        } else if (students.equals("subject")) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("subjectid", subid);

            List<Study> studies = studyMapper.selectByMap(map);

            for (Study study : studies) {
                test.setStudentid(study.getStudentid());
                testMapper.insert(test);
            }
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherid", tid);
        map.put("subjectid", subid);

        List<Testpaper> testpapers = testpaperMapper.selectByMap(map);

        model.addAttribute("testpapers", testpapers);
        model.addAttribute("subid", subid);
        model.addAttribute("tid", tid);

        return "allPaper";
    }

    @RequestMapping("/toformationPapge")//查看试卷选择题
    public String selectPaperChoice(@Param("pid") int pid,
                                    @Param("subid") int subid,
                                    Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", pid);
        List<Formation1> choices = formation1Mapper.selectByMap(map);

        if (!choices.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation1 choice : choices) {
                list.add(choice.getQuestionid());
            }
            List<Choice> choices1 = choiceMapper.selectBatchIds(list);
            model.addAttribute("choices", choices1);
        }

        model.addAttribute("pid", pid);
        model.addAttribute("subid", subid);
        model.addAttribute("displaceQuestion", "choice");
        return "PaperQuestions";
    }

    @RequestMapping("/toformationJudge")//查看试卷判断题
    public String selectPaperJudge(@Param("pid") int pid,
                                   @Param("subid") int subid,
                                   Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", pid);
        List<Formation2> judges = formation2Mapper.selectByMap(map);

        if (!judges.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation2 judge : judges) {
                list.add(judge.getQuestionid());
            }
            List<Judge> judges1 = judgeMapper.selectBatchIds(list);
            model.addAttribute("judges", judges1);
        }

        model.addAttribute("pid", pid);
        model.addAttribute("subid", subid);
        model.addAttribute("displaceQuestion", "judge");
        return "PaperQuestions";
    }

    @RequestMapping("/toformationCompletion")//查看试卷填空题
    public String selectPaperCompletion(@Param("pid") int pid,
                                        @Param("subid") int subid,
                                        Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", pid);
        List<Formation3> completions = formation3Mapper.selectByMap(map);

        if (!completions.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation3 completion : completions) {
                list.add(completion.getQuestionid());
            }
            List<Completion> completions1 = completionMapper.selectBatchIds(list);
            model.addAttribute("completions", completions1);
        }
        model.addAttribute("pid", pid);
        model.addAttribute("subid", subid);
        model.addAttribute("displaceQuestion", "completion");
        return "PaperQuestions";
    }

    @RequestMapping("/toformationSQA")//查看试卷简答题
    public String selectPaperSAQ(@Param("pid") int pid,
                                 @Param("subid") int subid,
                                 Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", pid);
        List<Formation4> saqs = formation4Mapper.selectByMap(map);

        if (!saqs.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation4 saq : saqs) {
                list.add(saq.getQuestionid());
            }
            List<Shortanswerquestion> shortanswerquestions = saqMapper.selectBatchIds(list);
            model.addAttribute("shortanswerquestions", shortanswerquestions);
        }
        model.addAttribute("pid", pid);
        model.addAttribute("subid", subid);
        model.addAttribute("displaceQuestion", "saq");
        return "PaperQuestions";
    }


    @RequestMapping("/selectChoiceToPaper")
    public String addChoiceToPaper(@Param("pid") int pid,
                                   @Param("subid") int subid,
                                   Model model) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Choice> choices = choiceMapper.selectByMap(map);
        model.addAttribute("choices", choices);
        model.addAttribute("subid", subid);
        model.addAttribute("pid", pid);
        return "addChoiceToPaper";
    }


    @RequestMapping("/selectJudgeToPaper")
    public String addJudgeToPaper(@Param("pid") int pid,
                                  @Param("subid") int subid,
                                  Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Judge> judges = judgeMapper.selectByMap(map);
        model.addAttribute("judges", judges);
        model.addAttribute("subid", subid);
        model.addAttribute("pid", pid);
        return "addJudgeToPaper";
    }

    @RequestMapping("/selectCompletionToPaper")
    public String addCompletionToPaper(@Param("pid") int pid,
                                       @Param("subid") int subid,
                                       Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Completion> completions = completionMapper.selectByMap(map);
        model.addAttribute("completions", completions);
        model.addAttribute("subid", subid);
        model.addAttribute("pid", pid);
        return "addCompletionToPaper";
    }

    @RequestMapping("/selectSAQToPaper")
    public String addSAQToPaper(@Param("pid") int pid,
                                @Param("subid") int subid,
                                Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Shortanswerquestion> shortanswerquestions = saqMapper.selectByMap(map);
        model.addAttribute("shortanswerquestions", shortanswerquestions);
        model.addAttribute("subid", subid);
        model.addAttribute("pid", pid);
        return "addSAQToPaper";
    }


    @RequestMapping("/addChoiceToPaper")
    public String addChoiceToPaper1(@Param("weight") String weight,
                                    @Param("questionid") int questionid,
                                    @Param("tpaperid") int tpaperid,
                                    @Param("subid") int subid,
                                    Model model) {
        /*QueryWrapper<Testpaper> wrapper = new QueryWrapper<>();
        wrapper.eq("tpaperid", tpaperid);

        Testpaper testpaper = testpaperMapper.selectOne(wrapper);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("tpaperid", tpaperid);
        List<Formation1> formation1s1 = formation1Mapper.selectByMap(map1);
        List<Formation2> formation2s = formation2Mapper.selectByMap(map1);
        List<Formation3> formation3s = formation3Mapper.selectByMap(map1);
        List<Formation4> formation4s = formation4Mapper.selectByMap(map1);
        int tweight = Integer.parseInt(weight);
        if (!formation1s1.isEmpty()) {
            int total = 0;
            for (Formation1 formation1 : formation1s1) {
                total = total + formation1.getWeight();
            }
            for (Formation2 formation2 : formation2s) {
                total = total + formation2.getWeight();
            }
            for (Formation3 formation3 : formation3s) {
                total = total + formation3.getWeight();
            }
            for (Formation4 formation4 : formation4s) {
                total = total + formation4.getWeight();
            }

            if ((total + tweight) <= testpaper.getTotalscore()) {

                HashMap<String, Object> map = new HashMap<>();
                //map.put("weight",tweight);
                map.put("questionid", questionid);
                map.put("tpaperid", tpaperid);

                List<Formation1> formation1s = formation1Mapper.selectByMap(map);

                if (formation1s.isEmpty()) {

                    Formation1 formation1 = new Formation1();
                    formation1.setQuestionid(questionid);
                    formation1.setTpaperid(tpaperid);
                    formation1.setWeight(tweight);
                    formation1Mapper.insert(formation1);
                }

            }

        } else {
            Formation1 formation1 = new Formation1();
            formation1.setQuestionid(questionid);
            formation1.setTpaperid(tpaperid);
            formation1.setWeight(tweight);
            formation1Mapper.insert(formation1);
        }

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("subjectid", subid);
        List<Choice> choices = choiceMapper.selectByMap(map2);
        model.addAttribute("choices", choices);
        model.addAttribute("subid", subid);
        model.addAttribute("pid", tpaperid);*/

        QueryWrapper<Formation1> wrapper = new QueryWrapper<>();
        wrapper.eq("tpaperid", tpaperid)
                .eq("questionid", questionid);

        Formation1 paperChoice = formation1Mapper.selectOne(wrapper);

        if (null == paperChoice) {
            QueryWrapper<Testpaper> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("tpaperid", tpaperid);
            Testpaper testpaper = testpaperMapper.selectOne(wrapper1);

            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("tpaperid", tpaperid);
            List<Formation1> formation1s = formation1Mapper.selectByMap(map1);
            List<Formation2> formation2s = formation2Mapper.selectByMap(map1);
            List<Formation3> formation3s = formation3Mapper.selectByMap(map1);
            List<Formation4> formation4s = formation4Mapper.selectByMap(map1);

            int tweight = Integer.parseInt(weight);

            int total = 0;
            for (Formation1 formation1 : formation1s) {
                total = total + formation1.getWeight();
            }
            for (Formation2 formation2 : formation2s) {
                total = total + formation2.getWeight();
            }
            for (Formation3 formation3 : formation3s) {
                total = total + formation3.getWeight();
            }
            for (Formation4 formation4 : formation4s) {
                total = total + formation4.getWeight();
            }

            if ((total + tweight) <= testpaper.getTotalscore()) {
                Formation1 formation1 = new Formation1();
                formation1.setQuestionid(questionid);
                formation1.setTpaperid(tpaperid);
                formation1.setWeight(tweight);
                formation1Mapper.insert(formation1);
            }
        }

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("subjectid", subid);
        List<Choice> choices = choiceMapper.selectByMap(map2);

        model.addAttribute("choices", choices);
        model.addAttribute("subid", subid);
        model.addAttribute("pid", tpaperid);

        return "addChoiceToPaper";
    }

    @RequestMapping("/addJudgeToPaper")
    public String addJudgeToPaper1(@Param("weight") String weight,
                                   @Param("questionid") int questionid,
                                   @Param("tpaperid") int tpaperid,
                                   @Param("subid") int subid,
                                   Model model) {

        QueryWrapper<Formation2> wrapper = new QueryWrapper<>();
        wrapper.eq("tpaperid", tpaperid)
                .eq("questionid", questionid);

        Formation2 paperJudge = formation2Mapper.selectOne(wrapper);

        if (null == paperJudge) {
            QueryWrapper<Testpaper> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("tpaperid", tpaperid);
            Testpaper testpaper = testpaperMapper.selectOne(wrapper1);

            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("tpaperid", tpaperid);
            List<Formation1> formation1s = formation1Mapper.selectByMap(map1);
            List<Formation2> formation2s = formation2Mapper.selectByMap(map1);
            List<Formation3> formation3s = formation3Mapper.selectByMap(map1);
            List<Formation4> formation4s = formation4Mapper.selectByMap(map1);

            int tweight = Integer.parseInt(weight);

            int total = 0;
            for (Formation1 formation1 : formation1s) {
                total = total + formation1.getWeight();
            }
            for (Formation2 formation2 : formation2s) {
                total = total + formation2.getWeight();
            }
            for (Formation3 formation3 : formation3s) {
                total = total + formation3.getWeight();
            }
            for (Formation4 formation4 : formation4s) {
                total = total + formation4.getWeight();
            }

            if ((total + tweight) <= testpaper.getTotalscore()) {
                Formation2 formation2 = new Formation2();
                formation2.setQuestionid(questionid);
                formation2.setTpaperid(tpaperid);
                formation2.setWeight(tweight);
                formation2Mapper.insert(formation2);
            }
        }

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("subjectid", subid);
        List<Judge> judges = judgeMapper.selectByMap(map2);

        model.addAttribute("judges", judges);
        model.addAttribute("subid", subid);
        model.addAttribute("pid", tpaperid);

        return "addJudgeToPaper";
    }

    @RequestMapping("/addCompletionToPaper")
    public String addCompletionToPaper1(@Param("weight") String weight,
                                        @Param("questionid") int questionid,
                                        @Param("tpaperid") int tpaperid,
                                        @Param("subid") int subid,
                                        Model model) {
        QueryWrapper<Formation3> wrapper = new QueryWrapper<>();
        wrapper.eq("tpaperid", tpaperid)
                .eq("questionid", questionid);

        Formation3 paperCompletion = formation3Mapper.selectOne(wrapper);

        if (null == paperCompletion) {
            QueryWrapper<Testpaper> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("tpaperid", tpaperid);
            Testpaper testpaper = testpaperMapper.selectOne(wrapper1);

            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("tpaperid", tpaperid);
            List<Formation1> formation1s = formation1Mapper.selectByMap(map1);
            List<Formation2> formation2s = formation2Mapper.selectByMap(map1);
            List<Formation3> formation3s = formation3Mapper.selectByMap(map1);
            List<Formation4> formation4s = formation4Mapper.selectByMap(map1);

            int tweight = Integer.parseInt(weight);

            int total = 0;
            for (Formation1 formation1 : formation1s) {
                total = total + formation1.getWeight();
            }
            for (Formation2 formation2 : formation2s) {
                total = total + formation2.getWeight();
            }
            for (Formation3 formation3 : formation3s) {
                total = total + formation3.getWeight();
            }
            for (Formation4 formation4 : formation4s) {
                total = total + formation4.getWeight();
            }

            if ((total + tweight) <= testpaper.getTotalscore()) {
                Formation3 formation3 = new Formation3();
                formation3.setQuestionid(questionid);
                formation3.setTpaperid(tpaperid);
                formation3.setWeight(tweight);
                formation3Mapper.insert(formation3);
            }

        }

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("subjectid", subid);
        List<Completion> completions = completionMapper.selectByMap(map2);

        model.addAttribute("completions", completions);
        model.addAttribute("subid", subid);
        model.addAttribute("pid", tpaperid);

        return "addCompletionToPaper";
    }

    @RequestMapping("/addSAQToPaper")
    public String addSAQToPaper(@Param("weight") String weight,
                                @Param("questionid") int questionid,
                                @Param("tpaperid") int tpaperid,
                                @Param("subid") int subid,
                                Model model) {
        QueryWrapper<Formation4> wrapper = new QueryWrapper<>();
        wrapper.eq("tpaperid", tpaperid)
                .eq("questionid", questionid);

        Formation4 paperSAQ = formation4Mapper.selectOne(wrapper);

        if (null == paperSAQ) {
            QueryWrapper<Testpaper> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("tpaperid", tpaperid);
            Testpaper testpaper = testpaperMapper.selectOne(wrapper1);

            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("tpaperid", tpaperid);
            List<Formation1> formation1s = formation1Mapper.selectByMap(map1);
            List<Formation2> formation2s = formation2Mapper.selectByMap(map1);
            List<Formation3> formation3s = formation3Mapper.selectByMap(map1);
            List<Formation4> formation4s = formation4Mapper.selectByMap(map1);

            int tweight = Integer.parseInt(weight);

            int total = 0;
            for (Formation1 formation1 : formation1s) {
                total = total + formation1.getWeight();
            }
            for (Formation2 formation2 : formation2s) {
                total = total + formation2.getWeight();
            }
            for (Formation3 formation3 : formation3s) {
                total = total + formation3.getWeight();
            }
            for (Formation4 formation4 : formation4s) {
                total = total + formation4.getWeight();
            }

            if ((total + tweight) <= testpaper.getTotalscore()) {
                Formation4 formation4 = new Formation4();
                formation4.setQuestionid(questionid);
                formation4.setTpaperid(tpaperid);
                formation4.setWeight(tweight);
                formation4Mapper.insert(formation4);
            }

        }

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("subjectid", subid);
        List<Shortanswerquestion> shortanswerquestions = saqMapper.selectByMap(map2);

        model.addAttribute("shortanswerquestions", shortanswerquestions);
        model.addAttribute("subid", subid);
        model.addAttribute("pid", tpaperid);

        return "addSAQToPaper";
    }


    @RequestMapping("/removeChoicefromPaper")
    public String removeChoiceFromPaper(@Param("pid") int pid,
                                        @Param("cid") int cid,
                                        @Param("subid") int subid,
                                        Model model) {

        QueryWrapper<Formation1> wrapper = new QueryWrapper<>();
        wrapper
                .eq("tpaperid", pid)
                .eq("questionid", cid);

        Formation1 formation1 = formation1Mapper.selectOne(wrapper);
        formation1Mapper.deleteById(formation1.getFormationid());

        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", pid);
        List<Formation1> choices = formation1Mapper.selectByMap(map);

        if (!choices.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation1 choice : choices) {
                list.add(choice.getQuestionid());
            }
            List<Choice> choices1 = choiceMapper.selectBatchIds(list);
            model.addAttribute("choices", choices1);
        }

        model.addAttribute("pid", pid);
        model.addAttribute("subid", subid);
        model.addAttribute("displaceQuestion", "choice");
        return "PaperQuestions";
    }

    @RequestMapping("/removeJudgefromPaper")
    public String removeJudgeFromPaper(@Param("pid") int pid,
                                       @Param("jid") int jid,
                                       @Param("subid") int subid,
                                       Model model) {
        QueryWrapper<Formation2> wrapper = new QueryWrapper<>();
        wrapper
                .eq("tpaperid", pid)
                .eq("questionid", jid);

        Formation2 formation2 = formation2Mapper.selectOne(wrapper);
        formation2Mapper.deleteById(formation2.getFormationid());

        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", pid);
        List<Formation2> judges = formation2Mapper.selectByMap(map);

        if (!judges.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation2 judge : judges) {
                list.add(judge.getQuestionid());
            }
            List<Judge> judges1 = judgeMapper.selectBatchIds(list);
            model.addAttribute("judges", judges1);
        }

        model.addAttribute("pid", pid);
        model.addAttribute("subid", subid);
        model.addAttribute("displaceQuestion", "judge");
        return "PaperQuestions";
    }

    @RequestMapping("/removeCompletionfromPaper")
    public String removeCompletionFromPaper(@Param("pid") int pid,
                                            @Param("coid") int coid,
                                            @Param("subid") int subid,
                                            Model model) {

        QueryWrapper<Formation3> wrapper = new QueryWrapper<>();
        wrapper
                .eq("tpaperid", pid)
                .eq("questionid", coid);

        Formation3 formation3 = formation3Mapper.selectOne(wrapper);
        formation3Mapper.deleteById(formation3.getFormationid());

        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", pid);
        List<Formation3> completions = formation3Mapper.selectByMap(map);

        if (!completions.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation3 completion : completions) {
                list.add(completion.getQuestionid());
            }
            List<Completion> completions1 = completionMapper.selectBatchIds(list);
            model.addAttribute("completions", completions1);
        }

        model.addAttribute("pid", pid);
        model.addAttribute("subid", subid);
        model.addAttribute("displaceQuestion", "completion");
        return "PaperQuestions";
    }

    @RequestMapping("/removeSAQfromPaper")
    public String removeSAQFromPaper(@Param("pid") int pid,
                                     @Param("saqid") int saqid,
                                     @Param("subid") int subid,
                                     Model model) {

        QueryWrapper<Formation4> wrapper = new QueryWrapper<>();
        wrapper
                .eq("tpaperid", pid)
                .eq("questionid", saqid);

        Formation4 formation4 = formation4Mapper.selectOne(wrapper);
        formation4Mapper.deleteById(formation4.getFormationid());

        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", pid);
        List<Formation4> saqs = formation4Mapper.selectByMap(map);

        if (!saqs.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation4 saq : saqs) {
                list.add(saq.getQuestionid());
            }
            List<Shortanswerquestion> shortanswerquestions = saqMapper.selectBatchIds(list);
            model.addAttribute("shortanswerquestions", shortanswerquestions);
        }

        model.addAttribute("pid", pid);
        model.addAttribute("subid", subid);
        model.addAttribute("displaceQuestion", "saq");
        return "PaperQuestions";
    }

    @RequestMapping("/markingPapers")
    public String markingPapers(@Param("tid") String tid,
                                Model model) {
        int ttid = Integer.parseInt(tid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherid", ttid);
        List<Teach> teaches = teachMapper.selectByMap(map);

        if (!teaches.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Teach teach : teaches) {
                list.add(teach.getSubjectid());
            }

            List<Subject> subjects = subjectMapper.selectBatchIds(list);
            model.addAttribute("subjects", subjects);
            model.addAttribute("displace", "subject2");
            return "teacher";
        } else {
            model.addAttribute("submsg", "暂时没有课程可以进行试卷批阅");
            return "teacher";
        }
    }

    @RequestMapping("/selectSubjectPaperToMark")
    public String selectSubjectPaperToMark(@Param("subid") int subid,
                                           @Param("tid") String tid,
                                           Model model) {
        int ttid = Integer.parseInt(tid);
        HashMap<String, Object> map = new HashMap<>();
        //map.put("teacherid", ttid);
        map.put("subjectid", subid);
        List<Testpaper> testpapers = testpaperMapper.selectByMap(map);
        if (testpapers.isEmpty()) {
            model.addAttribute("paperMsg", "此课程下没有试卷");
        }
        model.addAttribute("testpapers", testpapers);
        model.addAttribute("subid", subid);
        model.addAttribute("tid", ttid);
        return "papersToBeReviewed";
    }

    /*
     * 此功能细节部分待优化
     * 空指针异常判断。。。
     * */
    @RequestMapping("/selectSubjectQuestionToMark")
    public String selectSubjectQuestionToMark(@Param("pid") int pid,
                                              @Param("subid") int subid,
                                              Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", pid);

        List<Formation3> completions = formation3Mapper.selectByMap(map);
        if (!completions.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation3 completion : completions) {
                list.add(completion.getQuestionid());
            }
            List<Completion> completions1 = completionMapper.selectBatchIds(list);
            model.addAttribute("completions", completions1);
            model.addAttribute("displacecompl", "yes");
        }


        List<Formation4> saqs = formation4Mapper.selectByMap(map);
        if (!saqs.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation4 saq : saqs) {
                list.add(saq.getQuestionid());
            }
            List<Shortanswerquestion> shortanswerquestions = saqMapper.selectBatchIds(list);
            model.addAttribute("shortanswerquestions", shortanswerquestions);
            model.addAttribute("displacesaq", "yes");
        }
        model.addAttribute("pid", pid);
        model.addAttribute("subid", subid);
        return "questionAndAnswerToBeMarked";
    }

    private List<Answer3> answer3List = null;
    private int answer3Index = 0;

    @RequestMapping("/oneCompletionAllAnsewrs")
    public String oneCompletionAllAnsewrs(@Param("tpaperid") int tpaperid,
                                          @Param("subjectid") int subjectid,
                                          @Param("questionid") int questionid,
                                          @Param("answer") String answer,
                                          @Param("analysis") String analysis,
                                          @Param("question") String question,
                                          Model model) {
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("tpaperid", tpaperid);
        map1.put("questionid", questionid);
        map1.put("isread", false);

        List<Answer3> answer3s = answer3Mapper.selectByMap(map1);

        if (!answer3s.isEmpty()) {
            answer3List = answer3s;
            model.addAttribute("sanswer", answer3List.get(answer3Index++));
            model.addAttribute("pid", tpaperid);
            model.addAttribute("subid", subjectid);
            model.addAttribute("answer", answer);
            model.addAttribute("analysis", analysis);
            model.addAttribute("question", question);
            return "comAnswer";
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", tpaperid);

        List<Formation3> completions = formation3Mapper.selectByMap(map);
        if (!completions.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation3 completion : completions) {
                list.add(completion.getQuestionid());
            }
            List<Completion> completions1 = completionMapper.selectBatchIds(list);
            model.addAttribute("completions", completions1);
            model.addAttribute("displacecompl", "yes");
        }

        List<Formation4> saqs = formation4Mapper.selectByMap(map);
        if (!saqs.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation4 saq : saqs) {
                list.add(saq.getQuestionid());
            }
            List<Shortanswerquestion> shortanswerquestions = saqMapper.selectBatchIds(list);
            model.addAttribute("shortanswerquestions", shortanswerquestions);
            model.addAttribute("displacesaq", "yes");
        }

        model.addAttribute("pid", tpaperid);
        model.addAttribute("subid", tpaperid);

        return "questionAndAnswerToBeMarked";
    }

    @Autowired
    private ScoreMapper scoreMapper;

    @RequestMapping("/insertWeightToScore")
    public String insertWeightToScore(@Param("question") String question,
                                      @Param("answer") String answer,
                                      @Param("analysis") String analysis,
                                      @Param("tpaperid") int tpaperid,
                                      @Param("subjectid") int subjectid,
                                      @Param("studentid") int studentid,
                                      @Param("answerid") int answerid,
                                      @Param("weight") int weight,
                                      Model model) {

        Answer3 answer3 = answer3Mapper.selectById(answerid);
        answer3.setIsread(true);
        answer3.setActualscore(weight);
        answer3Mapper.updateById(answer3);

        QueryWrapper<Score> wrapper = new QueryWrapper<>();
        wrapper
                .eq("tpaperid", tpaperid)
                .eq("studentid", studentid);

        Score score = scoreMapper.selectOne(wrapper);
        if (score != null) {
            score.setScore(score.getScore() + weight);
            scoreMapper.updateById(score);
        } else {
            score = new Score();
            score.setScore(weight);
            score.setTpaperid(tpaperid);
            score.setSubjectid(subjectid);
            score.setStudentid(studentid);
            scoreMapper.insert(score);
        }

        if (answer3Index < answer3List.size()) {
            model.addAttribute("sanswer", answer3List.get(answer3Index++));
            model.addAttribute("pid", tpaperid);
            model.addAttribute("subid", subjectid);
            model.addAttribute("answer", answer);
            model.addAttribute("analysis", analysis);
            model.addAttribute("question", question);
            return "comAnswer";
        } else {
            answer3List = null;
            answer3Index = 0;

            HashMap<String, Object> map = new HashMap<>();
            map.put("tpaperid", tpaperid);

            List<Formation3> completions = formation3Mapper.selectByMap(map);
            if (!completions.isEmpty()) {
                ArrayList<Integer> list = new ArrayList<>();
                for (Formation3 completion : completions) {
                    list.add(completion.getQuestionid());
                }
                List<Completion> completions1 = completionMapper.selectBatchIds(list);
                model.addAttribute("completions", completions1);
                model.addAttribute("displacecompl", "yes");
            }

            List<Formation4> saqs = formation4Mapper.selectByMap(map);
            if (!saqs.isEmpty()) {
                ArrayList<Integer> list = new ArrayList<>();
                for (Formation4 saq : saqs) {
                    list.add(saq.getQuestionid());
                }
                List<Shortanswerquestion> shortanswerquestions = saqMapper.selectBatchIds(list);
                model.addAttribute("shortanswerquestions", shortanswerquestions);
                model.addAttribute("displacesaq", "yes");
            }
            model.addAttribute("pid", tpaperid);
            model.addAttribute("subid", tpaperid);

            return "questionAndAnswerToBeMarked";
        }

    }

    private List<Answer4> answer4List = null;
    private int answer4Index = 0;

    @RequestMapping("/oneSAQAllAnswers")
    public String oneSAQAllAnswers(@Param("tpaperid") int tpaperid,
                                   @Param("subjectid") int subjectid,
                                   @Param("questionid") int questionid,
                                   @Param("answer") String answer,
                                   @Param("analysis") String analysis,
                                   @Param("question") String question,
                                   Model model) {

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("tpaperid", tpaperid);
        map1.put("questionid", questionid);
        map1.put("isread", false);

        List<Answer4> answer4s = answer4Mapper.selectByMap(map1);

        if (!answer4s.isEmpty()) {
            answer4List = answer4s;
            model.addAttribute("sanswer", answer4List.get(answer4Index++));
            model.addAttribute("pid", tpaperid);
            model.addAttribute("subid", subjectid);
            model.addAttribute("answer", answer);
            model.addAttribute("analysis", analysis);
            model.addAttribute("question", question);
            return "saqAnswer";
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", tpaperid);

        List<Formation3> completions = formation3Mapper.selectByMap(map);
        if (!completions.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation3 completion : completions) {
                list.add(completion.getQuestionid());
            }
            List<Completion> completions1 = completionMapper.selectBatchIds(list);
            model.addAttribute("completions", completions1);
            model.addAttribute("displacecompl", "yes");
        }

        List<Formation4> saqs = formation4Mapper.selectByMap(map);
        if (!saqs.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Formation4 saq : saqs) {
                list.add(saq.getQuestionid());
            }
            List<Shortanswerquestion> shortanswerquestions = saqMapper.selectBatchIds(list);
            model.addAttribute("shortanswerquestions", shortanswerquestions);
            model.addAttribute("displacesaq", "yes");
        }

        model.addAttribute("pid", tpaperid);
        model.addAttribute("subid", tpaperid);

        return "questionAndAnswerToBeMarked";
    }

    @RequestMapping("/insertWeight2Score")
    public String insertWeight2Score(@Param("question") String question,
                                     @Param("answer") String answer,
                                     @Param("analysis") String analysis,
                                     @Param("tpaperid") int tpaperid,
                                     @Param("subjectid") int subjectid,
                                     @Param("studentid") int studentid,
                                     @Param("answerid") int answerid,
                                     @Param("weight") int weight,
                                     Model model) {
        Answer4 answer4 = answer4Mapper.selectById(answerid);
        answer4.setIsread(true);
        answer4.setActualscore(weight);
        answer4Mapper.updateById(answer4);

        QueryWrapper<Score> wrapper = new QueryWrapper<>();
        wrapper
                .eq("tpaperid", tpaperid)
                .eq("studentid", studentid);

        Score score = scoreMapper.selectOne(wrapper);
        if (score != null) {
            score.setScore(score.getScore() + weight);
            scoreMapper.updateById(score);
        } else {
            score = new Score();
            score.setScore(weight);
            score.setTpaperid(tpaperid);
            score.setSubjectid(subjectid);
            score.setStudentid(studentid);
            scoreMapper.insert(score);
        }

        if (answer4Index < answer4List.size()) {
            model.addAttribute("sanswer", answer4List.get(answer4Index++));
            model.addAttribute("pid", tpaperid);
            model.addAttribute("subid", subjectid);
            model.addAttribute("answer", answer);
            model.addAttribute("analysis", analysis);
            model.addAttribute("question", question);
            return "saqAnswer";
        } else {
            answer4List = null;
            answer4Index = 0;

            HashMap<String, Object> map = new HashMap<>();
            map.put("tpaperid", tpaperid);

            List<Formation3> completions = formation3Mapper.selectByMap(map);
            if (!completions.isEmpty()) {
                ArrayList<Integer> list = new ArrayList<>();
                for (Formation3 completion : completions) {
                    list.add(completion.getQuestionid());
                }
                List<Completion> completions1 = completionMapper.selectBatchIds(list);
                model.addAttribute("completions", completions1);
                model.addAttribute("displacecompl", "yes");
            }

            List<Formation4> saqs = formation4Mapper.selectByMap(map);
            if (!saqs.isEmpty()) {
                ArrayList<Integer> list = new ArrayList<>();
                for (Formation4 saq : saqs) {
                    list.add(saq.getQuestionid());
                }
                List<Shortanswerquestion> shortanswerquestions = saqMapper.selectBatchIds(list);
                model.addAttribute("shortanswerquestions", shortanswerquestions);
                model.addAttribute("displacesaq", "yes");
            }
            model.addAttribute("pid", tpaperid);
            model.addAttribute("subid", tpaperid);

            return "questionAndAnswerToBeMarked";
        }
    }

    @RequestMapping("/selectStudentScore")
    public String selectStudentScore(@Param("tid") String tid,
                                     Model model) {
        int ttid = Integer.parseInt(tid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherid", ttid);
        List<Teach> teaches = teachMapper.selectByMap(map);

        if (!teaches.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Teach teach : teaches) {
                list.add(teach.getSubjectid());
            }

            List<Subject> subjects = subjectMapper.selectBatchIds(list);
            model.addAttribute("subjects", subjects);
            model.addAttribute("displace", "subject3");
            return "teacher";
        } else {
            model.addAttribute("submsg", "暂时没有课程可以进行成绩查看");
            return "teacher";
        }
    }

    @RequestMapping("/selectSubjectPaperToSeeScore")
    public String selectSubjectPaperToSeeScore(@Param("subid") int subid,
                                               @Param("tid") String tid,
                                               Model model) {
        int ttid = Integer.parseInt(tid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherid", ttid);
        map.put("subjectid", subid);
        List<Testpaper> testpapers = testpaperMapper.selectByMap(map);
        if (testpapers.isEmpty()) {
            model.addAttribute("paperMsg", "此课程下没有试卷");
        }
        model.addAttribute("testpapers", testpapers);
        model.addAttribute("subid", subid);
        model.addAttribute("tid", ttid);
        return "paperListToSeeScore";
    }

    @RequestMapping("/selectSubjectPaperToSeeScore2")
    public String selectSubjectPaperToSeeScore2(@Param("subid") int subid,
                                                @Param("tid") String tid,
                                                Model model) {
        int ttid = Integer.parseInt(tid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subid);
        List<Testpaper> testpapers = testpaperMapper.selectByMap(map);
        if (testpapers.isEmpty()) {
            model.addAttribute("paperMsg", "此课程下没有试卷");
        }
        model.addAttribute("testpapers", testpapers);
        model.addAttribute("subid", subid);
        model.addAttribute("tid", ttid);
        return "paperListToSeeScore";
    }

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private Answer1Mapper answer1Mapper;

    @Autowired
    private Answer2Mapper answer2Mapper;

    @RequestMapping("/selectThisPaperScores")
    public String selectThisPaperScores(@Param("pid") int pid,
                                        @Param("pname") String pname,
                                        Model model) {
        QueryWrapper<Score> wrapper = new QueryWrapper<>();

        wrapper
                .orderByDesc("score")
                .eq("tpaperid", pid);

        List<Score> scores = scoreMapper.selectList(wrapper);

        if (!scores.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            ArrayList<StudentScore> studentscore = new ArrayList<>();
            for (Score score : scores) {
                list.add(score.getStudentid());
            }

            List<Student> students = studentMapper.selectBatchIds(list);
            int rank = 0;
            for (Score score : scores) {
                for (Student student : students) {
                    if (score.getStudentid() == student.getStudentid()) {
                        studentscore.add(new StudentScore(++rank, student, score));
                    }
                }
            }

            model.addAttribute("scores", studentscore);
            //成绩分析
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            int count4 = 0;
            int count5 = 0;
            int totalscore = testpaperMapper.selectById(pid).getTotalscore();
            for (Score score : scores) {
                if ((int) ((double) score.getScore() / totalscore * 100) < 60) {
                    count1++;
                } else if ((int) ((double) score.getScore() / totalscore * 100) < 70 && (int) ((double) score.getScore() / totalscore * 100) >= 60) {
                    count2++;
                } else if ((int) ((double) score.getScore() / totalscore * 100) < 80 && (int) ((double) score.getScore() / totalscore * 100) >= 70) {
                    count3++;
                } else if ((int) ((double) score.getScore() / totalscore * 100) < 90 && (int) ((double) score.getScore() / totalscore * 100) >= 80) {
                    count4++;
                } else if ((int) ((double) score.getScore() / totalscore * 100) <= 100 && (int) ((double) score.getScore() / totalscore * 100) >= 90) {
                    count5++;
                }
            }
            HashMap<String, Object> dataList = new HashMap<>();
            dataList.put("name", "《" + testpaperMapper.selectById(pid).getTpapername() + "》不同得分率人数统计");
            dataList.put("worst", count1);
            dataList.put("sixty", count2);
            dataList.put("senty", count3);
            dataList.put("eity", count4);
            dataList.put("best", count5);
            model.addAttribute("dataList", JSON.toJSON(dataList));


        } else {
            HashMap<String, Object> dataList = new HashMap<>();
            dataList.put("name", "《" + testpaperMapper.selectById(pid).getTpapername() + "》不同得分率人数统计");
            dataList.put("worst", 0);
            dataList.put("sixty", 0);
            dataList.put("senty", 0);
            dataList.put("eity", 0);
            dataList.put("best", 0);
            model.addAttribute("dataList", JSON.toJSON(dataList));
            model.addAttribute("scoreMsg", "暂无成绩");
        }

        //试题正确率统计分析
        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", pid);


        //选择题试题分析
        List<Formation1> formation1s = formation1Mapper.selectByMap(map);
        if (!formation1s.isEmpty()) {
            ArrayList<ChoiceAndData> choiceList = new ArrayList<>();
            for (Formation1 formation1 : formation1s) {
                Choice choice = choiceMapper.selectById(formation1.getQuestionid());
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("tpaperid", pid);
                map1.put("questionid", formation1.getQuestionid());
                List<Answer1> answer1s = answer1Mapper.selectByMap(map1);
                if (!answer1s.isEmpty()) {
                    double accuracy;//答对题目人数/答题总人数
                    int count1 = 0;//答对题目的人数
                    for (Answer1 answer1 : answer1s) {
                        if (answer1.getActualscore() == answer1.getWeight()){
                            count1++;
                        }
                    }
                    accuracy = (double) count1/answer1s.size();
                    choiceList.add(new ChoiceAndData(choice,accuracy));
                }else {
                    choiceList.add(new ChoiceAndData(choice,0));
                }
            }
            model.addAttribute("choices",choiceList);
            model.addAttribute("choiceinfo","have");
        } else {
            model.addAttribute("choiceinfo","none");
        }
        //判断题
        List<Formation2> formation2s = formation2Mapper.selectByMap(map);
        if (!formation2s.isEmpty()){
            ArrayList<JudgeAndData> judgeList = new ArrayList<>();
            for (Formation2 formation2 : formation2s) {
                Judge judge = judgeMapper.selectById(formation2.getQuestionid());
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("tpaperid", pid);
                map1.put("questionid", formation2.getQuestionid());
                List<Answer2> answer2s = answer2Mapper.selectByMap(map1);
                if(!answer2s.isEmpty()){
                    double accuracy;//答对题目人数/答题总人数
                    int count1 = 0;//答对题目的人数
                    for (Answer2 answer2 : answer2s) {
                        if (answer2.getActualscore() == answer2.getWeight()){
                            count1++;
                        }
                    }
                    accuracy = (double) count1/answer2s.size();
                    judgeList.add(new JudgeAndData(judge,accuracy));
                }else {
                    judgeList.add(new JudgeAndData(judge,0));
                }
            }
            model.addAttribute("judges",judgeList);
            model.addAttribute("judgeinfo","have");
        }else {
            model.addAttribute("judgeinfo","none");
        }

        //填空题
        List<Formation3> formation3s = formation3Mapper.selectByMap(map);
        if (!formation3s.isEmpty()){
            ArrayList<CompletionAndData> completionList = new ArrayList<>();
            for (Formation3 formation3 : formation3s) {
                Completion completion = completionMapper.selectById(formation3.getQuestionid());
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("tpaperid", pid);
                map1.put("questionid", formation3.getQuestionid());
                List<Answer3> answer3s = answer3Mapper.selectByMap(map1);
                if (!answer3s.isEmpty()){
                    double accuracy;//答对题目人数/答题总人数
                    int count1 = 0;//答对题目的人数
                    int count3 = 0;//答题总人数
                    for (Answer3 answer3 : answer3s) {
                        if (answer3.isIsread()){
                            count3++;
                            if (answer3.getActualscore() == answer3.getWeight()){
                                count1++;
                            }
                        }
                    }
                    accuracy = (double) count1/count3;
                    completionList.add(new CompletionAndData(completion,accuracy));
                }else {
                    completionList.add(new CompletionAndData(completion,0));
                }
            }
            model.addAttribute("completions",completionList);
            model.addAttribute("compleinfo","have");
        }else {
            model.addAttribute("compleinfo","none");
        }

        //简答题
        List<Formation4> formation4s = formation4Mapper.selectByMap(map);
        if (!formation4s.isEmpty()){
            ArrayList<SAQAndData> saqList = new ArrayList<>();
            for (Formation4 formation4 : formation4s) {
                Shortanswerquestion saq = saqMapper.selectById(formation4.getQuestionid());
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("tpaperid", pid);
                map1.put("questionid", formation4.getQuestionid());
                List<Answer4> answer4s = answer4Mapper.selectByMap(map1);
                if (!answer4s.isEmpty()){
                    double accuracy;//答对题目人数/答题总人数
                    double scoringRate;//得分人数/答题总人数
                    int count1 = 0;//答对题目的人数
                    int count2 = 0;//得分人数
                    int count3 = 0;//答题总人数
                    for (Answer4 answer4 : answer4s) {
                        if (answer4.isIsread()){
                            count3++;
                            if (answer4.getActualscore()>0){
                                count2++;
                            }
                            if (answer4.getActualscore() == answer4.getWeight()){
                                count1++;
                            }
                        }
                    }
                    accuracy = (double) count1/count3;
                    scoringRate = (double) count2/count3;
                    saqList.add(new SAQAndData(saq,scoringRate,accuracy));
                }else {
                    saqList.add(new SAQAndData(saq,0,0));
                }
            }
            model.addAttribute("saqs",saqList);
            model.addAttribute("saqinfo","have");
        }else {
            model.addAttribute("saqinfo","none");
        }

        model.addAttribute("pname", pname);
        model.addAttribute("pid", pid);
        return "scoreList";
    }

    @RequestMapping("/selectThisPaperScoresAsc")
    public String selectThisPaperScoresAsc(@Param("pid") int pid,
                                           @Param("pname") String pname,
                                           Model model) {
        QueryWrapper<Score> wrapper = new QueryWrapper<>();

        wrapper
                .orderByAsc("score")
                .eq("tpaperid", pid);

        List<Score> scores = scoreMapper.selectList(wrapper);

        if (!scores.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            ArrayList<StudentScore> studentscore = new ArrayList<>();
            for (Score score : scores) {
                list.add(score.getStudentid());
            }

            List<Student> students = studentMapper.selectBatchIds(list);
            int rank = scores.size();
            for (Score score : scores) {
                for (Student student : students) {
                    if (score.getStudentid() == student.getStudentid()) {
                        studentscore.add(new StudentScore(rank--, student, score));
                    }
                }
            }

            model.addAttribute("scores", studentscore);
            //成绩分析
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            int count4 = 0;
            int count5 = 0;
            int totalscore = testpaperMapper.selectById(pid).getTotalscore();
            for (Score score : scores) {
                if ((int) ((double) score.getScore() / totalscore * 100) < 60) {
                    count1++;
                } else if ((int) ((double) score.getScore() / totalscore * 100) < 70 && (int) ((double) score.getScore() / totalscore * 100) >= 60) {
                    count2++;
                } else if ((int) ((double) score.getScore() / totalscore * 100) < 80 && (int) ((double) score.getScore() / totalscore * 100) >= 70) {
                    count3++;
                } else if ((int) ((double) score.getScore() / totalscore * 100) < 90 && (int) ((double) score.getScore() / totalscore * 100) >= 80) {
                    count4++;
                } else if ((int) ((double) score.getScore() / totalscore * 100) <= 100 && (int) ((double) score.getScore() / totalscore * 100) >= 90) {
                    count5++;
                }
            }
            HashMap<String, Object> dataList = new HashMap<>();
            dataList.put("name", "《" + testpaperMapper.selectById(pid).getTpapername() + "》不同得分率人数统计");
            dataList.put("worst", count1);
            dataList.put("sixty", count2);
            dataList.put("senty", count3);
            dataList.put("eity", count4);
            dataList.put("best", count5);
            model.addAttribute("dataList", JSON.toJSON(dataList));
        } else {
            HashMap<String, Object> dataList = new HashMap<>();
            dataList.put("name", "《" + testpaperMapper.selectById(pid).getTpapername() + "》不同得分率人数统计");
            dataList.put("worst", 0);
            dataList.put("sixty", 0);
            dataList.put("senty", 0);
            dataList.put("eity", 0);
            dataList.put("best", 0);
            model.addAttribute("dataList", JSON.toJSON(dataList));
            model.addAttribute("scoreMsg", "暂无成绩");
        }

        //试题正确率统计分析
        HashMap<String, Object> map = new HashMap<>();
        map.put("tpaperid", pid);

        //选择题试题分析
        List<Formation1> formation1s = formation1Mapper.selectByMap(map);
        if (!formation1s.isEmpty()) {
            ArrayList<ChoiceAndData> choiceList = new ArrayList<>();
            for (Formation1 formation1 : formation1s) {
                Choice choice = choiceMapper.selectById(formation1.getQuestionid());
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("tpaperid", pid);
                map1.put("questionid", formation1.getQuestionid());
                List<Answer1> answer1s = answer1Mapper.selectByMap(map1);
                if (!answer1s.isEmpty()) {
                    double accuracy;//答对题目人数/答题总人数
                    int count1 = 0;//答对题目的人数
                    for (Answer1 answer1 : answer1s) {
                        if (answer1.getActualscore() == answer1.getWeight()){
                            count1++;
                        }
                    }
                    accuracy = (double) count1/answer1s.size();
                    choiceList.add(new ChoiceAndData(choice,accuracy));
                }else {
                    choiceList.add(new ChoiceAndData(choice,0));
                }
            }
            model.addAttribute("choices",choiceList);
            model.addAttribute("choiceinfo","have");
        } else {
            model.addAttribute("choiceinfo","none");
        }

        //判断题
        List<Formation2> formation2s = formation2Mapper.selectByMap(map);
        if (!formation2s.isEmpty()){
            ArrayList<JudgeAndData> judgeList = new ArrayList<>();
            for (Formation2 formation2 : formation2s) {
                Judge judge = judgeMapper.selectById(formation2.getQuestionid());
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("tpaperid", pid);
                map1.put("questionid", formation2.getQuestionid());
                List<Answer2> answer2s = answer2Mapper.selectByMap(map1);
                if(!answer2s.isEmpty()){
                    double accuracy;//答对题目人数/答题总人数
                    int count1 = 0;//答对题目的人数
                    for (Answer2 answer2 : answer2s) {
                        if (answer2.getActualscore() == answer2.getWeight()){
                            count1++;
                        }
                    }
                    accuracy = (double) count1/answer2s.size();
                    judgeList.add(new JudgeAndData(judge,accuracy));
                }else {
                    judgeList.add(new JudgeAndData(judge,0));
                }
            }
            model.addAttribute("judges",judgeList);
            model.addAttribute("judgeinfo","have");
        }else {
            model.addAttribute("judgeinfo","none");
        }

        //填空题
        List<Formation3> formation3s = formation3Mapper.selectByMap(map);
        if (!formation3s.isEmpty()){
            ArrayList<CompletionAndData> completionList = new ArrayList<>();
            for (Formation3 formation3 : formation3s) {
                Completion completion = completionMapper.selectById(formation3.getQuestionid());
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("tpaperid", pid);
                map1.put("questionid", formation3.getQuestionid());
                List<Answer3> answer3s = answer3Mapper.selectByMap(map1);
                if (!answer3s.isEmpty()){
                    double accuracy;//答对题目人数/答题总人数
                    int count1 = 0;//答对题目的人数
                    int count3 = 0;//答题总人数
                    for (Answer3 answer3 : answer3s) {
                        if (answer3.isIsread()){
                            count3++;
                            if (answer3.getActualscore() == answer3.getWeight()){
                                count1++;
                            }
                        }
                    }
                    accuracy = (double) count1/count3;
                    completionList.add(new CompletionAndData(completion,accuracy));
                }else {
                    completionList.add(new CompletionAndData(completion,0));
                }
            }
            model.addAttribute("completions",completionList);
            model.addAttribute("compleinfo","have");
        }else {
            model.addAttribute("compleinfo","none");
        }

        //简答题
        List<Formation4> formation4s = formation4Mapper.selectByMap(map);
        if (!formation4s.isEmpty()){
            ArrayList<SAQAndData> saqList = new ArrayList<>();
            for (Formation4 formation4 : formation4s) {
                Shortanswerquestion saq = saqMapper.selectById(formation4.getQuestionid());
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("tpaperid", pid);
                map1.put("questionid", formation4.getQuestionid());
                List<Answer4> answer4s = answer4Mapper.selectByMap(map1);
                if (!answer4s.isEmpty()){
                    double accuracy;//答对题目人数/答题总人数
                    double scoringRate;//得分人数/答题总人数
                    int count1 = 0;//答对题目的人数
                    int count2 = 0;//得分人数
                    int count3 = 0;//答题总人数
                    for (Answer4 answer4 : answer4s) {
                        if (answer4.isIsread()){
                            count3++;
                            if (answer4.getActualscore()>0){
                                count2++;
                            }
                            if (answer4.getActualscore() == answer4.getWeight()){
                                count1++;
                            }
                        }
                    }
                    accuracy = (double) count1/count3;
                    scoringRate = (double) count2/count3;
                    saqList.add(new SAQAndData(saq,scoringRate,accuracy));
                }else {
                    saqList.add(new SAQAndData(saq,0,0));
                }
            }
            model.addAttribute("saqs",saqList);
            model.addAttribute("saqinfo","have");
        }else {
            model.addAttribute("saqinfo","none");
        }

        model.addAttribute("pname", pname);
        model.addAttribute("pid", pid);
        return "scoreList";
    }
    @Autowired
    private TeacherMapper teacherMapper;

    @RequestMapping("/updateTeinfo")
    public String updateTeinfo(String id,
                               Model model){
        Teacher teacher = teacherMapper.selectById(Integer.parseInt(id));
        model.addAttribute("teinfo",teacher);
        model.addAttribute("displace","updateInfo");
        return "teacher";
    }

    @RequestMapping("/updateTe")
    public String updateTe(@Param("id") int id,
                           @Param("pwd") String pwd,
                           @Param("phone") String phone,
                           Model model){
        Teacher teacher = teacherMapper.selectById(id);
        teacher.setTeacherpassword(pwd);
        teacher.setTeacherphone(phone);
        teacherMapper.updateById(teacher);
        Teacher teacher1 = teacherMapper.selectById(id);
        model.addAttribute("teinfo",teacher1);
        model.addAttribute("displace","updateInfo");
        return "teacher";
    }

}
