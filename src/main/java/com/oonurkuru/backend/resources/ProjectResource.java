package com.oonurkuru.backend.resources;

import com.oonurkuru.backend.dto.ProjectDTO;
import com.oonurkuru.backend.exceptions.CustomException;
import com.oonurkuru.backend.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Onur Kuru on 26.7.2017.
 */
@Component
@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResource {

    final private ProjectService projectService;

    @Autowired
    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GET
    public Response findProjectByParameters(@Context UriInfo uriInfo) {

        List<ProjectDTO> projectDTOList;

        try {
            projectDTOList = projectService.findProjectByParameters(uriInfo.getQueryParameters());
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.ok().entity(projectDTOList).build();
    }

    @GET
    @Path("/{id: \\d+}")
    public Response findProjectById(@PathParam("id") Integer projectId) {

        ProjectDTO projectDTO;

        try {
            projectDTO = projectService.findProjectById(projectId);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.ok().entity(projectDTO).build();
    }

    @POST
    public Response createProject(@Valid ProjectDTO projectDTO) {

        ProjectDTO created;
        try {
            projectDTO.setId(null);
            created = projectService.save(projectDTO);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.status(201).entity(created).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    public Response updateProject(@Valid ProjectDTO projectDTO) {

        ProjectDTO updated;
        try {
            updated = projectService.save(projectDTO);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.status(201).entity(updated).build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    public Response deleteProject(@PathParam("id") Integer id) {

        try {
            projectService.delete(id);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.status(204).build();
    }


}
