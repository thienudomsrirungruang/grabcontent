package com.thien.grabcontent.repository;

import com.thien.grabcontent.entity.CartoonInfo;
import liquibase.exception.DatabaseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartoonRepository extends JpaRepository<CartoonInfo, Long> {

    Optional<CartoonInfo> findFirstByCartoonNameOrderByChapterDesc(String cartoonName);

    @Query("select distinct(cartoon.cartoonName) from CartoonInfo cartoon")
    List<String> findDistinctCartoonName();

    List<CartoonInfo> findAllByOrderByViewsDescIdDesc();

    List<CartoonInfo> findByCartoonNameOrderByChapterDesc(String cartoonName);

    List<CartoonInfo> findAllByOrderByCreateDateDesc(Pageable pageable);
}
