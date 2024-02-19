package centralserver.resources;

import InteropClasses.Mesto;
import JMSMessenger.Destinations;
import JMSMessenger.Opcodes;
import centralserver.Banka;
import java.util.ArrayList;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("mesta")
public class MestaResource {
    
    @GET
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getMesta()
    {
        return Banka.getJMSHelper().GETHandler(Destinations.SUBSYSTEM_1, Opcodes.GET_MESTA, ArrayList.class);
    }
    
    @POST
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response postMesta(Mesto mesto){
        return Banka.getJMSHelper().POSTHandler(mesto, Destinations.SUBSYSTEM_1, Opcodes.CREATE_MESTO);
    }
    
}
