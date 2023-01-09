package com.elib.repository;

import com.elib.domain.CoreBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoreBookRepository extends JpaRepository<CoreBook, Long>, CoreBookRepositoryCustom {
}
