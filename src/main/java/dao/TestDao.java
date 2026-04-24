package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Test;

public class TestDao extends Dao {

    /**
     * 保存（INSERT or UPDATE）
     */
    public boolean save(Test test) throws Exception {

        // 既存データ確認
        Test old = get(
            test.getStudentNo(),
            test.getSubjectCd(),
            test.getSchoolCd(),
            test.getNo()
        );

        int count = 0;

        if (old == null) {
            // 新規登録
            count = insert(test);
        } else {
            // 更新
            count = update(test);
        }

        return count > 0;
    }

    /**
     * 条件検索
     */
    public List<Test> filter(School school, int entYear, String classNum, String subjectId) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {

            String sql =
                "select t.* from test t " +
                "inner join student s on t.student_no = s.no " +
                "where t.school_cd = ? ";

            // 条件追加（設計書準拠：空条件対応）
            if (entYear != 0) {
                sql += "and s.ent_year = ? ";
            }
            if (classNum != null && !classNum.isEmpty()) {
                sql += "and s.class_num = ? ";
            }
            if (subjectId != null && !subjectId.isEmpty()) {
                sql += "and t.subject_cd = ? ";
            }

            statement = connection.prepareStatement(sql);

            int idx = 1;
            statement.setString(idx++, school.getCd());

            if (entYear != 0) {
                statement.setInt(idx++, entYear);
            }
            if (classNum != null && !classNum.isEmpty()) {
                statement.setString(idx++, classNum);
            }
            if (subjectId != null && !subjectId.isEmpty()) {
                statement.setString(idx++, subjectId);
            }

            rSet = statement.executeQuery();

            while (rSet.next()) {
                Test test = new Test();

                test.setStudentNo(rSet.getString("student_no"));
                test.setSubjectCd(rSet.getString("subject_cd"));
                test.setSchoolCd(rSet.getString("school_cd"));
                test.setNo(rSet.getInt("no"));
                test.setPoint(rSet.getInt("point"));
                test.setClassNum(rSet.getString("class_num"));

                list.add(test);
            }

        } catch (Exception e) {
            throw e;

        } finally {

            if (rSet != null) {
                try {
                    rSet.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return list;
    }

    /**
     * 以下はあなたの既存コード（変更なし）
     */

    public List<Test> getList(String schoolCd) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            statement = connection.prepareStatement(
                "select * from test where school_cd = ?"
            );

            statement.setString(1, schoolCd);

            rSet = statement.executeQuery();

            while (rSet.next()) {
                Test test = new Test();

                test.setStudentNo(rSet.getString("student_no"));
                test.setSubjectCd(rSet.getString("subject_cd"));
                test.setSchoolCd(rSet.getString("school_cd"));
                test.setNo(rSet.getInt("no"));
                test.setPoint(rSet.getInt("point"));
                test.setClassNum(rSet.getString("class_num"));

                list.add(test);
            }

        } finally {
            if (rSet != null) rSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    public Test get(String studentNo, String subjectCd, String schoolCd, int no) throws Exception {

        Test test = null;

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            statement = connection.prepareStatement(
                "select * from test where student_no = ? and subject_cd = ? and school_cd = ? and no = ?"
            );

            statement.setString(1, studentNo);
            statement.setString(2, subjectCd);
            statement.setString(3, schoolCd);
            statement.setInt(4, no);

            rSet = statement.executeQuery();

            if (rSet.next()) {
                test = new Test();

                test.setStudentNo(rSet.getString("student_no"));
                test.setSubjectCd(rSet.getString("subject_cd"));
                test.setSchoolCd(rSet.getString("school_cd"));
                test.setNo(rSet.getInt("no"));
                test.setPoint(rSet.getInt("point"));
                test.setClassNum(rSet.getString("class_num"));
            }

        } finally {
            if (rSet != null) rSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return test;
    }

    public int insert(Test test) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "insert into test values (?, ?, ?, ?, ?, ?)"
            );

            statement.setString(1, test.getStudentNo());
            statement.setString(2, test.getSubjectCd());
            statement.setString(3, test.getSchoolCd());
            statement.setInt(4, test.getNo());
            statement.setInt(5, test.getPoint());
            statement.setString(6, test.getClassNum());

            return statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public int update(Test test) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "update test set point=?, class_num=? where student_no=? and subject_cd=? and school_cd=? and no=?"
            );

            statement.setInt(1, test.getPoint());
            statement.setString(2, test.getClassNum());
            statement.setString(3, test.getStudentNo());
            statement.setString(4, test.getSubjectCd());
            statement.setString(5, test.getSchoolCd());
            statement.setInt(6, test.getNo());

            return statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public int delete(String studentNo, String subjectCd, String schoolCd, int no) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "delete from test where student_no=? and subject_cd=? and school_cd=? and no=?"
            );

            statement.setString(1, studentNo);
            statement.setString(2, subjectCd);
            statement.setString(3, schoolCd);
            statement.setInt(4, no);

            return statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
}