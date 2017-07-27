package com.oonurkuru.backend.resources;

import com.oonurkuru.backend.dto.DepartmentDTO;
import com.oonurkuru.backend.exceptions.CustomException;
import com.oonurkuru.backend.services.DepartmentService;
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
@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    final DepartmentService departmentService;

    @Autowired
    public DepartmentResource(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GET
    public Response findDepartmentByParameters(@Context UriInfo uriInfo) {

        List<DepartmentDTO> departmentDTOList;

        try {
            departmentDTOList = departmentService.findDepartmentByParameters(uriInfo.getQueryParameters());
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.ok().entity(departmentDTOList).build();
    }


    @GET
    @Path("/{id: \\d+}")
    public Response findDepartmentById(@PathParam("id") Integer departmentId) {

        DepartmentDTO departmentDTO;

        try {
            departmentDTO = departmentService.findDepartmentById(departmentId);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.ok().entity(departmentDTO).build();
    }

    @POST
    public Response createDepartment(@Valid DepartmentDTO departmentDTO) {

        DepartmentDTO created;
        try {
            departmentDTO.setId(null);
            created = departmentService.save(departmentDTO);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.status(201).entity(created).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    public Response updateDepartment(@Valid DepartmentDTO departmentDTO) {

        DepartmentDTO updated;
        try {
            updated = departmentService.save(departmentDTO);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.status(201).entity(updated).build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    public Response deleteEmployee(@PathParam("id") Integer id) {

        try {
            departmentService.delete(id);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.status(204).build();
    }

}
