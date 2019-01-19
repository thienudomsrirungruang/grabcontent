package com.thien.grabcontent.repository;

import com.thien.grabcontent.entity.CartoonInfo;
import org.springframework.data.repository.CrudRepository;

public interface CartoonRepository extends CrudRepository<CartoonInfo, Long> {

}
