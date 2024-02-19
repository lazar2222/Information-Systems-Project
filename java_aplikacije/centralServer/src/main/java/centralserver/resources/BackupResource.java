package centralserver.resources;

import InteropClasses.Baza;
import InteropClasses.Filijala;
import InteropClasses.Komitent;
import InteropClasses.Mesto;
import InteropClasses.Racun;
import InteropClasses.Transakcija;
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
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("backup")
public class BackupResource {

    @GET
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getBackup()
    {
        return Banka.getJMSHelper().GETHandler(Destinations.SUBSYSTEM_3, Opcodes.GET_BACKUP, Baza.class);
    }
    
    @GET
    @Path("diff")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getBackupDiff()
    {
        Baza sub3,sub2,sub1;
        JMSMessengerHelper helper = Banka.getJMSHelper();
        int id=helper.sendMessage(null, Destinations.SUBSYSTEM_3, Opcodes.GET_BACKUP);
        Message msg=helper.recieveResponse(id);
        if(msg==null){return Response.status(408).build();}
        try {
            ObjectMessage omsg=(ObjectMessage)msg;
            sub3 = omsg.getBody(Baza.class);
        }catch(Exception e){
            e.printStackTrace();
            return Response.status(500).build();
        }
        
        
        id=helper.sendMessage(null, Destinations.SUBSYSTEM_2, Opcodes.GET_DIFF);
        msg=helper.recieveResponse(id);
        if(msg==null){return Response.status(408).build();}
        try {
            ObjectMessage omsg=(ObjectMessage)msg;
            sub2 = omsg.getBody(Baza.class);
        }catch(Exception e){
            e.printStackTrace();
            return Response.status(500).build();
        }
        
        id=helper.sendMessage(null, Destinations.SUBSYSTEM_1, Opcodes.GET_DIFF);
        msg=helper.recieveResponse(id);
        if(msg==null){return Response.status(408).build();}
        try {
            ObjectMessage omsg=(ObjectMessage)msg;
            sub1 = omsg.getBody(Baza.class);
        }catch(Exception e){
            e.printStackTrace();
            return Response.status(500).build();
        }
        
        Baza diff = formDiff(sub3,sub2,sub1);
        if(diff==null){return Response.status(500).build();}
        return Response.status(200).entity(diff).build();
    }

    private Baza formDiff(Baza sub3, Baza sub2, Baza sub1) {
        Baza diff = new Baza();
        diff.setMesta(formDiffMesta(sub3,sub1));
        diff.setFilijale(formDiffFilijale(sub3,sub1));
        diff.setKomitenti(formDiffKomitenti(sub3,sub1,sub2));
        diff.setRacuni(formDiffRacuni(sub3,sub2));
        diff.setTransakcije(formDiffTransakcije(sub3,sub2));
        return diff;
    }

    private ArrayList<Mesto> formDiffMesta(Baza sub3, Baza sub1) {
        ArrayList<Mesto> ret=new ArrayList<>();
        for (Mesto mesto3 : sub3.getMesta()) {
            Mesto mesto1 =findMByID(sub1.getMesta(), mesto3.getiDMesta());
            if(mesto1==null)
            {
                ret.add(mesto3);
                ret.add(mesto1);
            }
            else
            {
                if(!mesto3.equals(mesto1))
                {
                    ret.add(mesto3);
                    ret.add(mesto1);
                }
            }
        }
        for (Mesto mesto1 : sub1.getMesta()) {
            Mesto mesto3=findMByID(sub3.getMesta(), mesto1.getiDMesta());
            if(mesto3==null)
            {
                ret.add(mesto3);
                ret.add(mesto1);
            }
        }
        return ret;
    }
    
    private ArrayList<Filijala> formDiffFilijale(Baza sub3, Baza sub1) {
        ArrayList<Filijala> ret=new ArrayList<>();
        for (Filijala filijala3 : sub3.getFilijale()) {
            Filijala filijala1 =findFByID(sub1.getFilijale(), filijala3.getiDFilijale());
            if(filijala1==null)
            {
                ret.add(filijala3);
                ret.add(filijala1);
            }
            else
            {
                if(!filijala3.equals(filijala1))
                {
                    ret.add(filijala3);
                    ret.add(filijala1);
                }
            }
        }
        for (Filijala filijala1 : sub1.getFilijale()) {
            Filijala filijala3=findFByID(sub3.getFilijale(), filijala1.getiDFilijale());
            if(filijala3==null)
            {
                ret.add(filijala3);
                ret.add(filijala1);
            }
        }
        return ret;
    }

