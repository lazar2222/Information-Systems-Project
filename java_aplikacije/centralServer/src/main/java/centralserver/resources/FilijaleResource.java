package centralserver.resources;

import InteropClasses.Filijala;
import JMSMessenger.Destinations;
import JMSMessenger.JMSMessengerHelper;
import JMSMessenger.Opcodes;
import centralserver.Banka;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("filijale")
public class FilijaleResource {

    @GET
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getFilijale()
    {
        return Banka.getJMSHelper().GETHandler(Destinations.SUBSYSTEM_1, Opcodes.GET_FILIJALE, ArrayList.class);
    }
    
    @POST
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response postFilijale(Filijala filijala)
    {
        return Banka.getJMSHelper().POSTHandler(filijala, Destinations.SUBSYSTEM_1, Opcodes.CREATE_FILIJALA);
    }
}
