//package com.ecrick.config;
//
//import org.hibernate.dialect.MariaDB106Dialect;
//import org.hibernate.dialect.function.SQLFunctionTemplate;
//import org.hibernate.type.StandardBasicTypes;
//
//public class MariaDB106DialectCustom extends MariaDB106Dialect {
//
//    public MariaDB106DialectCustom() {
//        super();
//
//        registerFunction(
//                "match",
//                new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "match(?1) against(?2 in boolean mode) and 1")
//        );
//    }
//}
