package demo.helloworld.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.tools.Server;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@PropertySource({ "classpath:application.properties" })
@MapperScan(basePackages = "demo.helloworld.dao",
        sqlSessionFactoryRef = "h2Factory",
        sqlSessionTemplateRef = "h2Session")
public class H2ServerConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = "h2DataSource")
//    @Primary
    @ConfigurationProperties("spring.datasource.hikari.h2")
    public DataSource h2dataSource() throws SQLException {
        Server.createTcpServer(
                "-tcp",
                "-tcpAllowOthers"
        ).start();
        return new HikariDataSource();
    }

    @Bean(name = "h2Factory")
    public SqlSessionFactory h2Factory(@Autowired @Qualifier("h2DataSource") DataSource h2DataSource) throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(h2DataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/**.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "h2Session")
    public SqlSessionTemplate h2SqlSession(@Autowired @Qualifier("h2Factory") SqlSessionFactory h2Factory) throws Exception{
        return new SqlSessionTemplate(h2Factory);
    }
}