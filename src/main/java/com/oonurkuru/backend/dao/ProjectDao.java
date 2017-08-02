package com.oonurkuru.backend.dao;

import com.oonurkuru.backend.domains.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

@Repository
public interface ProjectDao extends CrudRepository<Project, Integer>, JpaRepository<Project, Integer> {

    List<Project> findByEmployeeList_Id(Integer employeeId);

    Project findByIdAndEmployeeList_Id(Integer projectId, Integer employeeId);
}
