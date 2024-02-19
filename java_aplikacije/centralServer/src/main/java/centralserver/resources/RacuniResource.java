package centralserver.resources;

import InteropClasses.Racun;
import JMSMessenger.Destinations;
import JMSMessenger.Opcodes;
import centralserver.Banka;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("racuni")
public class RacuniResource {

    @POST
    public Response postRacuni(Racun racun)
    {
        racun.setDatumVremeOtvaranja(Date.from(Instant.now().truncatedTo(ChronoUnit.SECONDS)));
        return Banka.getJMSHelper().POSTHandler(racun, Destinations.SUBSYSTEM_2, Opcodes.CREATE_RACUN);
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteRacuni(@PathParam("id") int idRacuna)
    {
        return Banka.getJMSHelper().POSTHandler(idRacuna, Destinations.SUBSYSTEM_2, Opcodes.DELETE_RACUN);
    }
    
    @GET
    @Path("{id}/transakcije")
    public Response getRacuniTransakcije(@PathParam("id") int idRacuna)
    {
        return Banka.getJMSHelper().GETHandler(idRacuna,Destinations.SUBSYSTEM_2, Opcodes.GET_TRANSAKCIJE_RACUN, ArrayList.class);
    }
}
