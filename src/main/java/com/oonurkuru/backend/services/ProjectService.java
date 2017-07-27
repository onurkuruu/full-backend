package com.oonurkuru.backend.services;

import com.oonurkuru.backend.dto.ProjectDTO;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

/**
 * Created by Onur Kuru on 26.7.2017.
 */
public interface ProjectService {

    default public List<ProjectDTO> findProjectByParameters(MultivaluedMap<String, String> queryMap) {
        //Log
        return null;
    }

    default public ProjectDTO findProjectById(Integer projectId) {
        //Log
        return null;
    }

    default public ProjectDTO save(ProjectDTO projectDTO) {
        //Log
        return null;
    }

    default public void delete(Integer projectId) {
        //Log
    }
}
