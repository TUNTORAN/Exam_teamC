package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// パラメータ取得
		String no = req.getParameter("no");

		// DAO
		StudentDao sDao = new StudentDao();
		ClassNumDao cDao = new ClassNumDao();

		// 学生取得
		Student student = sDao.get(no);

		// クラス一覧
		List<String> classList = cDao.filter(teacher.getSchool());

		// JSPへセット
		req.setAttribute("ent_year", student.getEntYear());
		req.setAttribute("no", student.getNo());
		req.setAttribute("name", student.getName());
		req.setAttribute("class_num", student.getClassNum());
		req.setAttribute("is_attend", student.isAttend());
		req.setAttribute("class_num_set", classList);

		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}
}