package subsystem1;

import InteropClasses.Baza;
import InteropClasses.Filijala;
import InteropClasses.Komitent;
import InteropClasses.Mesto;
import JMSMessenger.Destinations;
import JMSMessenger.JMSMessengerHelper;
import JMSMessenger.Opcodes;
import entityClasses.Filijale;
import entityClasses.Komitenti;
import entityClasses.Mesta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.Response;


public class Main {

    @Resource(lookup = "IntraSystemTopic")
    static Topic IntraSystemTopic;
    @Resource(lookup = "IntraSystemConnectionFactory")
    static ConnectionFactory IntraSystemConnectionFactory;
    static EntityManager em;
    @Resource
    static UserTransaction utx;
    
    static JMSMessengerHelper helper;
    
    public static void main(String[] args) {
        helper = new JMSMessengerHelper(IntraSystemConnectionFactory, IntraSystemTopic, Destinations.SUBSYSTEM_1, "Destination='"+Destinations.SUBSYSTEM_1+"'");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("subsystem1PU");
        em=emf.createEntityManager();
        
        while(true)
        {
            Message msg=helper.getMessenger().recieve(5);
            if(msg!=null && msg instanceof ObjectMessage)
            {
                ObjectMessage omsg=(ObjectMessage) msg;
                System.out.println(omsg);
                try{
                    int opcode=omsg.getIntProperty("Opcode");
                    int id=omsg.getIntProperty("ID");
                    Serializable obj=omsg.getObject();
                    switch (opcode)
                    {
                        case Opcodes.GET_DIFF: {getDiff(id); break;}
                        case Opcodes.GET_BACKUP: {getBackup(id); break;}
                        case Opcodes.GET_FILIJALE: {getFilijale(id); break;}
                        case Opcodes.CREATE_FILIJALA: {createFilijala(id,obj); break;}
                        case Opcodes.GET_KOMITENTI: {getKomitenti(id); break;}
                        case Opcodes.CREATE_KOMITENT: {createKomitent(id,obj); break;}
                        case Opcodes.PATCH_KOMITENT: {patchKomitent(id,obj); break;}
                        case Opcodes.GET_MESTA: {getMesta(id); break;}
                        case Opcodes.CREATE_MESTO: {createMesto(id,obj); break;}
                    }
                }
                catch(Exception e){e.printStackTrace();}
            }
        }
    }

    private static void getDiff(int id) {
        Baza b = new Baza();
        List<Mesta> mesta = em.createNamedQuery("Mesta.findAll",Mesta.class).getResultList();
        List<Filijale> filijale=em.createNamedQuery("Filijale.findAll",Filijale.class).getResultList();
        List<Komitenti> komitenti=em.createNamedQuery("Komitenti.findAll",Komitenti.class).getResultList();
        for (Mesta mesto : mesta) {
            Mesto m = new Mesto();
            m.toInteropClass(mesto);
            b.getMesta().add(m);
        }
        for (Filijale filijala : filijale) {
            Filijala f = new Filijala();
            f.toInteropClass(filijala);
            b.getFilijale().add(f);
        }
        for (Komitenti komitent : komitenti) {
            Komitent k = new Komitent();
            k.toInteropClass(komitent);
            b.getKomitenti().add(k);
        }
        helper.sendResponse(b, 200, id);
    }
    
    private static void getBackup(int id) {
        Baza b = new Baza();
        List<Mesta> mesta = em.createNamedQuery("Mesta.findAll",Mesta.class).getResultList();
        List<Filijale> filijale=em.createNamedQuery("Filijale.findAll",Filijale.class).getResultList();
        List<Komitenti> komitenti=em.createNamedQuery("Komitenti.findAll",Komitenti.class).getResultList();
        for (Mesta mesto : mesta) {
            Mesto m = new Mesto();
            m.toInteropClass(mesto);
            b.getMesta().add(m);
        }
        for (Filijale filijala : filijale) {
            Filijala f = new Filijala();
            f.toInteropClass(filijala);
            b.getFilijale().add(f);
        }
        for (Komitenti komitent : komitenti) {
            Komitent k = new Komitent();
            k.toInteropClass(komitent);
            b.getKomitenti().add(k);
        }
        helper.sendResponse(b,Destinations.SUBSYSTEM_3, 200, id);
    }

