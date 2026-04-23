//追加部分「クラス管理」

package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassNumCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 入力画面へ遷移
        request.getRequestDispatcher("scoremanager/main/class_num_create.jsp")
               .forward(request, response);
    }
}