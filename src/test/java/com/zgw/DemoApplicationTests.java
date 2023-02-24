package com.zgw;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zgw.mapper.*;
import com.zgw.pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private AdminMapper am;

	@Autowired
	private TeacherMapper tm;

	@Autowired
	private StudentMapper sm;

	@Test
	void contextLoads() {
		List<Administrator> as = am.selectList(null);
		as.forEach(System.out::println);

	}

	@Test
	public void testInsert(){
		Teacher t = new Teacher();
		t.setTeacherid(2);
		t.setTeachername("测试2");
		t.setTeachersex("男");
		t.setTeacherphone("13358977564");
		t.setTeachermajor("文学系");
		t.setTeacherdepart("文学院");
		t.setTeacherpassword("123456");

		int result = tm.insert(t);
		System.out.println(result);
		System.out.println(t);
	}


	@Test
	public void testUpdate(){
		Teacher t = new Teacher();
		t.setTeacherid(1);
		t.setTeachername("更新测试");
		t.setTeachersex("男");
		t.setTeacherphone("13358977564");
		t.setTeachermajor("文学系");
		t.setTeacherdepart("文学院");
		t.setTeacherpassword("123456");
		int i = tm.updateById(t);
		System.out.println(i);
	}

	@Test
	public void testSelectById(){
		Teacher teacher = tm.selectById(2);
		System.out.println(teacher);
	}

	@Test
	public void testSelectByBatchId(){
		List<Teacher> teachers = tm.selectBatchIds(Arrays.asList(1, 2));
		teachers.forEach(System.out::println);
	}

	@Test
	public void 条件查询(){
		HashMap<String, Object> map = new HashMap<>();

		map.put("adminid",1);
		map.put("adminpassword","123456");
		List<Administrator> ads = am.selectByMap(map);
		ads.forEach(System.out::println);
	}

	@Test
	public void 删除(){

		sm.deleteById(2);

	}

	@Test
	public void 批量删除(){
		sm.deleteBatchIds(Arrays.asList(5,6));
	}

	@Test
	public void 条件删除(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("adminid",2);
		sm.deleteByMap(map);
	}


	//============Wrapper条件构造器==========================

	@Test
	public void Wrapper1(){
		QueryWrapper<Student> wrapper = new QueryWrapper<>();
		wrapper
				.isNotNull("adminid")
				.isNotNull("studentid")
				.ge("studentid",2);//大于等于
		sm.selectList(wrapper).forEach(System.out::println);

	}

	@Test
	void wrapper2(){
		QueryWrapper<Student> wrapper = new QueryWrapper<>();
		wrapper.eq("studentname","学生姓名3");
		System.out.println(sm.selectOne(wrapper));//查询一个数据，出现多个结果使用list或者Map
	}

	@Test
	void wrapper3(){
		QueryWrapper<Student> wrapper = new QueryWrapper<>();
		wrapper.between("studentid",3,5);//区间
		Integer count = sm.selectCount(wrapper);//查询结果数
		System.out.println(count);
	}

	@Test
	void 模糊查询(){

		QueryWrapper<Student> wrapper = new QueryWrapper<>();
		wrapper
				.notLike("studentname","1")
				.likeRight("studentid",6);//右匹配6%;左匹配%6;匹配%6%
		List<Map<String, Object>> maps = sm.selectMaps(wrapper);
		maps.forEach(System.out::println);
	}

	@Test
	void 嵌套查询(){
		QueryWrapper<Student> wrapper = new QueryWrapper<>();
		wrapper.inSql("adminid","select adminid from student where adminid=1");
		List<Object> objects = sm.selectObjs(wrapper);
		objects.forEach(System.out::println);
	}

	@Test
	void 排序操作(){
		QueryWrapper<Student> wrapper = new QueryWrapper<>();

		wrapper.orderByDesc("studentid");//降序，asc升序

		List<Student> students = sm.selectList(wrapper);
		students.forEach(System.out::println);

	}

	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
	private TeachMapper teachMapper;

	@Test
	void 查询课程操作测试(){
		int subjectid = 1001;
		int adid = 1;

		QueryWrapper<Subject> wrapper = new QueryWrapper<>();
		wrapper
				.eq("subjectid",subjectid)
				.eq("adminid",adid);
		Subject subject = subjectMapper.selectOne(wrapper);

		if(subject==null){
			System.out.println("查无此课");
		}else {
			HashMap<String, Object> map = new HashMap<>();
			map.put("subjectid",subjectid);
			map.put("adminid",adid);
			List<Teach> teaches = teachMapper.selectByMap(map);
			System.out.println("----------------------------");
			teaches.forEach(System.out::println);
			System.out.println("----------------------------");
			ArrayList<Integer> list = new ArrayList<>();
			for (Teach teach : teaches) {
				list.add(teach.getTeacherid());
			}

			List<Teacher> teachers = tm.selectBatchIds(list);
			System.out.println("----------------------------");
			teachers.forEach(System.out::println);
			System.out.println("----------------------------");
		}
	}

	@Test
	void 测试(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("subjectid",1001);
		//map.put("adminid",1);
		List<Teach> teaches = teachMapper.selectByMap(map);
		System.out.println("----------------------------");
		teaches.forEach(System.out::println);
		System.out.println("----------------------------");
	}

	@Autowired
	private CompletionMapper completionMapper;

	@Test
	void 选择题测试(){

		Completion completion = new Completion(1, "11111", "22222", "33333", 1001);
		completionMapper.insert(completion);
		List<Completion> list = completionMapper.selectList(null);
		list.forEach(System.out::println);

	}

	@Autowired
	private ShortanswerquestionMapper saqmapper;

	@Test
	void 简答题测试(){
		Shortanswerquestion shortAnswerQuestion = new Shortanswerquestion(1, "1111111", "2222222", "3333333", 1001);
		saqmapper.insert(shortAnswerQuestion);
		List<Shortanswerquestion> list = saqmapper.selectList(null);
		list.forEach(System.out::println);
	}

	@Autowired
	private TestMapper testMapper;

	@Test
	void Test表(){

		com.zgw.pojo.Test test = new com.zgw.pojo.Test(0,0,0,false,0);
		testMapper.insert(test);
	}

	@Autowired
	private Answer1Mapper answer1Mapper;

	@Test
	void 答案表测试(){
		Answer1 answer1 = new Answer1(0,0,0,0,"11",0,0,false);
		answer1Mapper.insert(answer1);

	}

	@Autowired
	private ScoreMapper scoreMapper;

	@Test
	void 成绩表测试(){
		/*Score score = new Score(0,0,0,0,0);
		scoreMapper.insert(score);*/
		QueryWrapper<Score> wrapper2 = new QueryWrapper<>();
		wrapper2.eq("studentid", 0)
				.eq("tpaperid", 0)
				.eq("subjectid", 0);
		Score score1 = scoreMapper.selectOne(wrapper2);
		System.out.println(score1);
	}

	@Test
	void 课程号查询测试(){
		QueryWrapper<Subject> wrapper2 = new QueryWrapper<>();
		wrapper2.eq("subjectid",1001);
		Subject subject = subjectMapper.selectOne(wrapper2);
		System.out.println(subject);
	}








}
