package ge.tbc.testautomation.utils;

import ge.tbc.testautomation.database.mappers.LoanDataMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseConfig {

    private static LoanDataMapper mapper;

    public static synchronized LoanDataMapper dbMapper() {
        if (mapper != null) {
            return mapper;
        }

        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:loandb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        initDatabase(dataSource);

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(LoanDataMapper.class);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        mapper = sqlSession.getMapper(LoanDataMapper.class);
        return mapper;
    }

    private static void initDatabase(PooledDataSource dataSource) {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS loan_test_data (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "amount VARCHAR(50) NOT NULL, " +
                    "currency VARCHAR(10) NOT NULL, " +
                    "period VARCHAR(10) NOT NULL);");

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM loan_test_data");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO loan_test_data (amount, currency, period) VALUES ('20000', 'USD', '8')");
                stmt.execute("INSERT INTO loan_test_data (amount, currency, period) VALUES ('50000', 'GEL', '10')");
                stmt.execute("INSERT INTO loan_test_data (amount, currency, period) VALUES ('10000', 'EUR', '5')");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}