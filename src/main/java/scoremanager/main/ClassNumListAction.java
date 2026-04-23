//追加部分「クラス管理」

package scoremanager.main;

import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassNumListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();

        // ログインユーザー取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // DAO
        ClassNumDao dao = new ClassNumDao();

        // クラス一覧取得
        List<String> list = dao.filter(teacher.getSchool());

        // リクエストへセット
        request.setAttribute("class_num_set", list);

        // JSPへフォワード
        request.getRequestDispatcher("scoremanager/main/class_num_list.jsp")
        .forward(request, response);
    }
}