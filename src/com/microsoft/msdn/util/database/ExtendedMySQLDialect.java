package com.microsoft.msdn.util.database;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * �Զ��巽��
 */
public class ExtendedMySQLDialect extends MySQLDialect {
    public ExtendedMySQLDialect() {
        super();
        registerFunction("date_add" ,  new  SQLFunctionTemplate(StandardBasicTypes.DATE,  "date_add(?1, INTERVAL ?2 ?3)" ));
        registerFunction("to_utf8" ,  new  SQLFunctionTemplate(StandardBasicTypes.STRING,  "CONVERT(?1 using UTF8)" ));
        registerFunction("regexp" ,  new  SQLFunctionTemplate(StandardBasicTypes.STRING,  "(?1 regexp ?2)" ));
        registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
