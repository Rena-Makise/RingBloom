/* DatabaseConfiguration.java
 * Description : 데이터베이스 설정
 * ver 0.1 : 초기 구성 - 이 창 재
 * ver 0.2 : 마이바티스 연동
 */
package ringbloom.configuration;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")  // 설정파일 위치 지정
public class DatabaseConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * application.properties에 설정했던 데이터베이스 관련 정보를 사용하도록 지정
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	/**
	 * 앞에서 만든 히카리CP의 설정 파일을 이용해서 데이터베이스와 연결하는 데이터 소스를 생성
	 * @return
	 * @throws Exception
	 */
	@Bean
	public DataSource dataSource() throws Exception {
		return new HikariDataSource(hikariConfig());
	}
	
	/**
	 * sql 세션 설정
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		// 스프링-마이바티스에서는 SqlSessionFactory를 생성하기 위해서 SqlSessionFactoryBean을 사용한다.
		// 만약 마이바티스 단독으로 사용할 경우에는 SqlSessionFactoryBuilder를 사용하면 된다.
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();  
		// 앞에서 만든 데이터 소스를 설정한다.
		sqlSessionFactoryBean.setDataSource(dataSource);
		// 마이바티스 매퍼(Mapper)파일의 위치를 설정한다.
		// 매퍼는 애플리케이션에서 사용할 SQL을 담고 있는 XML파일을 의미한다.
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		return sqlSessionFactoryBean.getObject();
	}
	
	/**
	 * sql 세션 템플릿 설정
	 * @param sqlSessionFactory
	 * @return
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	/**
	 * 마이바티스 설정 반환
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig() {
		return new org.apache.ibatis.session.Configuration();
	}
}
