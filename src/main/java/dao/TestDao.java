//追加コード「TestDao.java」

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Test;

public class TestDao extends Dao {

    /**
     * 試験一覧取得
     */
    public List<Test> getList(String schoolCd) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection con = getConnection();

        String sql = "SELECT * FROM TEST WHERE SCHOOL_CD = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, schoolCd);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Test test = new Test();

            test.setStudentNo(rs.getString("STUDENT_NO"));
            test.setSubjectCd(rs.getString("SUBJECT_CD"));
            test.setSchoolCd(rs.getString("SCHOOL_CD"));
            test.setNo(rs.getInt("NO"));
            test.setPoint(rs.getInt("POINT"));
            test.setClassNum(rs.getString("CLASS_NUM"));

            list.add(test);
        }

        st.close();
        con.close();

        return list;
    }

    /**
     * 1件取得（主キー検索）
     */
    public Test get(String studentNo, String subjectCd, String schoolCd, int no) throws Exception {

        Connection con = getConnection();

        String sql = "SELECT * FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, studentNo);
        st.setString(2, subjectCd);
        st.setString(3, schoolCd);
        st.setInt(4, no);

        ResultSet rs = st.executeQuery();

        Test test = null;

        if (rs.next()) {
            test = new Test();

            test.setStudentNo(rs.getString("STUDENT_NO"));
            test.setSubjectCd(rs.getString("SUBJECT_CD"));
            test.setSchoolCd(rs.getString("SCHOOL_CD"));
            test.setNo(rs.getInt("NO"));
            test.setPoint(rs.getInt("POINT"));
            test.setClassNum(rs.getString("CLASS_NUM"));
        }

        st.close();
        con.close();

        return test;
    }

    /**
     * 登録（INSERT）
     */
    public int insert(Test test) throws Exception {

        Connection con = getConnection();

        String sql = "INSERT INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, test.getStudentNo());
        st.setString(2, test.getSubjectCd());
        st.setString(3, test.getSchoolCd());
        st.setInt(4, test.getNo());
        st.setInt(5, test.getPoint());
        st.setString(6, test.getClassNum());

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }

    /**
     * 更新（UPDATE）
     */
    public int update(Test test) throws Exception {

        Connection con = getConnection();

        String sql = "UPDATE TEST SET POINT = ?, CLASS_NUM = ? WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?";

        PreparedStatement st = con.prepareStatement(sql);

        st.setInt(1, test.getPoint());
        st.setString(2, test.getClassNum());
        st.setString(3, test.getStudentNo());
        st.setString(4, test.getSubjectCd());
        st.setString(5, test.getSchoolCd());
        st.setInt(6, test.getNo());

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }

    /**
     * 削除（DELETE）
     */
    public int delete(String studentNo, String subjectCd, String schoolCd, int no) throws Exception {

        Connection con = getConnection();

        String sql = "DELETE FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?";

        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, studentNo);
        st.setString(2, subjectCd);
        st.setString(3, schoolCd);
        st.setInt(4, no);

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }
}