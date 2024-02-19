package centralserver.resources;

import InteropClasses.Komitent;
import InteropClasses.Racun;
import JMSMessenger.Destinations;
import JMSMessenger.Opcodes;
import centralserver.Banka;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("komitenti")
public class KomitentiResource {

    @GET
    public Response getKomitenti()    
    {
        return Banka.getJMSHelper().GETHandler(Destinations.SUBSYSTEM_1, Opcodes.GET_KOMITENTI, ArrayList.class);
    }
    
    @POST
    public Response postKomitenti(Komitent komitent)
    {
        return Banka.getJMSHelper().POSTHandler(komitent, Destinations.SUBSYSTEM_1, Opcodes.CREATE_KOMITENT);
    }
    
    @POST
    @Path("{id}")
    public Response patchKomitenti(@PathParam("id") int idKomitenta,Komitent komitent)
    {
        if(komitent.getiDKomitenta()!=null && komitent.getiDKomitenta()!=idKomitenta){return Response.status(409).build();}
        komitent.setiDKomitenta(idKomitenta);
        return Banka.getJMSHelper().POSTHandler(komitent, Destinations.SUBSYSTEM_1, Opcodes.PATCH_KOMITENT);
    }
    
    @GET
    @Path("{id}/racuni")
    public Response getKomitentiRacuni(@PathParam("id") int idKomitenta)
    {
        return Banka.getJMSHelper().GETHandler(idKomitenta,Destinations.SUBSYSTEM_2, Opcodes.GET_RACUNI_KOMITENT, ArrayList.class);
    }
}
