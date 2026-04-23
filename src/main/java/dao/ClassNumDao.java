package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {
	public ClassNum get(String class_num, School school) throws Exception {

	    // クラス番号インスタンスを初期化
	    ClassNum classNum = new ClassNum();

	    // データベースへのコネクションを確立
	    Connection connection = getConnection();

	    // プリペアードステートメント
	    PreparedStatement statement = null;

	    try {
	        // プリペアードステートメントにSQL文をセット
	        statement = connection.prepareStatement(
	            "select * from class_num where class_num = ? and school_cd = ?"
	        );

	        // プリペアードステートメントにクラス番号をバインド
	        statement.setString(1, class_num);

	        // プリペアードステートメントに学校コードをバインド
	        statement.setString(2, school.getCd());

	        // プリペアードステートメントを実行
	        ResultSet rSet = statement.executeQuery();

	        // 学校Daoを初期化
	        SchoolDao sDao = new SchoolDao();

	        if (rSet.next()) {
	            // リザルトセットが存在する場合
	            // クラス番号インスタンスに検索結果をセット
	            classNum.setClass_num(rSet.getString("class_num"));
	            classNum.setSchool(sDao.get(rSet.getString("school_cd")));

	        } else {
	            // リザルトセットが存在しない場合
	            // クラス番号インスタンスにnullをセット
	            classNum = null;
	        }

	    } catch (Exception e) {
	        throw e;

	    } finally {
	        // プリペアードステートメントを閉じる
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }

	        // コネクションを閉じる
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    return classNum;
	}
	
	public List<String> filter(School school) throws Exception {

	    // リストを初期化
	    List<String> list = new ArrayList<>();

	    // データベースへのコネクションを確立
	    Connection connection = getConnection();

	    // プリペアードステートメント
	    PreparedStatement statement = null;

	    try {
	        // プリペアードステートメントにSQL文をセット
	        statement = connection
	                .prepareStatement(
	                        "select class_num from class_num where school_cd=? order by class_num"
	                );

	        // プリペアードステートメントに学校コードをバインド
	        statement.setString(1, school.getCd());

	        // プリペアードステートメントを実行
	        ResultSet rSet = statement.executeQuery();

	        // リザルトセットを全件走査
	        while (rSet.next()) {
	            // リストにクラス番号を追加
	            list.add(rSet.getString("class_num"));
	        }

	    } catch (Exception e) {
	        throw e;

	    } finally {
	        // プリペアードステートメントを閉じる
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }

	        // コネクションを閉じる
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    return list;
	}
	
	// 追加部分「クラス管理」
	
		public boolean insert(ClassNum classNum) throws Exception {

		    // コネクションを確立
		    Connection connection = getConnection();

		    // プリペアードステートメント
		    PreparedStatement statement = null;

		    // 実行件数
		    int count = 0;

		    try {
		        // INSERT文
		        statement = connection.prepareStatement(
		            "insert into class_num (class_num, school_cd) values (?, ?)"
		        );

		        // パラメータをバインド
		        statement.setString(1, classNum.getClass_num());
		        statement.setString(2, classNum.getSchool().getCd());

		        // 実行
		        count = statement.executeUpdate();

		    } catch (Exception e) {
		        throw e;

		    } finally {
		        // ステートメントを閉じる
		        if (statement != null) {
		            try {
		                statement.close();
		            } catch (SQLException sqle) {
		                throw sqle;
		            }
		        }

		        // コネクションを閉じる
		        if (connection != null) {
		            try {
		                connection.close();
		            } catch (SQLException sqle) {
		                throw sqle;
		            }
		        }
		    }

		    return count > 0;
		}
		
//		public boolean update(ClassNum classNum) throws Exception {
//
//		    // コネクションを確立
//		    Connection connection = getConnection();
//
//		    // プリペアードステートメント
//		    PreparedStatement statement = null;
//
//		    // 実行件数
//		    int count = 0;
//
//		    try {
//		        // UPDATE文
//		        statement = connection.prepareStatement(
//		            "update class_num set class_num=? where class_num=? and school_cd=?"
//		        );
//
//		        // パラメータをバインド
//		        statement.setString(1, classNum.getClass_num());  // 新しいクラス番号
//		        statement.setString(2, classNum.getClass_num());  // 旧クラス番号（今回は変更不可）
//		        statement.setString(3, classNum.getSchool().getCd());
//
//		        // 実行
//		        count = statement.executeUpdate();
//
//		    } catch (Exception e) {
//		        throw e;
//
//		    } finally {
//		        if (statement != null) {
//		            try {
//		                statement.close();
//		            } catch (SQLException sqle) {
//		                throw sqle;
//		            }
//		        }
//
//		        if (connection != null) {
//		            try {
//		                connection.close();
//		            } catch (SQLException sqle) {
//		                throw sqle;
//		            }
//		        }
//		    }
//
//		    return count > 0;
//		}
		
		//追加部分「削除部分」
		
		public boolean delete(String class_num, School school) throws Exception {

		    Connection connection = getConnection();

		    PreparedStatement statement = null;

		    int count = 0;

		    try {
		        statement = connection.prepareStatement(
		            "delete from class_num where class_num = ? and school_cd = ?"
		        );

		        statement.setString(1, class_num);
		        statement.setString(2, school.getCd());

		        count = statement.executeUpdate();

		    } catch (Exception e) {
		        throw e;

		    } finally {
		        if (statement != null) {
		            try {
		                statement.close();
		            } catch (SQLException sqle) {
		                throw sqle;
		            }
		        }

		        if (connection != null) {
		            try {
		                connection.close();
		            } catch (SQLException sqle) {
		                throw sqle;
		            }
		        }
		    }

		    return count > 0;
		}
}
	
	