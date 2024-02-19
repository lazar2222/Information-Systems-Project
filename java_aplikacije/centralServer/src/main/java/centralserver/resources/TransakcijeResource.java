package centralserver.resources;

import InteropClasses.Transakcija;
import JMSMessenger.Destinations;
import JMSMessenger.Opcodes;
import centralserver.Banka;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("transakcije")
public class TransakcijeResource {

    @POST
    public Response postTransakcije(Transakcija transakcija)
    {
        transakcija.setDatumVremeRealizacije(Date.from(Instant.now().truncatedTo(ChronoUnit.SECONDS)));
        return Banka.getJMSHelper().POSTHandler(transakcija, Destinations.SUBSYSTEM_2, Opcodes.CREATE_TRANSAKCIJA);
    }
}
