package subsystem3;

import InteropClasses.Baza;
import InteropClasses.Filijala;
import InteropClasses.Komitent;
import InteropClasses.Mesto;
import InteropClasses.Racun;
import InteropClasses.Transakcija;
import JMSMessenger.Destinations;
import JMSMessenger.JMSMessengerHelper;
import JMSMessenger.Opcodes;
import entityClasses.Filijale;
import entityClasses.Komitenti;
import entityClasses.Mesta;
import entityClasses.Racuni;
import entityClasses.Transakcije;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

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
        helper = new JMSMessengerHelper(IntraSystemConnectionFactory, IntraSystemTopic, Destinations.SUBSYSTEM_3, "Destination='"+Destinations.SUBSYSTEM_3+"'");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("subsystem3PU");
        em=emf.createEntityManager();
        long last=System.currentTimeMillis();
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
                        case Opcodes.GET_BACKUP: {getBackup(id); break;}
                    }
                }
                catch(Exception e){e.printStackTrace();}
            }
            long curent = System.currentTimeMillis();
            if(curent-last>2*60*1000)
            {
                doBackup();
                last=System.currentTimeMillis();
            }
        }
    }

    private static void getBackup(int id) {
        Baza b = new Baza();
        List<Mesta> mesta = em.createNamedQuery("Mesta.findAll",Mesta.class).getResultList();
        List<Filijale> filijale=em.createNamedQuery("Filijale.findAll",Filijale.class).getResultList();
        List<Komitenti> komitenti = em.createNamedQuery("Komitenti.findAll",Komitenti.class).getResultList();
        List<Racuni> racuni=em.createNamedQuery("Racuni.findAll",Racuni.class).getResultList();
        List<Transakcije> transakcije=em.createNamedQuery("Transakcije.findAll",Transakcije.class).getResultList();
        for (Mesta mesto : mesta) {
            em.refresh(mesto);
            Mesto m = new Mesto();
            m.toInteropClass(mesto);
            b.getMesta().add(m);
        }
        for (Filijale filijala : filijale) {
            em.refresh(filijala);
            Filijala f = new Filijala();
            f.toInteropClass(filijala);
            b.getFilijale().add(f);
        }
        for (Komitenti komitent : komitenti) {
            em.refresh(komitent);
            Komitent k = new Komitent();
            k.toInteropClass(komitent);
            b.getKomitenti().add(k);
        }
        for (Racuni racun : racuni) {
            em.refresh(racun);
            Racun r = new Racun();
            r.toInteropClass(racun);
            b.getRacuni().add(r);
        }
        for (Transakcije transakcija : transakcije) {
            em.refresh(transakcija);
            Transakcija t = new Transakcija();
            t.toInteropClass(transakcija);
            b.getTransakcije().add(t);
        }
        helper.sendResponse(b, 200, id);
    }

    private static void doBackup() {
        System.out.println("Running Backup...");
        try{
            int id=helper.sendMessage(null, Destinations.SUBSYSTEM_2, Opcodes.GET_BACKUP);
            Message msg=helper.recieveResponse(id);
            if(msg==null){throw new Exception("subsystem 2 timed out");}
            ObjectMessage omsg=(ObjectMessage)msg;
            Baza sub2 = omsg.getBody(Baza.class);

            id=helper.sendMessage(null, Destinations.SUBSYSTEM_1, Opcodes.GET_BACKUP);
            msg=helper.recieveResponse(id);
            if(msg==null){throw new Exception("subsystem 1 timed out");}
            omsg=(ObjectMessage)msg;
            Baza sub1 = omsg.getBody(Baza.class);
            
            checkKomitentiConsistent(sub1,sub2);
            
            utx.begin();
            em.joinTransaction();
            
            //Clear Tables
            Query q1 = em.createQuery("DELETE FROM Transakcije");
            Query q2 = em.createQuery("DELETE FROM Racuni");
            Query q3 = em.createQuery("DELETE FROM Komitenti");
            Query q4 = em.createQuery("DELETE FROM Filijale");
            Query q5 = em.createQuery("DELETE FROM Mesta");

            q1.executeUpdate();
            q2.executeUpdate();
            q3.executeUpdate();
            q4.executeUpdate();
            q5.executeUpdate();
            
            HashMap<Integer,Mesta> mapMesta=new HashMap<>();
            HashMap<Integer,Filijale> mapFilijale=new HashMap<>();
            HashMap<Integer,Komitenti> mapKomitenti=new HashMap<>();
            HashMap<Integer,Racuni> mapRacuni=new HashMap<>();
            HashMap<Integer,Transakcije> mapTransakcije=new HashMap<>();
                    
            
            for (Mesto mesto : sub1.getMesta()) {
                Mesta m = new Mesta();
                mesto.fromInteropClass(m);
                mapMesta.put(m.getIDMesta(), m);
                em.persist(m);
            }
            for (Filijala filijala : sub1.getFilijale()) {
                Filijale f = new Filijale();
                filijala.fromInteropClass(f);
                //Maintain referential intergrity
                f.setIDMesta(mapMesta.get(f.getIDMesta().getIDMesta()));
                mapFilijale.put(f.getIDFilijale(), f);
                em.persist(f);
            }
            for (Komitent komitent : sub1.getKomitenti()) {
                Komitenti k = new Komitenti();
                komitent.fromInteropClass(k);
                //Maintain referential intergrity
                k.setIDMestaSedista(mapMesta.get(k.getIDMestaSedista().getIDMesta()));
                mapKomitenti.put(k.getIDKomitenta(), k);
                em.persist(k);
            }
            for (Racun racun : sub2.getRacuni()) {
                Racuni r = new Racuni();
                racun.fromInteropClass(r);
                //Maintain referential intergrity
                r.setIDMestaOtvaranja(mapMesta.get(r.getIDMestaOtvaranja().getIDMesta()));
                r.setIDKomitenta(mapKomitenti.get(r.getIDKomitenta().getIDKomitenta()));
                mapRacuni.put(r.getIDRacuna(), r);
                em.persist(r);
            }
            for (Transakcija transakcija : sub2.getTransakcije()) {
                Transakcije t = new Transakcije();
                transakcija.fromInteropClass(t);
                //Maintain referential intergrity
                if(t.getIDSrcRacuna()!=null){
                    t.setIDSrcRacuna(mapRacuni.get(t.getIDSrcRacuna().getIDRacuna()));
                }
                if(t.getIDDstRacuna()!=null){
                    t.setIDDstRacuna(mapRacuni.get(t.getIDDstRacuna().getIDRacuna()));
                }
                if(t.getIDFilijale()!=null){
                    t.setIDFilijale(mapFilijale.get(t.getIDFilijale().getIDFilijale()));
                }
                mapTransakcije.put(t.getIDTransakcije(), t);
                em.persist(t);
            }
            
            em.flush();
            utx.commit();
            System.out.println("Backup Successful.");
        }catch(Exception e){
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception ex) { ex.printStackTrace(); }
            System.out.println("Backup Failed.");
        }
    }

    private static void checkKomitentiConsistent(Baza sub1, Baza sub2) throws Exception {
        ArrayList<Komitent> compound=new ArrayList<>();
        for (Komitent komitent : sub2.getKomitenti()) {
            if(findKByID(compound,komitent.getiDKomitenta())==null)
            {
                compound.add(komitent);
            }
        }
        for (Komitent komitent : sub1.getKomitenti()) {
            if(findKByID(compound,komitent.getiDKomitenta())==null)
            {
                compound.add(komitent);
            }
        }
        for (Komitent komitent : compound) {
            Komitent k2=findKByID(sub2.getKomitenti(), komitent.getiDKomitenta());
            Komitent k1=findKByID(sub1.getKomitenti(), komitent.getiDKomitenta());
            if(k2 ==null || k1==null)
            {
                throw new Exception("Komitenti is not consistent between subsystem1 and subsystem2, please restore from bacup");
            }
            else
            {
                if(!k1.equals(k2))
                {
                    throw new Exception("Komitenti is not consistent between subsystem1 and subsystem2, please restore from bacup");
                }
            }
        }
    }
    
    private static Komitent findKByID(List<Komitent> lsf,int id)
    {
        for (Komitent komitent : lsf) {
            if(komitent.getiDKomitenta()==id)
            {
                return komitent;
            }
        }
        return null;
    }
    
}
