package com.ruban.framework.dao.database;

import java.sql.Connection;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

@Component
public class DataSourceConfiguration4Test implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setName("rubanDao");
        builder.setType(EmbeddedDatabaseType.H2);
        builder.addScript("classpath:H2_TYPE.sql");
        builder.addScript("classpath:INIT_TABLE.sql");
        Connection connection = builder.build().getConnection();
        IDataSet dataSet = new XlsDataSet(new ClassPathResource("data.xls").getFile());

        IDatabaseConnection databaseConnection = new H2Connection(connection, null);
        databaseConnection.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, "true");
        DatabaseOperation databaseOperation = ((DatabaseOperation) DatabaseOperation.CLEAN_INSERT);
        databaseOperation.execute(databaseConnection, dataSet);

    }
}
