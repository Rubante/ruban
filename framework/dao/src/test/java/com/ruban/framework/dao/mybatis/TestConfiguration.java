package com.ruban.framework.dao.mybatis;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ruban.framework.dao.domain.TestUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestConfiguration {

    @Test
    public void testMybatis() {
        // myBatis配置文件
        String resource = "mybatis.xml";
        InputStream is = TestConfiguration.class.getClassLoader().getResourceAsStream(resource);

        // 构建sqlSession工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);

        // 获取sqlSession
        SqlSession sqlSession = sessionFactory.openSession();

        // sql标识ID
        String statement = "com.ruban.framework.dao.mapping.userMapper.findById";

        // 执行
        TestUser user = sqlSession.selectOne(statement, 1);
        
        System.out.println(user);
    }

}
