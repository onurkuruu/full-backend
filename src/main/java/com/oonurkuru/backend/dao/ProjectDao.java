package com.oonurkuru.backend.dao;

import com.oonurkuru.backend.domains.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

@Repository
public interface ProjectDao extends CrudRepository<Project, Integer> {


}
