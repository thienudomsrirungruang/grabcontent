package com.thien.grabcontent.repository;

import com.thien.grabcontent.entity.CartoonInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartoonRepository extends JpaRepository<CartoonInfo, Long> {

    Optional<CartoonInfo> findFirstByCartoonNameOrderByChapterDesc(String cartoonName);

    @Query("select distinct(cartoon.cartoonName) from CartoonInfo cartoon")
    List<String> findDistinctCartoonName();

    List<CartoonInfo> findAllByOrderByViewsDescIdDesc(Pageable pageable);

    List<CartoonInfo> findByCartoonNameOrderByChapterDesc(String cartoonName);

    List<CartoonInfo> findAllByOrderByCreateDateDesc(Pageable pageable);

    @Query("select cartoonInfo from CartoonInfo cartoonInfo join cartoonInfo.pageInfoList pageInfo where cartoonInfo.cartoonName = :cartoon and cartoonInfo.chapter = :chapter order by pageInfo.pageNumber")
    Optional<CartoonInfo> findByCartoonNameAndChapterOrderByPageNumber(@Param("cartoon") String cartoon, @Param("chapter") int chapter);
}
