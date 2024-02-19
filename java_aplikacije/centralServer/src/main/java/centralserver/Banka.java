package centralserver;

import JMSMessenger.Destinations;
import JMSMessenger.JMSMessengerHelper;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.jms.ConnectionFactory;
import javax.jms.Topic;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("banka")
@Singleton
public class Banka extends Application {
    
    @Resource(lookup = "IntraSystemConnectionFactory")
    public ConnectionFactory InterSystemConnectionFactory;
    @Resource(lookup = "IntraSystemTopic")
    public Topic InterSystemTopic;
    
    public Banka()
    {
        instance=this;
    }
    
    private static Banka instance=null;
    private static JMSMessengerHelper helperInstance=null;
    
    public static JMSMessengerHelper getJMSHelper()
    {
        
        if(helperInstance==null)
        {
            if(instance==null){throw new RuntimeException("Banka is null");}
            helperInstance=new JMSMessengerHelper(instance.InterSystemConnectionFactory, instance.InterSystemTopic, Destinations.CENTRAL_SERVER,"Destination='"+Destinations.CENTRAL_SERVER+"'");
        }
        return helperInstance;
    }
    
}
