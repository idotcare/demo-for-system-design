package com.zgw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zgw.mapper.*;
import com.zgw.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: zgw
 * @Date: 2021/4/24 - 04 - 24 - 22:14
 * @Description: com.zgw.controller
 * @version: 1.0
 */

@Controller
public class StudentController {

    @Autowired
    private TestMapper testMapper;

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
    private ChoiceMapper choiceMapper;

    @Autowired
    private JudgeMapper judgeMapper;

    @Autowired
    private CompletionMapper completionMapper;

    @Autowired
    private ShortanswerquestionMapper saqMapper;

    @Autowired
    private Answer1Mapper answer1Mapper;

    @Autowired
    private Answer2Mapper answer2Mapper;

    @Autowired
    private Answer3Mapper answer3Mapper;

    @Autowired
    private Answer4Mapper answer4Mapper;

    @Autowired
    private ScoreMapper scoreMapper;

    private ArrayList<ChoiceAndWeight> choiceCache = null;
    private ArrayList<JudgeAndWeight> judgeCache = null;
    private ArrayList<CompletionAndWeight> completionCache = null;
    private ArrayList<SAQAndWeight> saqCache = null;

    private int questionIndex = 0; //试卷试题数


    @RequestMapping("/TakePartInTest")
    public String takePartInTest(@Param("sid") String sid,
                                 Model model) {
        int stid = Integer.parseInt(sid);

        HashMap<String, Object> map = new HashMap<>();
        map.put("studentid", stid);

        List<Test> tests = testMapper.selectByMap(map);

        if(!tests.isEmpty()){
            ArrayList<Integer> list = new ArrayList<>();
            for (Test test : tests) {
                list.add(test.getTpaperid());
            }
            List<Testpaper> testpapers = testpaperMapper.selectBatchIds(list);
            model.addAttribute("testpapers", testpapers);
        }else {
            model.addAttribute("testinfo","暂时没有可以参加的考试");
        }
        model.addAttribute("displace", "testpapers");
        return "student";
    }

