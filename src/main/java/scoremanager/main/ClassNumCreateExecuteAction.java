//追加部分「クラス管理」

package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassNumCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();

        // ログイン情報取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 入力値取得
        String classNumStr = request.getParameter("class_num");

        // Bean生成
        ClassNum classNum = new ClassNum();
        classNum.setClass_num(classNumStr);
        classNum.setSchool(teacher.getSchool());

        // DAO
        ClassNumDao dao = new ClassNumDao();

        // 登録
        dao.insert(classNum);

        // 一覧へリダイレクト
        response.sendRedirect("ClassNumList.action");
    }
}
