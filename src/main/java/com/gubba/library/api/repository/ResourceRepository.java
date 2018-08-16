package com.gubba.library.api.repository;

import com.gubba.library.api.model.Resource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Long> {
    Resource findByTitle(String title);
    List<Resource> findByTitleContains(String searchTitle);
}