    @RequestMapping("/getPaperQuestionPage")
    public String getPaperQuestionPage(@Param("tpaperid") int tpaperid,
                                       @Param("sid") String sid,
                                       Model model) {

        QueryWrapper<Testpaper> wrapper = new QueryWrapper<>();
        wrapper.eq("tpaperid", tpaperid);
        Testpaper testpaper = testpaperMapper.selectOne(wrapper);

        int stid = Integer.parseInt(sid);

        QueryWrapper<Test> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("tpaperid", tpaperid)
                .eq("studentid", stid);

        Test test1 = testMapper.selectOne(wrapper1);


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date now = null;
        Date start = null;
        Date end = null;

        try {
            now = df.parse(df.format(new Date()));
            start = df.parse(df.format(testpaper.getStarttime()));
            end = df.parse(df.format(testpaper.getEndtime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int ifstart = now.compareTo(start);//如果now在start之后，返回1
        int ifend = now.compareTo(end);//如果now在end之前，返回-1

        if (ifstart == 1 && ifend == -1 && !test1.isIstest()) {//在考试时间内且尚未测试过

            //记得打开-----------------------------------------------------------------------
            test1.setIstest(true);
            testMapper.updateById(test1);
            //------------------------------------------------------------------------------

            HashMap<String, Object> map = new HashMap<>();
            map.put("tpaperid", tpaperid);
            List<Formation1> choices = formation1Mapper.selectByMap(map);
            if (!choices.isEmpty()) {
                ArrayList<Integer> list = new ArrayList<>();
                ArrayList<ChoiceAndWeight> list1 = new ArrayList<>();
                for (Formation1 choice : choices) {
                    list.add(choice.getQuestionid());
                }
                List<Choice> choices1 = choiceMapper.selectBatchIds(list);
                for (Choice choice : choices1) {
                    for (Formation1 formation1 : choices) {
                        if (choice.getChoiceid() == formation1.getQuestionid()) {
                            ChoiceAndWeight choiceAndWeight = new ChoiceAndWeight();
                            choiceAndWeight.setChoice(choice);
                            choiceAndWeight.setFormation1(formation1);
                            list1.add(choiceAndWeight);
                        }
                    }
                }
                choiceCache = list1;
                //model.addAttribute("displayChoice", "yes");
                //model.addAttribute("choices", choiceCache);
            } else {
                choiceCache = null;
                //model.addAttribute("displayChoice", "no");
            }
            List<Formation2> judges = formation2Mapper.selectByMap(map);
            if (!judges.isEmpty()) {
                ArrayList<Integer> list = new ArrayList<>();
                ArrayList<JudgeAndWeight> list1 = new ArrayList<>();
                for (Formation2 judge : judges) {
                    list.add(judge.getQuestionid());
                }
                List<Judge> judges1 = judgeMapper.selectBatchIds(list);
                for (Judge judge : judges1) {
                    for (Formation2 formation2 : judges) {
                        if (judge.getJudgeid() == formation2.getQuestionid()) {
                            JudgeAndWeight judgeAndWeight = new JudgeAndWeight(judge, formation2);
                            list1.add(judgeAndWeight);
                        }
                    }
                }
                judgeCache = list1;
                //model.addAttribute("judges", judgeCache);
                //model.addAttribute("displayJudge", "yes");
            } else {
                judgeCache = null;
                //model.addAttribute("displayJudge", "no");
            }
            List<Formation3> completions = formation3Mapper.selectByMap(map);
            if (!completions.isEmpty()) {
                ArrayList<Integer> list = new ArrayList<>();
                ArrayList<CompletionAndWeight> list1 = new ArrayList<>();
                for (Formation3 completion : completions) {
                    list.add(completion.getQuestionid());
                }
                List<Completion> completions1 = completionMapper.selectBatchIds(list);
                for (Completion completion : completions1) {
                    for (Formation3 formation3 : completions) {
                        if (completion.getCompletid() == formation3.getQuestionid()) {
                            CompletionAndWeight completionAndWeight = new CompletionAndWeight(completion, formation3);
                            list1.add(completionAndWeight);
                        }
                    }
                }
                completionCache = list1;
                //model.addAttribute("completions", completionCache);
                //model.addAttribute("displayComp", "yes");
            } else {
                completionCache = null;
                //model.addAttribute("displayComp", "no");
            }
            List<Formation4> saqs = formation4Mapper.selectByMap(map);

            if (!saqs.isEmpty()) {
                ArrayList<Integer> list = new ArrayList<>();
                ArrayList<SAQAndWeight> list1 = new ArrayList<>();
                for (Formation4 saq : saqs) {
                    list.add(saq.getQuestionid());
                }
                List<Shortanswerquestion> shortanswerquestions = saqMapper.selectBatchIds(list);
                for (Shortanswerquestion saq : shortanswerquestions) {
                    for (Formation4 formation4 : saqs) {
                        if (saq.getSaqid() == formation4.getQuestionid()) {
                            SAQAndWeight saqAndWeight = new SAQAndWeight(saq, formation4);
                            list1.add(saqAndWeight);
                        }
                    }

                }
                saqCache = list1;
                //model.addAttribute("shortanswerquestions", saqCache);
                //model.addAttribute("displaySAQ", "yes");
            } else {
                saqCache = null;
                //model.addAttribute("displaySAQ", "no");
            }

            model.addAttribute("testpaper", testpaper);
            model.addAttribute("studentid", stid);

            if (choiceCache != null) {
                model.addAttribute("choice", choiceCache.get(questionIndex++));
                model.addAttribute("displayChoice", "yes");
                return "answerThePaperQuestion";
            } else if (judgeCache != null) {
                model.addAttribute("judge", judgeCache.get(questionIndex++));
                model.addAttribute("displayJudge", "yes");
                return "answerThePaperQuestion";
            } else if (completionCache != null) {
                model.addAttribute("completion", completionCache.get(questionIndex++));
                model.addAttribute("displayComp", "yes");
                return "answerThePaperQuestion";
            } else if (saqCache != null) {
                model.addAttribute("saq", saqCache.get(questionIndex++));
                model.addAttribute("displaySAQ", "yes");
                return "answerThePaperQuestion";
            } else {
                return "answerThePaperQuestion";
            }

        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("studentid", stid);

        List<Test> tests = testMapper.selectByMap(map);

        ArrayList<Integer> list = new ArrayList<>();

        for (Test test : tests) {
            list.add(test.getTpaperid());
        }

        List<Testpaper> testpapers = testpaperMapper.selectBatchIds(list);

        model.addAttribute("testpapers", testpapers);
        model.addAttribute("displace", "testpapers");

        return "student";
    }

    @RequestMapping("/submitChoiceAnswer")
    public String submitChoiceAnswer(@Param("weight") int weight,
                                     @Param("answer") String answer,
                                     @Param("tpaperid") int tpaperid,
                                     @Param("studentid") int studentid,
                                     @Param("questionid") int questionid,
                                     Model model) {
        QueryWrapper<Testpaper> wrapper = new QueryWrapper<>();
        wrapper.eq("tpaperid", tpaperid);
        Testpaper testpaper = testpaperMapper.selectOne(wrapper);

        Answer1 answer1 = new Answer1();
        answer1.setAnswer(answer);
        answer1.setTpaperid(testpaper.getTpaperid());
        answer1.setStudentid(studentid);
        answer1.setQuestionid(questionid);
        answer1.setWeight(weight);

        Choice choice1 = choiceMapper.selectById(questionid);
        if (choice1.getChoiceanswer().equals(answer)) {
            answer1.setActualscore(weight);
        } else {
            answer1.setActualscore(0);
        }
        answer1.setIsread(true);

        QueryWrapper<Answer1> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("studentid", studentid)
                .eq("questionid", questionid)
                .eq("tpaperid", testpaper.getTpaperid());
        Answer1 answer11 = answer1Mapper.selectOne(wrapper1);

        if (answer11 == null) {
            answer1Mapper.insert(answer1);
        } else {
            answer1.setAnswerid(answer11.getAnswerid());
            answer1Mapper.updateById(answer1);
        }

        QueryWrapper<Score> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("studentid", studentid)
                .eq("tpaperid", testpaper.getTpaperid())
                .eq("subjectid", testpaper.getSubjectid());
        Score score = scoreMapper.selectOne(wrapper2);

        if (score != null) {
            score.setScore(score.getScore() + answer1.getActualscore());
            scoreMapper.updateById(score);
        } else {
            score = new Score();
            score.setStudentid(studentid);
            score.setSubjectid(testpaper.getSubjectid());
            score.setTpaperid(testpaper.getTpaperid());
            score.setScore(weight);
            scoreMapper.insert(score);
        }

        model.addAttribute("testpaper", testpaper);
        model.addAttribute("studentid", studentid);
        if (questionIndex < choiceCache.size()) {
            model.addAttribute("choice", choiceCache.get(questionIndex++));
            model.addAttribute("displayChoice", "yes");
            return "answerThePaperQuestion";
        } else if (judgeCache != null) {
            questionIndex = 0;
            model.addAttribute("judge", judgeCache.get(questionIndex++));
            model.addAttribute("displayJudge", "yes");
            return "answerThePaperQuestion";
        } else if (completionCache != null) {
            questionIndex = 0;
            model.addAttribute("completion", completionCache.get(questionIndex++));
            model.addAttribute("displayComp", "yes");
            return "answerThePaperQuestion";
        } else if (saqCache != null) {
            questionIndex = 0;
            model.addAttribute("saq", saqCache.get(questionIndex++));
            model.addAttribute("displaySAQ", "yes");
            return "answerThePaperQuestion";
        } else {
            questionIndex = 0;
            choiceCache = null;
            judgeCache = null;
            completionCache = null;
            saqCache = null;
            model.addAttribute("questionMsg","试题作答完毕，考试结束");
            return "answerThePaperQuestion";
        }
    }

    @RequestMapping("/submitJudgeAnsewr")
    public String submitJudgeAnsewr(@Param("weight") int weight,
                                    @Param("answer") String answer,
                                    @Param("tpaperid") int tpaperid,
                                    @Param("studentid") int studentid,
                                    @Param("questionid") int questionid,
                                    Model model) {
        QueryWrapper<Testpaper> wrapper = new QueryWrapper<>();
        wrapper.eq("tpaperid", tpaperid);
        Testpaper testpaper = testpaperMapper.selectOne(wrapper);

        Answer2 answer2 = new Answer2();
        answer2.setAnswer(answer);
        answer2.setTpaperid(tpaperid);
        answer2.setStudentid(studentid);
        answer2.setQuestionid(questionid);
        answer2.setWeight(weight);


        Judge judge1 = judgeMapper.selectById(questionid);
        if (judge1.getJudgeanswer().equals(answer)){
            answer2.setActualscore(weight);
        }else{
            answer2.setActualscore(0);
        }
        answer2.setIsread(true);

        QueryWrapper<Answer2> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("studentid", studentid)
                .eq("questionid", questionid)
                .eq("tpaperid", tpaperid);
        Answer2 answer22 = answer2Mapper.selectOne(wrapper1);

        if (answer22 == null) {
            answer2Mapper.insert(answer2);
        } else {
            answer2.setAnswerid(answer22.getAnswerid());
            answer2Mapper.updateById(answer2);
        }

        QueryWrapper<Score> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("studentid", studentid)
                .eq("tpaperid", testpaper.getTpaperid())
                .eq("subjectid", testpaper.getSubjectid());
        Score score = scoreMapper.selectOne(wrapper2);

        if (score != null) {
            score.setScore(score.getScore() + answer2.getActualscore());
            scoreMapper.updateById(score);
        } else {
            score = new Score();
            score.setStudentid(studentid);
            score.setSubjectid(testpaper.getSubjectid());
            score.setTpaperid(testpaper.getTpaperid());
            score.setScore(weight);
            scoreMapper.insert(score);
        }

        model.addAttribute("testpaper", testpaper);
        model.addAttribute("studentid", studentid);

        if (questionIndex < judgeCache.size()) {
            model.addAttribute("judge", judgeCache.get(questionIndex++));
            model.addAttribute("displayJudge", "yes");
            return "answerThePaperQuestion";
        } else if (completionCache != null) {
            questionIndex = 0;
            model.addAttribute("completion", completionCache.get(questionIndex++));
            model.addAttribute("displayComp", "yes");
            return "answerThePaperQuestion";
        } else if (saqCache != null) {
            questionIndex = 0;
            model.addAttribute("saq", saqCache.get(questionIndex++));
            model.addAttribute("displaySAQ", "yes");
            return "answerThePaperQuestion";
        } else {
            questionIndex = 0;
            choiceCache = null;
            judgeCache = null;
            completionCache = null;
            saqCache = null;
            model.addAttribute("questionMsg","试题作答完毕，考试结束");
            return "answerThePaperQuestion";
        }
    }

    @RequestMapping("/submitCompletionAnswer")
    public String submitCompletionAnswer(@Param("weight") int weight,
                                         @Param("answer") String answer,
                                         @Param("tpaperid") int tpaperid,
                                         @Param("studentid") int studentid,
                                         @Param("questionid") int questionid,
                                         Model model) {
        QueryWrapper<Testpaper> wrapper = new QueryWrapper<>();
        wrapper.eq("tpaperid", tpaperid);
        Testpaper testpaper = testpaperMapper.selectOne(wrapper);

        Answer3 answer3 = new Answer3();
        answer3.setActualscore(0);
        answer3.setAnswer(answer);
        answer3.setTpaperid(tpaperid);
        answer3.setStudentid(studentid);
        answer3.setQuestionid(questionid);
        answer3.setWeight(weight);
        answer3.setIsread(false);

        QueryWrapper<Answer3> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("studentid", studentid)
                .eq("questionid", questionid)
                .eq("tpaperid", tpaperid);
        Answer3 answer33 = answer3Mapper.selectOne(wrapper1);

        if (answer33 == null) {
            answer3Mapper.insert(answer3);
        } else {
            answer3.setAnswerid(answer33.getAnswerid());
            answer3Mapper.updateById(answer3);
        }

        model.addAttribute("testpaper", testpaper);
        model.addAttribute("studentid", studentid);

        if (questionIndex < completionCache.size()) {
            model.addAttribute("completion", completionCache.get(questionIndex++));
            model.addAttribute("displayComp", "yes");
            return "answerThePaperQuestion";
        } else if (saqCache != null) {
            questionIndex = 0;
            model.addAttribute("saq", saqCache.get(questionIndex++));
            model.addAttribute("displaySAQ", "yes");
            return "answerThePaperQuestion";
        } else {
            model.addAttribute("questionMsg","试题作答完毕，考试结束");
            questionIndex = 0;
            choiceCache = null;
            judgeCache = null;
            completionCache = null;
            saqCache = null;
            return "answerThePaperQuestion";
        }
    }

    @RequestMapping("/submitSAQAnswer")
    public String submitSAQAnswer(@Param("weight") int weight,
                                  @Param("answer") String answer,
                                  @Param("tpaperid") int tpaperid,
                                  @Param("studentid") int studentid,
                                  @Param("questionid") int questionid,
                                  Model model) {
        QueryWrapper<Testpaper> wrapper = new QueryWrapper<>();
        wrapper.eq("tpaperid", tpaperid);
        Testpaper testpaper = testpaperMapper.selectOne(wrapper);


        Answer4 answer4 = new Answer4();
        answer4.setActualscore(0);
        answer4.setAnswer(answer);
        answer4.setTpaperid(tpaperid);
        answer4.setStudentid(studentid);
        answer4.setQuestionid(questionid);
        answer4.setWeight(weight);
        answer4.setIsread(false);

        QueryWrapper<Answer4> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("studentid", studentid)
                .eq("questionid", questionid)
                .eq("tpaperid", tpaperid);
        Answer4 answer44 = answer4Mapper.selectOne(wrapper1);

        if (answer44 == null) {
            answer4Mapper.insert(answer4);
        } else {
            answer4.setAnswerid(answer44.getAnswerid());
            answer4Mapper.updateById(answer4);
        }

        model.addAttribute("testpaper", testpaper);
        model.addAttribute("studentid", studentid);

        if (questionIndex < saqCache.size()) {
            model.addAttribute("saq", saqCache.get(questionIndex++));
            model.addAttribute("displaySAQ", "yes");
            return "answerThePaperQuestion";
        } else {
            model.addAttribute("questionMsg","试题作答完毕，考试结束");
            questionIndex = 0;
            choiceCache = null;
            judgeCache = null;
            completionCache = null;
            saqCache = null;
            return "answerThePaperQuestion";
        }
    }


    @RequestMapping("/studentsViewGrades")
    public String studentsViewGrades(@Param("sid") String sid,
                                     Model model){
        HashMap<String, Object> map = new HashMap<>();
        map.put("studentid",Integer.parseInt(sid));
        List<Score> scores = scoreMapper.selectByMap(map);
        if (scores.isEmpty()){
            model.addAttribute("scoreinfo","暂时没有成绩");
        }else {
            ArrayList<Integer> list = new ArrayList<>();
            for (Score score : scores) {
                list.add(score.getTpaperid());
            }
            List<Testpaper> testpapers = testpaperMapper.selectBatchIds(list);
            model.addAttribute("testpapers",testpapers);
        }
        model.addAttribute("displace","score");
        return "student";
    }

    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping("/viewThisPaperScore")
    public String viewThisPaperScore(@Param("sid") String sid,
                                     @Param("pid") int pid,
                                     Model model){
        Student student = studentMapper.selectById(Integer.parseInt(sid));
        model.addAttribute("studentinfo",student);

        Testpaper testpaper = testpaperMapper.selectById(pid);
        model.addAttribute("paperinfo",testpaper);

        QueryWrapper<Score> wrapper = new QueryWrapper<>();
        wrapper
                .eq("studentid",Integer.parseInt(sid))
                .eq("tpaperid",pid);

        Score score = scoreMapper.selectOne(wrapper);
        model.addAttribute("studentscore",score);

        //计算排名
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("tpaperid",pid);
        List<Score> scores = scoreMapper.selectByMap(map1);
        int rank = 1;
        for (Score score1 : scores) {
            if (score.getScore()<score1.getScore()&&score.getScoreid()!=score1.getScoreid()){
                rank++;
            }
        }
        model.addAttribute("rank",rank);

        HashMap<String, Object> map = new HashMap<>();
        map.put("studentid",Integer.parseInt(sid));
        map.put("tpaperid",pid);

        //选择题试题分析
        List<Answer1> answer1s = answer1Mapper.selectByMap(map);
        if (!answer1s.isEmpty()){
            ArrayList<ChoiceAndAnswer1> choiceList = new ArrayList<>();
            for (Answer1 answer1 : answer1s) {
                Choice choice = choiceMapper.selectById(answer1.getQuestionid());
                choiceList.add(new ChoiceAndAnswer1(choice,answer1));
            }
            model.addAttribute("choices",choiceList);
        }
        //判断题试题分析
        List<Answer2> answer2s = answer2Mapper.selectByMap(map);
        if(!answer2s.isEmpty()){
            ArrayList<JudgeAndAnswer2> judgeList = new ArrayList<>();
            for (Answer2 answer2 : answer2s) {
                Judge judge = judgeMapper.selectById(answer2.getQuestionid());
                judgeList.add(new JudgeAndAnswer2(judge,answer2));
            }
            model.addAttribute("judges",judgeList);
        }
        //填空题试题分析
        List<Answer3> answer3s = answer3Mapper.selectByMap(map);
        if (!answer3s.isEmpty()){
            ArrayList<CompletionAndAnswer3> completionList = new ArrayList<>();
            for (Answer3 answer3 : answer3s) {
                Completion completion = completionMapper.selectById(answer3.getQuestionid());
                completionList.add(new CompletionAndAnswer3(completion,answer3));
            }
            model.addAttribute("completions",completionList);
        }
        //简答题试题分析
        List<Answer4> answer4s = answer4Mapper.selectByMap(map);
        if (!answer4s.isEmpty()){
            ArrayList<SAQAndAnswer4> saqList = new ArrayList<>();
            for (Answer4 answer4 : answer4s) {
                Shortanswerquestion shortanswerquestion = saqMapper.selectById(answer4.getQuestionid());
                saqList.add(new SAQAndAnswer4(shortanswerquestion,answer4));
            }
            model.addAttribute("saqs",saqList);
        }
        model.addAttribute("displace","pqs");
        return "student";
    }


    @RequestMapping("/updateStinfo")
    public String updateStinfo(@Param("id") String id,
                               Model model){
        Student student = studentMapper.selectById(Integer.parseInt(id));
        model.addAttribute("stinfo",student);
        model.addAttribute("displace","updateInfo");
        return "student";
    }

    @RequestMapping("/updateSt")
    public String updateSt(@Param("id") int id,
                           @Param("pwd") String pwd,
                           Model model){
        Student student = studentMapper.selectById(id);
        student.setStudentpassword(pwd);
        studentMapper.updateById(student);
        Student student1 = studentMapper.selectById(id);
        model.addAttribute("stinfo",student1);
        model.addAttribute("displace","updateInfo");
        return "student";
    }


}
