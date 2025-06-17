package hello.jdbc.connection.DBConnectionUtilTest;

import static org.assertj.core.api.Assertions.assertThat;

import hello.jdbc.connection.DBConnectionUtil;
import java.sql.Connection;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DBConnectionUtilTest {

    @Test
    void connection(){
        Connection connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull();
    }
}
