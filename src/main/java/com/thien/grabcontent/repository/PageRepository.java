package com.thien.grabcontent.repository;

import com.thien.grabcontent.entity.PageInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends CrudRepository<PageInfo, Long> {

}
