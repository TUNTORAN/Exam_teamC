package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher) session.getAttribute("user");

		int ent_year = 0; // 入学年度
		String student_no = ""; // 学生番号
		String student_name = ""; // 氏名
		String class_num = ""; // クラス番号
		boolean is_attend = false; // 在学フラグ

		Student student = new Student();
		StudentDao studentDao = new StudentDao();
		Map<String, String> errors = new HashMap<>();

		// リクエストパラメーターの取得 2
		ent_year = Integer.parseInt(req.getParameter("ent_year"));
		student_no = req.getParameter("no");
		student_name = req.getParameter("name");
		class_num = req.getParameter("class_num");

		// checkbox対応（あるときだけtrue）
		if (req.getParameter("is_attend") != null) {
			is_attend = true;
		}

		// DBからデータ取得 3
		// なし

		// ビジネスロジック 4
		if (student_name == null || student_name.isEmpty()) {
			errors.put("1", "氏名を入力してください");
			req.setAttribute("errors", errors);
		} else {
			// studentに情報をセット
			student.setNo(student_no);
			student.setName(student_name);
			student.setEntYear(ent_year);
			student.setClassNum(class_num);
			student.setAttend(is_attend);
			student.setSchool(teacher.getSchool());

			// save（update）
			studentDao.save(student);
		}

		// レスポンス値をセット 6
		req.setAttribute("ent_year", ent_year);
		req.setAttribute("no", student_no);
		req.setAttribute("name", student_name);
		req.setAttribute("class_num", class_num);
		req.setAttribute("is_attend", is_attend);

		// JSPへフォワード 7
		if (errors.isEmpty()) {
			req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
		} else {
			req.getRequestDispatcher("StudentUpdate.action?no=" + student_no).forward(req, res);
		}
	}
}