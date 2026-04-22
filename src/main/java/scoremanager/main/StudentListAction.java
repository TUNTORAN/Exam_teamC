package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentListAction extends Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		//TODO 自動生成されたメソッド・スタブ
		
		HttpSession session = request.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		String entYearStr=request.getParameter("f1");;   // 入力された入学年度
		String classNum =request.getParameter("f2");;    // 入力されたクラス番号
		String isAttendStr=request.getParameter("f3");;  // 入力された在学クラブ
		int entYear = 0;        //入学年度
		boolean isAttend = false;//在学クラブ
		List<Student> students = null;//学生リスト
		LocalDate todaysDate = LocalDate.now();//LocaDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		StudentDao sDao = new StudentDao();//学生dao 
		ClassNumDao cNumDao = new ClassNumDao();//クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();
		
		if (entYearStr !=null) {
			//数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year -10; i < year + 1; i++) {
			entYearSet.add(i);
		}
		
		List<String> list = cNumDao.filter(teacher.getSchool());
		
		if (entYear !=0 && !classNum.equals("0")) {
			//入学年度とクラス番号を指定
			students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
		} else if (entYear !=0 && classNum.equals("0")) {
			//入学年度のみ指定
			students = sDao.filter(teacher.getSchool(), entYear, isAttend);
		} else if (entYear ==0 && classNum == null || entYear == 0 && classNum.equals("0")) {
			//指定なしの場合
			//全学生情報を取得
			students = sDao.filter(teacher.getSchool(), isAttend);
		} else {
			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
			request.setAttribute("errors", errors);
			//全学生情報を取得
			students = sDao.filter(teacher.getSchool(), isAttend);
		}
		
		request.setAttribute("f1", entYear);
		request.setAttribute("f2", classNum);
		if (isAttendStr !=null) {
			isAttend =true;
			request.setAttribute("f3", isAttendStr);
		}
		
		
		request.setAttribute("students", students);
		request.setAttribute("class_num_set", list);
		request.setAttribute("ent_year_set", entYearSet);
		
		request.getRequestDispatcher("student_list.jsp").forward(request, response);
		
	}
}