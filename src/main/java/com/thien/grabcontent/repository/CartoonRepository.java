package com.thien.grabcontent.repository;

import com.thien.grabcontent.entity.CartoonInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartoonRepository extends CrudRepository<CartoonInfo, Long> {

}
