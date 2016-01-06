package main.java.se.bumaklion.myrecipes.resources;

import main.java.se.bumaklion.myrecipes.domain.Measurement;
import main.java.se.bumaklion.myrecipes.service.BumService;
import main.java.se.bumaklion.myrecipes.service.MeasurementService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("measurements")
public class MeasurementResource extends BumResource<Measurement> {

    @GET
    @Path("{measurementId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMeasurement(@Context UriInfo uriInfo, @PathParam("measurementId") String uuid) {
        Measurement measurement = new MeasurementService().getByUuid(uuid);
        if (measurement == null)
            return getErrorResponse(Status.BAD_REQUEST, "Can't find measurement with id: <" + uuid + ">.");

        return getResponse(Status.OK, getJson(measurement, uriInfo));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMeasurements(@Context UriInfo uriInfo) {
        List<Measurement> measurements = new MeasurementService().getAll();
        return getResponse(Status.OK, getJson(measurements, "Measurements", uriInfo));
    }

    @Override
    protected BumService<Measurement> getService() {
        return new MeasurementService();
    }

    @Override
    protected Class<Measurement> getClazz() {
        return Measurement.class;
    }

}
