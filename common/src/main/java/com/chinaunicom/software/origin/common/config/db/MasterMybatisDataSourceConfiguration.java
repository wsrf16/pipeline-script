package com.chinaunicom.software.origin.common.config.db;

import com.aio.portable.swiss.suite.storage.db.mybatis.multidatasource.MybatisBaseDataSourceConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration
@MapperScan(basePackages = {MasterMybatisDataSourceConfiguration.BASE_PACKAGES}, sqlSessionTemplateRef = MasterMybatisDataSourceConfiguration.SQL_SESSION_TEMPLATE_BEAN)
public class MasterMybatisDataSourceConfiguration extends MybatisBaseDataSourceConfiguration {
    private final static String SPECIAL_NAME = "master";

    public final static String BASE_PACKAGES = "com.chinaunicom.software.origin.db." + SPECIAL_NAME + ".mapper";

    private final static String DATA_SOURCE_PREFIX = "spring.datasource." + SPECIAL_NAME;
    public final static String SQL_SESSION_TEMPLATE_BEAN = SPECIAL_NAME + "SQLSessionTemplate";
    private final static String DATA_SOURCE_BEAN = SPECIAL_NAME + "DataSource";
    private final static String SQL_SESSION_FACTORY_BEAN = SPECIAL_NAME + "SQLSessionFactory";
    private final static String PLATFORM_TRANSACTION_MANAGER_BEAN = SPECIAL_NAME + "PlatformTransactionManager";

    private final static String MYBATIS_PREFIX = DATA_SOURCE_PREFIX + ".mybatis";
    private final static String MYBATIS_PROPERTIES_BEAN = SPECIAL_NAME + "MybatisProperties";

    @Bean(MYBATIS_PROPERTIES_BEAN)
    @Primary
    @ConfigurationProperties(prefix = MYBATIS_PREFIX)
    public MybatisProperties mybatisProperties() {
        return super.mybatisProperties();
    }

    @Bean(DATA_SOURCE_BEAN)
    @Primary
    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
//    @ConditionalOnProperty(prefix = DATA_SOURCE_PREFIX, value = "url")
//    @ConditionalOnClass(DruidDataSourceBuilder.class)
    public DataSource dataSource() {
//        return DruidDataSourceBuilder.create().build();
        return super.dataSource();
    }

    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
    @Bean(SQL_SESSION_FACTORY_BEAN)
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATA_SOURCE_BEAN) DataSource dataSource, @Qualifier(MYBATIS_PROPERTIES_BEAN) MybatisProperties properties) throws Exception {
        return super.sqlSessionFactory(dataSource, properties);
    }

    @ConditionalOnBean(name = SQL_SESSION_FACTORY_BEAN)
    @Bean(SQL_SESSION_TEMPLATE_BEAN)
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier(SQL_SESSION_FACTORY_BEAN) SqlSessionFactory sqlSessionFactory, @Qualifier(MYBATIS_PROPERTIES_BEAN) MybatisProperties properties) throws Exception {
        return super.sqlSessionTemplate(sqlSessionFactory, properties);
    }

    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
    @Bean(PLATFORM_TRANSACTION_MANAGER_BEAN)
    public PlatformTransactionManager platformTransactionManager(@Qualifier(DATA_SOURCE_BEAN) DataSource dataSource) {
        return super.platformTransactionManager(dataSource);
    }
}
