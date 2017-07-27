package com.oonurkuru.backend.resources;

import com.oonurkuru.backend.dto.EmployeeDTO;
import com.oonurkuru.backend.exceptions.CustomException;
import com.oonurkuru.backend.services.EmployeeService;
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
 * <p>
 * - Eklenecekler -
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

    @Autowired
    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GET
    public Response findEmployeeByParameters(@Context UriInfo uriInfo) {

        List<EmployeeDTO> employeeDTOList;

        try {
            employeeDTOList = employeeService.findEmployeeByParameters(uriInfo.getQueryParameters());
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.ok().entity(employeeDTOList).build();
    }

    @GET
    @Path("/{id: \\d+}")
    public Response findEmployeeById(@PathParam("id") Integer employeeId) {

        EmployeeDTO employeeDTO;

        try {
            employeeDTO = employeeService.findEmployeeById(employeeId);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.ok().entity(employeeDTO).build();
    }

    @POST
    public Response createEmployee(@Valid EmployeeDTO employee) {

        EmployeeDTO created;
        try {
            employee.setId(null);
            created = employeeService.save(employee);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.status(201).entity(created).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    public Response updateEmployee(@Valid EmployeeDTO employee) {

        EmployeeDTO updated;
        try {
            updated = employeeService.save(employee);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.status(201).entity(updated).build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    public Response deleteEmployee(@PathParam("id") Integer id) {

        try {
            employeeService.delete(id);
        } catch (CustomException e) {
            return Response.status(500).entity(e.getExceptionModel()).build();
        }

        return Response.status(204).build();
    }

}
