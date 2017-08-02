package com.oonurkuru.backend.services.Impl;

import com.oonurkuru.backend.dao.EmployeeDao;
import com.oonurkuru.backend.dao.ProjectDao;
import com.oonurkuru.backend.domains.Project;
import com.oonurkuru.backend.dto.ProjectDTO;
import com.oonurkuru.backend.exceptions.CustomException;
import com.oonurkuru.backend.services.ProjectService;
import com.oonurkuru.backend.utils.Mapper;
import com.oonurkuru.backend.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Onur Kuru on 26.7.2017.
 */
@Component
public class ProjectServiceImpl implements ProjectService {


    private final ProjectDao projectDao;
    private final EmployeeDao employeeDao;

    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao, EmployeeDao employeeDao) {
        this.projectDao = projectDao;
        this.employeeDao = employeeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDTO> findProjectByParameters(MultivaluedMap<String, String> queryMap) throws CustomException{
        PageRequest pageRequest = QueryUtils.createPageRequest(queryMap);
        Example<Project> example = QueryUtils.createExample(queryMap, Project.class);

        List<Project> projectList;

        try {
            projectList = projectDao.findAll(example, pageRequest).getContent();
        } catch (Exception e) {
            throw new CustomException(201, "Query Error", "Sorgu Çalıştırılamadı");
        }

        return (List<ProjectDTO>) Mapper.objectMapper(projectList, true);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDTO findProjectById(Integer projectId) throws CustomException{

        Project project;
        ProjectDTO projectDTO;

        try {
            project = projectDao.findOne(projectId);
            projectDTO = (ProjectDTO) Mapper.objectMapper(project, true);
        } catch (Exception e) {
            throw new CustomException(600, "Unhandled Error", e.getMessage());
        }

        return projectDTO;
    }

    @Override
    @Transactional
    public ProjectDTO save(ProjectDTO projectDTO) throws CustomException{
        Project project = (Project) Mapper.objectMapper(projectDTO, false);
        project.setEmployeeList(new ArrayList<>());

        List<Integer> idList = projectDTO.getEmployeeList().stream()
                .map(employee -> employee.getId()).collect(Collectors.toList());
        project.setEmployeeList(employeeDao.findAll(idList));

        try {
            projectDao.save(project);
        } catch (Exception e) {
            throw new CustomException(100, "Save Entity Error", e.getMessage());
        }

        return (ProjectDTO) Mapper.objectMapper(project, true);
    }

    @Override
    @Transactional
    public void delete(Integer projectId) {
        try {
            projectDao.delete(projectId);
        } catch (Exception e) {
            throw new CustomException(102, "Delete Entity Error", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDTO> findProjectsByEmployee(Integer employeeId) throws CustomException{

        List<Project> projectList;
        try {
            projectList = projectDao.findByEmployeeList_Id(employeeId);
        } catch (Exception e) {
            throw new CustomException(600, "Unhandled Error", e.getMessage());
        }

        return (List<ProjectDTO>) Mapper.objectMapper(projectList, false);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDTO findProjectByEmployeeIdAndProjectId(Integer employeeId, Integer projectId) throws CustomException{

        Project project;
        ProjectDTO projectDTO;

        try {
            project = projectDao.findByIdAndEmployeeList_Id(projectId, employeeId);
            projectDTO = (ProjectDTO) Mapper.objectMapper(project, true);
        } catch (Exception e) {
            throw new CustomException(600, "Unhandled Error", e.getMessage());
        }

        return projectDTO;
    }
}
