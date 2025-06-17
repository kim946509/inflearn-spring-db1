package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";// 파라미너 바인딩 사용시 단순하게 데이터로 취급되어 SQL 인젝션 공격을 방어할 수 있다.
//        String weakSql = "insert into member(member_id, money) values ("+ memberId + ", "+money+" )"; // SQL 인젝션 취약점
        Connection con = null;
        PreparedStatement pstmt = null;

        con = getConnection();
        try{
            con.prepareStatement(sql);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate(); // 실제 SQL 실행
            return member;
        }catch (SQLException e){
            log.error("db error", e);
            throw e;
        }finally {
            close(con,pstmt, null);
        }

    }

    private void close(Connection con, Statement stmt, ResultSet rs){
        //하나가 close할때 오류가 생겨도 다른 하나에는 영향을 미치지 않음.

        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }
    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
