package JMSMessenger;

import java.io.Serializable;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.ws.rs.core.Response;

public class JMSMessengerHelper {

    private JMSMessenger messenger;

    public JMSMessenger getMessenger() {
        return messenger;
    }
    private javax.jms.ConnectionFactory jmsconnectionfactory;
    private javax.jms.Topic jmsdestination;
    private javax.jms.JMSContext jmscontext;
    private javax.jms.JMSConsumer jmsconsumer;
    private javax.jms.JMSProducer jmsproducer;
    private int currentId;

    public JMSMessengerHelper(ConnectionFactory jmsconnectionfactory, Topic jmsdestination,String identity,String filter) {
        this.jmsconnectionfactory = jmsconnectionfactory;
        this.jmsdestination = jmsdestination;
        jmscontext=jmsconnectionfactory.createContext();
        jmsconsumer=jmscontext.createSharedDurableConsumer(jmsdestination, identity, filter);
        jmsproducer=jmscontext.createProducer();
        messenger = new JMSMessenger(jmsdestination, jmsproducer, jmsconsumer);
        currentId=0;
        messenger.Start();
    }
    
    public void stopJMS()
    {
        messenger.Stop();
    }
    
    public int sendMessage(Serializable o,String destination,int opcode)
    {
        int id=-1;
        try{
        ObjectMessage objectMessage = jmscontext.createObjectMessage();
        objectMessage.setObject(o);
        objectMessage.setStringProperty("Destination",destination);
        objectMessage.setIntProperty("Opcode", opcode);
        id=++currentId;
        objectMessage.setIntProperty("ID", id);
        messenger.send(objectMessage);
        }catch(Exception ex){ex.printStackTrace(); return -1;}
        return id;
    } 
    
    public int sendResponse(Serializable o,int status,int id)
    {
        try{
        ObjectMessage objectMessage = jmscontext.createObjectMessage();
        objectMessage.setObject(o);
        objectMessage.setStringProperty("Destination",Destinations.CENTRAL_SERVER);
        objectMessage.setIntProperty("Status", status);
        objectMessage.setIntProperty("ID", id);
        messenger.send(objectMessage);
        System.out.println(objectMessage);
        }catch(Exception ex){ex.printStackTrace(); return -1;}
        return id;
    }
    
    public int sendResponse(Serializable o,String destination,int status,int id)
    {
        try{
        ObjectMessage objectMessage = jmscontext.createObjectMessage();
        objectMessage.setObject(o);
        objectMessage.setStringProperty("Destination",destination);
        objectMessage.setIntProperty("Status", status);
        objectMessage.setIntProperty("ID", id);
        messenger.send(objectMessage);
        System.out.println(objectMessage);
        }catch(Exception ex){ex.printStackTrace(); return -1;}
        return id;
    }
    
    public Message recieveResponse(int id)
    {
        return messenger.filteredRecieve("ID",id,5);
    }
    
    public Response GETHandler(String destination,int opcode,Class type)
    {
        int id=sendMessage(null, destination, opcode);
        Message msg=recieveResponse(id);
        if(msg==null){return Response.status(408).build();}
        int status;
        try {
            status=msg.getIntProperty("Status");
            ObjectMessage omsg=(ObjectMessage)msg;
            Object obj = omsg.getBody(type);
            return Response.status(status).entity(obj).build();
        }catch(Exception e){e.printStackTrace();}
        return Response.status(500).build();
    }
    
    public Response GETHandler(Serializable o,String destination,int opcode,Class type)
    {
        int id=sendMessage(o, destination, opcode);
        Message msg=recieveResponse(id);
        if(msg==null){return Response.status(408).build();}
        int status;
        try {
            status=msg.getIntProperty("Status");
            ObjectMessage omsg=(ObjectMessage)msg;
            Object obj = omsg.getBody(type);
            return Response.status(status).entity(obj).build();
        }catch(Exception e){e.printStackTrace();}
        return Response.status(500).build();
    }
    
    public Response POSTHandler(Serializable o,String destination,int opcode)
    {
        int id=sendMessage(o, destination, opcode);
        Message msg=recieveResponse(id);
        if(msg==null){return Response.status(408).build();}
        int status=500;
        try{status=msg.getIntProperty("Status");}catch(Exception e){e.printStackTrace();}
        return Response.status(status).build();
    }
}
