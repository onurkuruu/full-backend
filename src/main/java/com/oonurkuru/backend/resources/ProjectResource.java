package com.oonurkuru.backend.resources;

import com.oonurkuru.backend.dto.ProjectDTO;
import com.oonurkuru.backend.exceptions.CustomException;
import com.oonurkuru.backend.services.ProjectService;
import org.apache.log4j.Logger;
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
    final private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GET
    public Response findProjectByParameters(@Context UriInfo uriInfo) {
        logger.info("/projects (GET) çağrıldı.");
        List<ProjectDTO> projectDTOList;

        try {
            projectDTOList = projectService.findProjectByParameters(uriInfo.getQueryParameters());
        } catch (CustomException e) {
            logger.debug("/projects (GET) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        logger.info("/projects (GET) başarıyla tamamlandı.");
        return Response.ok().entity(projectDTOList).build();
    }

    @GET
    @Path("/{id: \\d+}")
    public Response findProjectById(@PathParam("id") Integer projectId) {

        logger.info("/projects/" + projectId + " (GET) çağrıldı.");
        ProjectDTO projectDTO;

        try {
            projectDTO = projectService.findProjectById(projectId);
        } catch (CustomException e) {
            logger.debug("/projects/ " + projectId + " (GET) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        logger.info("/projects/" + projectId + " (GET) başarıyla tamamlandı.");
        return Response.ok().entity(projectDTO).build();
    }

    @POST
    public Response createProject(@Valid ProjectDTO projectDTO) {

        logger.info("/projects (POST) çağrıldı.");
        ProjectDTO created;
        try {
            projectDTO.setId(null);
            created = projectService.save(projectDTO);
        } catch (CustomException e) {
            logger.debug("/projects (POST) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        logger.info("/projects (POST) başarıyla tamamlandı.");
        return Response.status(201).entity(created).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    public Response updateProject(@Valid ProjectDTO projectDTO) {

        logger.info("/projects/" + projectDTO.getId() + " (PUT) çağrıldı.");
        ProjectDTO updated;
        try {
            updated = projectService.save(projectDTO);
        } catch (CustomException e) {
            logger.debug("/projects/" + projectDTO.getId() + " (PUT) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        logger.info("/projects/" + projectDTO.getId() + " (PUT) başarıyla tamamlandı.");
        return Response.status(201).entity(updated).build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    public Response deleteProject(@PathParam("id") Integer id) {

        logger.info("/projects/" + id + " (DELETE) çağrıldı.");
        try {
            projectService.delete(id);
        } catch (CustomException e) {
            logger.debug("/projects/" + id + " (DELETE) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        logger.info("/projects/" + id + " (DELETE) başarıyla tamamlandı.");
        return Response.status(204).build();
    }


}
