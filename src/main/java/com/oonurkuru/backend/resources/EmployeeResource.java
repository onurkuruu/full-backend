package com.oonurkuru.backend.resources;

import com.oonurkuru.backend.dto.EmployeeDTO;
import com.oonurkuru.backend.dto.ProjectDTO;
import com.oonurkuru.backend.exceptions.CustomException;
import com.oonurkuru.backend.services.EmployeeService;
import com.oonurkuru.backend.services.ProjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Onur Kuru on 22.7.2017.
 */


/**
 * Employee kaynaklarını kullanıcıya sunan servis
 * /employee ile servise erişim sağlanır
 *
 * @GET /employees ile employee kaynaklarında arama yapılabilir.
 * @POST /employees yeni bir employee eklemek için kullanılır.
 * @PUT /employees/{employeeId} employee düzenlemek için kullanılır.
 * @DELETE /employees/{employeeId} employee silmek için kullanılır.
 * @GET /employees/{employeeId}/projects employee ye ait projeler listelenir
 * @GET /employees/{employeeId}/projects/{projectId} kullanıcıya ait ilgili proje listenenir.
 * @POST /employees/{employeeId}/projects kullanıcıya bir proje ekleme için kullanılır.
 * @DELETE /employees/{employeeId}/projects/{projectId} kullanıcıya ait bir proje silmek için kullanılır.
 */
@Component
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    final private EmployeeService employeeService;
    final private ProjectService projectService;
    final private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public EmployeeResource(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GET
    public Response findEmployeeByParameters(@Context UriInfo uriInfo) {
        logger.info("/employees (GET) çağrıldı.");
        List<EmployeeDTO> employeeDTOList;

        try {
            employeeDTOList = employeeService.findEmployeeByParameters(uriInfo.getQueryParameters());
        } catch (CustomException e) {
            logger.debug("/employees (GET) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }
        logger.info("/employees (GET) başarıyla tamamlandı.");
        return Response.ok().entity(employeeDTOList).build();
    }

    @GET
    @Path("/{id: \\d+}")
    @PreAuthorize("#employeeId == principal.id")
    public Response findEmployeeById(@PathParam("id") Integer employeeId) {
        logger.info("/employees/" + employeeId + " (GET) çağrıldı.");
        EmployeeDTO employeeDTO;

        try {
            employeeDTO = employeeService.findEmployeeById(employeeId);
        } catch (CustomException e) {
            logger.debug("/employees/ " + employeeId + " (GET) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        logger.info("/employees/" + employeeId + " (GET) başarıyla tamamlandı.");
        return Response.ok().entity(employeeDTO).build();
    }

    @POST
    public Response createEmployee(@Valid EmployeeDTO employee) {
        logger.info("/employees (POST) çağrıldı.");
        EmployeeDTO created;
        try {
            employee.setId(null);
            created = employeeService.save(employee);
        } catch (CustomException e) {
            logger.debug("/employees (POST) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        logger.info("/employees (POST) başarıyla tamamlandı.");
        return Response.status(201).entity(created).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    public Response updateEmployee(@Valid EmployeeDTO employee) {
        logger.info("/employees/" + employee.getId() + " (PUT) çağrıldı.");
        EmployeeDTO updated;
        try {
            updated = employeeService.save(employee);
        } catch (CustomException e) {
            logger.debug("/employees/" + employee.getId() + " (PUT) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }
        logger.info("/employees/" + employee.getId() + " (PUT) başarıyla tamamlandı.");
        return Response.status(201).entity(updated).build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    public Response deleteEmployee(@PathParam("id") Integer id) {
        logger.info("/employees/" + id + " (DELETE) çağrıldı.");
        try {
            employeeService.delete(id);
        } catch (CustomException e) {
            logger.debug("/employees/" + id + " (DELETE) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }
        logger.info("/employees/" + id + " (DELETE) başarıyla tamamlandı.");
        return Response.status(204).build();
    }


    @GET
    @Path("/{id: \\d+}/projects")
    public Response getProjectsByEmployeeId(@PathParam("id") Integer employeeId) {

        logger.info("/employees/" + employeeId + "/projects (GET) çağrıldı.");
        List<ProjectDTO> projectDTOList;

        try {
            projectDTOList = projectService.findProjectsByEmployee(employeeId);
        } catch (CustomException e) {
            logger.info("/employees/" + employeeId + "/projects (GET) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        logger.info("/employees/" + employeeId + "/projects (GET) başarıyla tamamlandı.");
        return Response.ok().entity(projectDTOList).build();
    }

    @GET
    @Path("/{employeeId: \\d+}/projects/{projectId: \\d+}")
    public Response getProjectByEmployeeIdAndProjectId(@PathParam("employeeId") Integer employeeId, @PathParam("projectId") Integer projectId) {

        logger.info("/employees/" + employeeId + "/projects/ " + projectId + " (GET) çağrıldı.");
        ProjectDTO projectDTO;

        try {
            projectDTO = projectService.findProjectByEmployeeIdAndProjectId(employeeId, projectId);
        } catch (CustomException e) {
            logger.info("/employees/" + employeeId + "/projects/ " + projectId + " (GET) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        logger.info("/employees/" + employeeId + "/projects/ " + projectId + " (GET) başarıyla tamamlandı.");
        return Response.ok().entity(projectDTO).build();
    }


    @POST
    @Path("/{employeeId: \\d+}/projects")
    public Response addProjectToEmployee(@PathParam("employeeId") Integer employeeId, ProjectDTO projectDTO) {

        logger.info("/employees/" + employeeId + "/projects (POST) çağrıldı.");
        EmployeeDTO employeeDTO;

        try {
            employeeDTO = employeeService.addProjectToEmployee(employeeId, projectDTO);
        } catch (CustomException e) {
            logger.info("/employees/" + employeeId + "/projects (POST) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        logger.info("/employees/" + employeeId + "/projects (POST) başarıyla tamamlandı.");
        return Response.ok().entity(employeeDTO).build();
    }

    @DELETE
    @Path("/{employeeId: \\d+}/projects/{projectId: \\d+}")
    public Response deleteProjectFromEmployee(@PathParam("employeeId") Integer employeeId, @PathParam("projectId") Integer projectId) {

        logger.info("/employees/" + employeeId + "/projects/ " + projectId + " (DELETE) çağrıldı.");
        EmployeeDTO employeeDTO;

        try {
            employeeDTO = employeeService.deleteProjectFromEmployee(employeeId, projectId);
        } catch (CustomException e) {
            logger.info("/employees/" + employeeId + "/projects/ " + projectId + " (DELETE) hata oluştu. Mesaj: " + e.getMessage(), e);
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        logger.info("/employees/" + employeeId + "/projects/ " + projectId + " (DELETE) başarıyla tamamlandı.");
        return Response.ok().entity(employeeDTO).build();
    }

}
