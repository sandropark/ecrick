package com.elib.repository;

import com.elib.domain.Core;

import java.util.List;

public interface CoreRepositoryCustom {

    boolean isAllBookIdNull();

    List<Core> findNewAll();
}
