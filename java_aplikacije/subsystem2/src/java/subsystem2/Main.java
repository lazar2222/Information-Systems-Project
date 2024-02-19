package subsystem2;

import InteropClasses.Baza;
import InteropClasses.Komitent;
import InteropClasses.Racun;
import InteropClasses.Transakcija;
import JMSMessenger.Destinations;
import JMSMessenger.JMSMessengerHelper;
import JMSMessenger.Opcodes;
import entityClasses.Komitenti;
import entityClasses.Racuni;
import entityClasses.Transakcije;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        helper = new JMSMessengerHelper(IntraSystemConnectionFactory, IntraSystemTopic, Destinations.SUBSYSTEM_2, "Destination='"+Destinations.SUBSYSTEM_2+"'");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("subsystem2PU");
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
                        case Opcodes.GET_RACUNI_KOMITENT: {getRacuniKomitent(id,obj); break;}
                        case Opcodes.CREATE_RACUN: {createRacun(id,obj); break;}
                        case Opcodes.DELETE_RACUN: {deleteRacun(id,obj); break;}
                        case Opcodes.GET_TRANSAKCIJE_RACUN: {getTransakcijeRacun(id,obj); break;}
                        case Opcodes.CREATE_TRANSAKCIJA: {createTransakcija(id,obj); break;}
                        case Opcodes.CREATE_KOMITENT: {createKomitent(id,obj); break;}
                        case Opcodes.PATCH_KOMITENT: {patchKomitent(id,obj); break;}
                    }
                }
                catch(Exception e){e.printStackTrace();}
            }
        }
    }

    private static void getDiff(int id) {
        Baza b = new Baza();
        List<Komitenti> komitenti = em.createNamedQuery("Komitenti.findAll",Komitenti.class).getResultList();
        List<Racuni> racuni=em.createNamedQuery("Racuni.findAll",Racuni.class).getResultList();
        List<Transakcije> transakcije=em.createNamedQuery("Transakcije.findAll",Transakcije.class).getResultList();
        for (Komitenti komitent : komitenti) {
            Komitent k = new Komitent();
            k.toInteropClass(komitent);
            b.getKomitenti().add(k);
        }
        for (Racuni racun : racuni) {
            Racun r = new Racun();
            r.toInteropClass(racun);
            b.getRacuni().add(r);
        }
        for (Transakcije transakcija : transakcije) {
            Transakcija t = new Transakcija();
            t.toInteropClass(transakcija);
            b.getTransakcije().add(t);
        }
        helper.sendResponse(b, 200, id);
    }
    
    private static void getBackup(int id) {
        Baza b = new Baza();
        List<Komitenti> komitenti = em.createNamedQuery("Komitenti.findAll",Komitenti.class).getResultList();
        List<Racuni> racuni=em.createNamedQuery("Racuni.findAll",Racuni.class).getResultList();
        List<Transakcije> transakcije=em.createNamedQuery("Transakcije.findAll",Transakcije.class).getResultList();
        for (Komitenti komitent : komitenti) {
            Komitent k = new Komitent();
            k.toInteropClass(komitent);
            b.getKomitenti().add(k);
        }
        for (Racuni racun : racuni) {
            Racun r = new Racun();
            r.toInteropClass(racun);
            b.getRacuni().add(r);
        }
        for (Transakcije transakcija : transakcije) {
            Transakcija t = new Transakcija();
            t.toInteropClass(transakcija);
            b.getTransakcije().add(t);
        }
        helper.sendResponse(b,Destinations.SUBSYSTEM_3, 200, id);
    }

    private static void getRacuniKomitent(int id, Serializable obj) {
        Komitenti komitent=em.find(Komitenti.class, ((Integer)obj));
        if(komitent==null){helper.sendResponse(null, 404, id);return;}
        List<Racuni> racuni=em.createQuery("SELECT r FROM Racuni r WHERE r.iDKomitenta = :iDKomitenta",Racuni.class)
                .setParameter("iDKomitenta", komitent).getResultList();
        ArrayList<Racun> res = new ArrayList<>();
        for (Racuni racun : racuni) {
            Racun r = new Racun();
            r.toInteropClass(racun);
            res.add(r);
        }
        helper.sendResponse(res, 200, id);
    }

    private static void createRacun(int id, Serializable obj) {
        Racuni racun = new Racuni();
        Racun r=(Racun)obj;
        r.setBrojTransakcija(0);
        r.setStanje(0L);
        r.setStatus('A');
        r.fromInteropClass(racun);
        Komitenti k = em.find(Komitenti.class, racun.getIDKomitenta().getIDKomitenta());
        racun.setIDKomitenta(k);
        try {
            utx.begin();
            em.joinTransaction();
            em.persist(racun);
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

    private static void deleteRacun(int id, Serializable obj) {
        try {
            Racuni racun =em.find(Racuni.class, ((Integer)obj));
            if(racun==null){helper.sendResponse(null, 404, id);return;}
            if(racun.getStanje()!=0){helper.sendResponse(null, 409, id);return;}
            utx.begin();
            em.joinTransaction();
            racun.setDozvoljenMinus(0);
            racun.setStatus('U');
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

    private static void getTransakcijeRacun(int id, Serializable obj) {
        Racuni racun=em.find(Racuni.class, ((Integer)obj));
        if(racun==null){helper.sendResponse(null, 404, id);return;}
        List<Transakcije> transakcije=em.createQuery("SELECT t FROM Transakcije t WHERE t.iDSrcRacuna = :iDRacuna OR t.iDDstRacuna = :iDRacuna",Transakcije.class)
                .setParameter("iDRacuna", racun).getResultList();
        ArrayList<Transakcija> res = new ArrayList<>();
        for (Transakcije transakcija : transakcije) {
            Transakcija t = new Transakcija();
            t.toInteropClass(transakcija);
            res.add(t);
        }
        helper.sendResponse(res, 200, id);
    }

    private static void createTransakcija(int id, Serializable obj) {
        Transakcija t=(Transakcija)obj;
        Transakcije transakcija = new Transakcije();
        t.fromInteropClass(transakcija);
        if(t.getiDSrcRacuna()!=null){
            Racuni sr = em.find(Racuni.class, transakcija.getIDSrcRacuna().getIDRacuna());
            transakcija.setIDSrcRacuna(sr);
        }
        if(t.getiDDstRacuna()!=null){
            Racuni dr = em.find(Racuni.class, transakcija.getIDDstRacuna().getIDRacuna());
            transakcija.setIDDstRacuna(dr);
        }
        if(autorizujTransakciju(transakcija,t.getChecked()))
        {
            try {
            utx.begin();
            em.joinTransaction();
            //Full calculated fields
            if(transakcija.getIDSrcRacuna()!=null)
            {
                transakcija.setRedniBrojSrc(transakcija.getIDSrcRacuna().getBrojTransakcija()+1);
            }
            if(transakcija.getIDDstRacuna()!=null)
            {
                transakcija.setRedniBrojDst(transakcija.getIDDstRacuna().getBrojTransakcija()+1);
            }
            em.persist(transakcija);
            //Update accounts
            if(transakcija.getIDSrcRacuna()!=null)
            {
                Racuni src=transakcija.getIDSrcRacuna();
                src.setBrojTransakcija(src.getBrojTransakcija()+1);
                src.setStanje(src.getStanje()-transakcija.getIznos());
                src.setStatus(src.getStanje()>=-src.getDozvoljenMinus()?'A':'B');
            }
            if(transakcija.getIDDstRacuna()!=null)
            {
                Racuni dst=transakcija.getIDDstRacuna();
                dst.setBrojTransakcija(dst.getBrojTransakcija()+1);
                dst.setStanje(dst.getStanje()+transakcija.getIznos());
                dst.setStatus(dst.getStanje()>=-dst.getDozvoljenMinus()?'A':'B');
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
        else
        {
            helper.sendResponse(null, 409, id);
        }
    }

    private static void patchKomitent(int id, Serializable obj) {
        Komitent k=((Komitent)obj);
        try {
            utx.begin();
            em.joinTransaction();
            Komitenti komitent = em.find(Komitenti.class, k.getiDKomitenta());
            komitent.setIDMestaSedista(k.getiDMestaSedista());
            em.flush();
            utx.commit();
            helper.sendResponse(null,Destinations.SUBSYSTEM_1,200, id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e) { e.printStackTrace(); }
            helper.sendResponse(null,Destinations.SUBSYSTEM_1,500, id);
        }
    }

    private static void createKomitent(int id, Serializable obj) {
       Komitenti komitent = new Komitenti();
        ((Komitent)obj).fromInteropClass(komitent);
        try {
            utx.begin();
            em.joinTransaction();
            em.persist(komitent);
            em.flush();
            utx.commit();
            helper.sendResponse(null,Destinations.SUBSYSTEM_1, 200, id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e) { e.printStackTrace(); }
            helper.sendResponse(null,Destinations.SUBSYSTEM_1, 500, id);
        } 
    }

    private static boolean autorizujTransakciju(Transakcije transakcija, Boolean checked) {
        if(checked==null){return false;}
        if(transakcija.getIDSrcRacuna() == null || transakcija.getIDDstRacuna()==null)
        {
            //Deposit or withdrawal, ensure at leas one is not null and filijala is not null
            if(transakcija.getIDSrcRacuna() == null && transakcija.getIDDstRacuna()==null)
            {
                return false;
            }
            if(transakcija.getIDFilijale()==null)
            {
                return false;
            }
        }
        else
        {
            //Transfer, ensure filijala is null
            if(transakcija.getIDFilijale()!=null)
            {
                return false;
            }
            if(Objects.equals(transakcija.getIDSrcRacuna().getIDRacuna(), transakcija.getIDDstRacuna().getIDRacuna()))
            {
                return false;
            }
        }
        //Verify iznos is >0
        if(transakcija.getIznos()<=0)
        {
            return false;
        }
        //Verify destination is active
        if(transakcija.getIDDstRacuna()!=null)
        {
            if(transakcija.getIDDstRacuna().getStatus()=='U')
            {
                return false;
            }
        }
        //Verify destination is active
        if(transakcija.getIDSrcRacuna()!=null)
        {
            if(transakcija.getIDSrcRacuna().getStatus()=='U')
            {
                return false;
            }
            if(transakcija.getIDSrcRacuna().getStatus()=='B' && checked)
            {
                return false;
            }
            //Verify funds available if checked
            if(checked)
            {
                if((transakcija.getIDSrcRacuna().getStanje()-transakcija.getIznos())<-transakcija.getIDSrcRacuna().getDozvoljenMinus())
                {
                    return false;
                }
            }
            
        }
        return true;
    }
    
}
