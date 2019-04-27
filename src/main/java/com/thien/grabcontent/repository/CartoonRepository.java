package com.thien.grabcontent.repository;

import com.thien.grabcontent.entity.CartoonInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartoonRepository extends CrudRepository<CartoonInfo, Long> {

    Optional<CartoonInfo> findFirstByCartoonNameOrderByChapterDesc(String cartoonName);

    @Query("select distinct(cartoon.cartoonName) from CartoonInfo cartoon")
    List<String> findDistinctCartoonName();

    List<CartoonInfo> findByCartoonNameOrderByChapterDesc(String cartoonName);
}