    private static void getFilijale(int id) {
        List<Filijale> filijale=em.createNamedQuery("Filijale.findAll",Filijale.class).getResultList();
        ArrayList<Filijala> res = new ArrayList<>();
        for (Filijale filijala : filijale) {
            Filijala f = new Filijala();
            f.toInteropClass(filijala);
            res.add(f);
        }
        helper.sendResponse(res, 200, id);
    }

    @Transactional
    private static void createFilijala(int id, Object obj) {
        Filijale filijala = new Filijale();
        ((Filijala)obj).fromInteropClass(filijala);
        Mesta m = em.find(Mesta.class, filijala.getIDMesta().getIDMesta());
        filijala.setIDMesta(m);
        try {
            utx.begin();
            em.joinTransaction();
            em.persist(filijala);
            em.flush();
            utx.commit();
            helper.sendResponse(null, 200, id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e) { e.printStackTrace(); }
            helper.sendResponse(null, 500, id);
        }
    }

    private static void getKomitenti(int id) {
        List<Komitenti> komitenti=em.createNamedQuery("Komitenti.findAll",Komitenti.class).getResultList();
        ArrayList<Komitent> res = new ArrayList<>();
        for (Komitenti komitent : komitenti) {
            Komitent k = new Komitent();
            k.toInteropClass(komitent);
            res.add(k);
        }
        helper.sendResponse(res, 200, id);
    }

    private static void createKomitent(int id, Serializable obj) {
        Komitenti komitent = new Komitenti();
        ((Komitent)obj).fromInteropClass(komitent);
        Mesta m = em.find(Mesta.class, komitent.getIDMestaSedista().getIDMesta());
        komitent.setIDMestaSedista(m);
        try {
            utx.begin();
            em.joinTransaction();
            em.persist(komitent);
            em.flush();
            ((Komitent)obj).setiDKomitenta(komitent.getIDKomitenta());
            int sid=helper.sendMessage(obj, Destinations.SUBSYSTEM_2, Opcodes.CREATE_KOMITENT);
            Message msg=helper.recieveResponse(sid);
            int status=500;
            if(msg==null){status=408;}
            else{
                try{status=msg.getIntProperty("Status");}catch(Exception e){e.printStackTrace();}
            }
            if(status!=200)
            {
                throw new Exception("Error propagating to subsystem2");
            }
            
            
            utx.commit();
            helper.sendResponse(null, 200, id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e) { e.printStackTrace(); }
            helper.sendResponse(null, 500, id);
        }
    }

    private static void patchKomitent(int id, Serializable obj) {
        Komitent k=((Komitent)obj);
        try {
            utx.begin();
            em.joinTransaction();
            
            Komitenti komitent = em.find(Komitenti.class, k.getiDKomitenta());
            Mesta mesto=em.find(Mesta.class, k.getiDMestaSedista());
            komitent.setIDMestaSedista(mesto);
            int sid=helper.sendMessage(obj, Destinations.SUBSYSTEM_2, Opcodes.PATCH_KOMITENT);
            Message msg=helper.recieveResponse(sid);
            int status=500;
            if(msg==null){status=408;}
            else{
                try{status=msg.getIntProperty("Status");}catch(Exception e){e.printStackTrace();}
            }
            if(status!=200)
            {
                throw new Exception("Error propagating to subsystem2");
            }
            
            em.flush();
            utx.commit();
            helper.sendResponse(null, 200, id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e) { e.printStackTrace(); }
            helper.sendResponse(null, 500, id);
        }
    }

    private static void getMesta(int id) {
        List<Mesta> mesta=em.createNamedQuery("Mesta.findAll",Mesta.class).getResultList();
        ArrayList<Mesto> res = new ArrayList<>();
        for (Mesta mesto : mesta) {
            Mesto m = new Mesto();
            m.toInteropClass(mesto);
            res.add(m);
        }
        helper.sendResponse(res, 200, id);
    }

    @Transactional
    private static void createMesto(int id, Object obj) {
        Mesta mesto = new Mesta();
        ((Mesto)obj).fromInteropClass(mesto);
        try {
            utx.begin();
            em.joinTransaction();
            em.persist(mesto);
            em.flush();
            utx.commit();
            helper.sendResponse(null, 200, id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e) { e.printStackTrace(); }
            helper.sendResponse(null, 500, id);
        }
        
    }
    
}
