package clientapp;

import InteropClasses.Baza;
import InteropClasses.Filijala;
import InteropClasses.InteropClass;
import InteropClasses.Komitent;
import InteropClasses.Mesto;
import InteropClasses.Racun;
import InteropClasses.Transakcija;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

public class Main {

    public static BufferedReader in;
    public static Client client;
    public static String url="";
    
    public static void main(String[] args) throws Exception{
        in = new BufferedReader(new InputStreamReader(System.in));
        client=ClientBuilder.newClient().register(JacksonJsonProvider.class);
        System.out.println("Unesite URL servera:");
        url=in.readLine();
        if(url.length()==0){url="http://localhost:8080/centralServer/banka";System.out.println("Koristi se default URL:"+url);}
        if(!url.startsWith("http://")){url="http://"+url;}
        while(url.endsWith("/")){url=url.substring(0,url.length()-1);}
        System.out.println("Unesite HELP za listu komandi.");
        boolean running=true;
        while(running)
        {
            try{
                String line = in.readLine();
                if(line.equals("HELP"))                             {printHelp();}
                else if(line.equals("EXIT"))                        {running=false;}
                else if(line.equals("GET MESTA"))                   {getRequest(url+"/mesta",new GenericType<ArrayList<Mesto>>(){});}
                else if(line.equals("GET FILIJALE"))                {getRequest(url+"/filijale",new GenericType<ArrayList<Filijala>>(){});}
                else if(line.equals("GET KOMITENTI"))               {getRequest(url+"/komitenti",new GenericType<ArrayList<Komitent>>(){});}
                else if(line.startsWith("GET RACUNI KOMITENT"))     {parametrizedGetRequest("/komitenti/{id}/racuni",new GenericType<ArrayList<Racun>>(){},Integer.parseInt(line.split(" ")[3]));}
                else if(line.startsWith("GET TRANSAKCIJE RACUN"))   {parametrizedGetRequest("/racuni/{id}/transakcije",new GenericType<ArrayList<Transakcija>>(){},Integer.parseInt(line.split(" ")[3]));}
                else if(line.equals("GET BACKUP"))                  {getBackup();}
                else if(line.equals("GET BACKUP DIFF"))             {getBackupDiff();}
                else if(line.equals("POST MESTO"))                  {postMesto();}
                else if(line.equals("POST FILIJALA"))               {postFilijala();}
                else if(line.equals("POST KOMITENT"))               {postKomitent();}
                else if(line.equals("POST RACUN"))                  {postRacun();}
                else if(line.equals("POST PRENOS"))                 {postPrenos();}
                else if(line.equals("POST UPLATA"))                 {postUplata();}
                else if(line.equals("POST ISPLATA"))                {postIsplata();}
                else if(line.startsWith("POST KOMITENT MESTO"))     {patch("/komitenti/{id}",Integer.parseInt(line.split(" ")[3]),Integer.parseInt(line.split(" ")[4]));}
                else if(line.startsWith("DELETE RACUN"))            {delete("/racuni/{id}",Integer.parseInt(line.split(" ")[2]));}
                else{System.out.println("Nepoznata komanda, unesite HELP za listu komandi");}
            }catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("Greska u obradi komande");
            }
        }
    }

    private static void printHelp() {
        System.out.println("Lista komandi:");
        System.out.println("HELP: prikazuje pomoc.");
        System.out.println("EXIT: zatvara program.");
        System.out.println("GET MESTA: prikazuje sva mesta u bazi.");
        System.out.println("GET FILIJALE: prikazuje sve filijale u bazi.");
        System.out.println("GET KOMITENTI: prikazuje sve komitente u bazi.");
        System.out.println("GET RACUNI KOMITENT {idKomitenta}: prikazuje sve racune trazenog komitenta.");
        System.out.println("GET TRANSAKCIJE RACUN {idRacuna}: prikazuje sve transakcije za trazeni racun.");
        System.out.println("GET BACKUP: prikazuje stanje rezervne kopije.");
        System.out.println("GET BACKUP DIFF: prikazuje razliku stanja rezervne kopije i produkcijskog okruzenja.");
        System.out.println("POST MESTO: interaktivno dodaje mesto.");
        System.out.println("POST FILIJALA: interaktivno dodaje filijalu.");
        System.out.println("POST KOMITENT: interaktivno dodaje komitenta.");
        System.out.println("POST RACUN: interaktivno dodaje racun.");
        System.out.println("POST PRENOS: interaktivno obavlja prenos stedstava.");
        System.out.println("POST UPLATA: interaktivno obavlja uplatu sredstava.");
        System.out.println("POST ISPLATA: interaktivno obavlja isplatu sredstava.");
        System.out.println("POST KOMITENT MESTO {idKomitenta} {idMesta}: azurira mesto sedista za trazenog komitenta.");
        System.out.println("DELETE RACUN {idRacuna}: gasi trazeni racun.");
    }

    private static void getRequest(String path, GenericType T) {       
        Response resp=client.target(path).request(MediaType.APPLICATION_JSON).get();
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
        if(status!=200){return;}
        ArrayList<InteropClass> res=(ArrayList<InteropClass>)resp.readEntity(T);
        printListInterop(res);
    }
    
    private static void parametrizedGetRequest(String path, GenericType T,int par) {
        Response resp=client.target(url).path(path).resolveTemplate("id", par).request(MediaType.APPLICATION_JSON).get();
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
        if(status!=200){return;}
        ArrayList<InteropClass> res=(ArrayList<InteropClass>)resp.readEntity(T);
        printListInterop(res);
    }

    private static void printListInterop(ArrayList<InteropClass> res) {
        if(res==null){return;}
        if(res.isEmpty()){System.out.println("EMPTY");return;}
        int numcol=res.get(0).numcol();
        int[] colsize=res.get(0).colsize();
        String[] colnames=res.get(0).colnames();
        
        for (InteropClass ic : res) {
            int[] tcolsize=ic.colsize();
            for (int i = 0; i < numcol; i++) {
                if(tcolsize[i]>colsize[i]){colsize[i]=tcolsize[i];}
            }
        }
        for (int i = 0; i < numcol; i++) {
                if(colnames[i].length()>colsize[i]){colsize[i]=colnames[i].length();}
            }
        System.out.print("|");
        for (int i = 0; i < numcol; i++) {
            System.out.print(padright(colnames[i],colsize[i])+"|");
        }
        System.out.println();
        System.out.print("|");
        for (int i = 0; i < numcol; i++) {
            for (int j = 0; j < colsize[i]; j++) {
                System.out.print('=');
            }
            System.out.print("|");
        }
        System.out.println();
        for (InteropClass ic : res) {
            System.out.print("|");
            for (int i = 0; i < numcol; i++) {
                System.out.print(padright(ic.colvals()[i],colsize[i])+"|");
            }
            System.out.println();
        }
    }
    
    private static String padleft(String s,int len)
    {
        int pad=len-s.length();
        String res="";
        for (int i = 0; i < pad; i++) {
            res+=" ";
        }
        res+=s;
        return res;
    }
    
    private static String padright(String s,int len)
    {
        int pad=len-s.length();
        for (int i = 0; i < pad; i++) {
            s+=" ";
        }
        return s;
    }

    private static void getBackup() {
        Response resp=client.target(url+"/backup").request(MediaType.APPLICATION_JSON).get();
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
        if(status!=200){return;}
        Baza res=(Baza)resp.readEntity(Baza.class);
        printListInterop(toALIC(res.getMesta()));
        System.out.println();
        printListInterop(toALIC(res.getFilijale()));
        System.out.println();
        printListInterop(toALIC(res.getKomitenti()));
        System.out.println();
        printListInterop(toALIC(res.getRacuni()));
        System.out.println();
        printListInterop(toALIC(res.getTransakcije()));
    }

    private static void getBackupDiff() {
        Response resp=client.target(url+"/backup/diff").request(MediaType.APPLICATION_JSON).get();
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
        if(status!=200){return;}
        Baza res=(Baza)resp.readEntity(Baza.class);
        printDiff(toALIC(res.getMesta()),"SUBSYSTEM1");
        printDiff(toALIC(res.getFilijale()),"SUBSYSTEM1");
        printThreeWayDiff(toALIC(res.getKomitenti()));
        printDiff(toALIC(res.getRacuni()),"SUBSYSTEM2");
        printDiff(toALIC(res.getTransakcije()),"SUBSYSTEM2");
        System.out.println("END DIFF");
    }
    
    private static ArrayList<InteropClass> toALIC(ArrayList al)
    {
        ArrayList<InteropClass> ALIC=new ArrayList<>();
        for (Object object : al) {
            ALIC.add((InteropClass)object);
        }
        return ALIC;
    }

    private static void printDiff(ArrayList<InteropClass> list,String other) {
        for (int i = 0; i < list.size(); i+=2) {
            InteropClass s3=list.get(i);
            InteropClass so=list.get(i+1);
            if(s3==null)
            {
                System.out.println(other+" ONLY "+ICtoString(so));
            }
            else if(so==null)
            {
                System.out.println("BACKUP     ONLY "+ICtoString(s3));
            }
            else
            {
                System.out.println();
                System.out.println("BACKUP     DIFF "+ICtoString(s3));
                System.out.println(other+" DIFF "+ICtoString(so));
                System.out.println();
            }
        }
    }
    
    private static String ICtoString(InteropClass obj)
    {
        String ret=obj.getClass().getSimpleName()+": ";
        for (int i = 0; i < obj.numcol(); i++) {
            ret+=obj.colnames()[i]+"="+obj.colvals()[i];
            if(i!=obj.numcol()-1)
            {
            ret+=", ";
            }
        }
        return ret;
    }

    private static void printThreeWayDiff(ArrayList<InteropClass> list) {
        for (int i = 0; i < list.size(); i+=3) {
            InteropClass s3=list.get(i);
            InteropClass s2=list.get(i+1);
            InteropClass s1=list.get(i+2);
            if(s3==null || s2==null || s1==null)
            {
                String out="";
                InteropClass rep=s3;
                if(s3==null)
                {
                    out+="BACKUP";
                }
                else
                {
                    rep=s3;
                }
                if(s2==null)
                {
                    if(out!=""){out+=" AND ";}
                    out+="SUBSYSTEM2";
                }
                else
                {
                    rep=s2;
                }
                if(s1==null)
                {
                    if(out!=""){out+=" AND ";}
                    out+="SUBSYSTEM3";
                }
                else
                {
                    rep=s1;
                }
                
                System.out.println(out+" ONLY "+ICtoString(rep));
            }
            else
            {
                System.out.println();
                System.out.println("BACKUP     DIFF "+ICtoString(s3));
                System.out.println("SUBSYSTEM2 DIFF "+ICtoString(s2));
                System.out.println("SUBSYSTEM1 DIFF "+ICtoString(s1));
                System.out.println();
            }
        }
    }

    private static void patch(String path, int id, int mesto) {
        Komitent k = new Komitent();
        k.setiDMestaSedista(mesto);
        Response resp=client.target(url).path(path).resolveTemplate("id", id).request().post(Entity.entity(k, MediaType.APPLICATION_JSON));
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
    }

    private static void delete(String path, int id) {
        Response resp=client.target(url).path(path).resolveTemplate("id", id).request().delete();
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
    }

    private static void postMesto() throws IOException {
        System.out.println("Naziv:");
        String naziv=in.readLine();
        System.out.println("PostanskiBroj:");
        int postanskiBroj=Integer.parseInt(in.readLine());
        Mesto m = new Mesto(null,naziv,postanskiBroj);
        Response resp=client.target(url).path("/mesta").request().post(Entity.entity(m, MediaType.APPLICATION_JSON));
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
    }

    private static void postFilijala() throws IOException {
        System.out.println("Naziv:");
        String naziv=in.readLine();
        System.out.println("Adresa:");
        String adresa=in.readLine();
        System.out.println("IdMesta:");
        int idmesta=Integer.parseInt(in.readLine());
        Filijala f = new Filijala(null,naziv,adresa,idmesta);
        Response resp=client.target(url).path("/filijale").request().post(Entity.entity(f, MediaType.APPLICATION_JSON));
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
    }

    private static void postKomitent() throws IOException {
         System.out.println("Naziv:");
        String naziv=in.readLine();
        System.out.println("Adresa:");
        String adresa=in.readLine();
        System.out.println("IdMestaSedista:");
        int idmesta=Integer.parseInt(in.readLine());
        Komitent k = new Komitent(null,naziv,adresa,idmesta);
        Response resp=client.target(url).path("/komitenti").request().post(Entity.entity(k, MediaType.APPLICATION_JSON));
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
    }

    private static void postPrenos() throws IOException {
        System.out.println("IdRacunaSa:");
        int idsrc=Integer.parseInt(in.readLine());
        System.out.println("IdRacunaNa:");
        int iddst=Integer.parseInt(in.readLine());
        System.out.println("Iznos:");
        long iznos=Long.parseLong(in.readLine());
        System.out.println("Svrha:");
        String svrha=in.readLine();
        Transakcija t = new Transakcija(null,iznos,null,null,null,svrha,null,iddst,idsrc,true);
        Response resp=client.target(url).path("/transakcije").request().post(Entity.entity(t, MediaType.APPLICATION_JSON));
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
    }

    private static void postUplata() throws IOException {
        System.out.println("IdRacunaNa:");
        int iddst=Integer.parseInt(in.readLine());
        System.out.println("Iznos:");
        long iznos=Long.parseLong(in.readLine());
        System.out.println("Svrha:");
        String svrha=in.readLine();
        System.out.println("IdFilijale:");
        int idfil=Integer.parseInt(in.readLine());
        Transakcija t = new Transakcija(null,iznos,null,null,null,svrha,idfil,iddst,null,true);
        Response resp=client.target(url).path("/transakcije").request().post(Entity.entity(t, MediaType.APPLICATION_JSON));
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
    }

    private static void postIsplata() throws IOException {
        System.out.println("IdRacunaSa:");
        int idsrc=Integer.parseInt(in.readLine());
        System.out.println("Iznos:");
        long iznos=Long.parseLong(in.readLine());
        System.out.println("Svrha:");
        String svrha=in.readLine();
        System.out.println("IdFilijale:");
        int idfil=Integer.parseInt(in.readLine());
        Transakcija t = new Transakcija(null,iznos,null,null,null,svrha,idfil,null,idsrc,true);
        Response resp=client.target(url).path("/transakcije").request().post(Entity.entity(t, MediaType.APPLICATION_JSON));
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
    }

    private static void postRacun() throws IOException {
        System.out.println("IdKomitenta:");
        int idk=Integer.parseInt(in.readLine());
        System.out.println("IdMestaOtvaranja:");
        int idfil=Integer.parseInt(in.readLine());
        System.out.println("DozvoljeniMinus:");
        long minus=Long.parseLong(in.readLine());
        Racun r = new Racun(null,idk,idfil,null,minus,null,null,null);
        Response resp=client.target(url).path("/racuni").request().post(Entity.entity(r, MediaType.APPLICATION_JSON));
        int status =resp.getStatus();
        System.out.println("Response: STATUS:"+status+" "+resp.getStatusInfo().getReasonPhrase());
    }
}
