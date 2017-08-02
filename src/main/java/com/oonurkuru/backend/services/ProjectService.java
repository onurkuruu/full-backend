package com.oonurkuru.backend.services;

import com.oonurkuru.backend.dto.ProjectDTO;
import com.oonurkuru.backend.exceptions.CustomException;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

/**
 * Created by Onur Kuru on 26.7.2017.
 */

@Service
public interface ProjectService {

    List<ProjectDTO> findProjectByParameters(MultivaluedMap<String, String> queryMap) throws CustomException;

    ProjectDTO findProjectById(Integer projectId) throws CustomException;

    ProjectDTO save(ProjectDTO projectDTO) throws CustomException;

    void delete(Integer projectId) throws CustomException;

    List<ProjectDTO> findProjectsByEmployee(Integer employeeId) throws CustomException;

    ProjectDTO findProjectByEmployeeIdAndProjectId(Integer employeeId, Integer projectId) throws CustomException;
}
