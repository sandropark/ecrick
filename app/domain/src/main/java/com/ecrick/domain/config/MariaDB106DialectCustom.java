package com.ecrick.domain.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.MariaDBDialect;
import org.hibernate.query.sqm.function.FunctionKind;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.query.sqm.produce.function.PatternFunctionDescriptorBuilder;
import org.hibernate.type.spi.TypeConfiguration;

public class MariaDB106DialectCustom extends MariaDBDialect {

    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {
        super.initializeFunctionRegistry(functionContributions);

        SqmFunctionRegistry registry = functionContributions.getFunctionRegistry();
        TypeConfiguration types = functionContributions.getTypeConfiguration();

        new PatternFunctionDescriptorBuilder(registry, "match", FunctionKind.NORMAL, "match(?1) against(?2 in boolean mode) and 1")
                .setExactArgumentCount(2)
                .setInvariantType(types.getBasicTypeForJavaType(Integer.class))
                .register();
    }
}
