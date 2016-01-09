package com.ruban.framework.dao.database;

import org.junit.BeforeClass;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

@Component
public class DataSourceConfiguration4Test implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        new EmbeddedDatabaseBuilder().setName("rubanDao").setType(EmbeddedDatabaseType.H2).addScript("classpath:H2_TYPE.sql")
                .addScript("classpath:INIT_TABLE.sql").build();

    }
}