    private ArrayList<Komitent> formDiffKomitenti(Baza sub3, Baza sub1, Baza sub2) {
        ArrayList<Komitent> ret=new ArrayList<>();
        ArrayList<Komitent> compound=new ArrayList<>();
        for (Komitent komitent : sub3.getKomitenti()) {
            if(findKByID(compound,komitent.getiDKomitenta())==null)
            {
                compound.add(komitent);
            }
        }
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
            Komitent k3=findKByID(sub3.getKomitenti(), komitent.getiDKomitenta());
            Komitent k2=findKByID(sub2.getKomitenti(), komitent.getiDKomitenta());
            Komitent k1=findKByID(sub1.getKomitenti(), komitent.getiDKomitenta());
            if(k3==null || k2 ==null || k1==null)
            {
                ret.add(k3);
                ret.add(k2);
                ret.add(k1);
            }
            else
            {
                if(!(k3.equals(k1) && k3.equals(k2)))
                {
                    ret.add(k3);
                    ret.add(k2);
                    ret.add(k1);
                }
            }
        }
        return ret;
    }

    private ArrayList<Racun> formDiffRacuni(Baza sub3, Baza sub1) {
        ArrayList<Racun> ret=new ArrayList<>();
        for (Racun racun3 : sub3.getRacuni()) {
            Racun racun1 =findRByID(sub1.getRacuni(), racun3.getiDRacuna());
            if(racun1==null)
            {
                ret.add(racun3);
                ret.add(racun1);
            }
            else
            {
                if(!racun3.equals(racun1))
                {
                    ret.add(racun3);
                    ret.add(racun1);
                }
            }
        }
        for (Racun racun1 : sub1.getRacuni()) {
            Racun racun3=findRByID(sub3.getRacuni(), racun1.getiDRacuna());
            if(racun3==null)
            {
                ret.add(racun3);
                ret.add(racun1);
            }
        }
        return ret;
    }

    private ArrayList<Transakcija> formDiffTransakcije(Baza sub3, Baza sub1) {
        ArrayList<Transakcija> ret=new ArrayList<>();
        for (Transakcija transakcija3 : sub3.getTransakcije()) {
            Transakcija transakcija1 =findTByID(sub1.getTransakcije(), transakcija3.getiDTransakcije());
            if(transakcija1==null)
            {
                ret.add(transakcija3);
                ret.add(transakcija1);
            }
            else
            {
                if(!transakcija3.equals(transakcija1))
                {
                    ret.add(transakcija3);
                    ret.add(transakcija1);
                }
            }
        }
        for (Transakcija transakcija1 : sub1.getTransakcije()) {
            Transakcija transakcija3=findTByID(sub3.getTransakcije(), transakcija1.getiDTransakcije());
            if(transakcija3==null)
            {
                ret.add(transakcija3);
                ret.add(transakcija1);
            }
        }
        return ret;
    }
    
    private Mesto findMByID(List<Mesto> lsf,int id)
    {
        for (Mesto mesto : lsf) {
            if(mesto.getiDMesta()==id)
            {
                return mesto;
            }
        }
        return null;
    }
    
    private Filijala findFByID(List<Filijala> lsf,int id)
    {
        for (Filijala filijala : lsf) {
            if(filijala.getiDFilijale()==id)
            {
                return filijala;
            }
        }
        return null;
    }
    
    private Komitent findKByID(List<Komitent> lsf,int id)
    {
        for (Komitent komitent : lsf) {
            if(komitent.getiDKomitenta()==id)
            {
                return komitent;
            }
        }
        return null;
    }
    
    private Racun findRByID(List<Racun> lsf,int id)
    {
        for (Racun racun : lsf) {
            if(racun.getiDRacuna()==id)
            {
                return racun;
            }
        }
        return null;
    }
    
    private Transakcija findTByID(List<Transakcija> lsf,int id)
    {
        for (Transakcija transakcija : lsf) {
            if(transakcija.getiDTransakcije()==id)
            {
                return transakcija;
            }
        }
        return null;
    }
    
}
