package JMSMessenger;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.jms.JMSConsumer;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Destination;

public class JMSMessenger implements Runnable{

    private final Lock lock = new ReentrantLock();
    private final Condition recieveLock  = lock.newCondition(); 
    private final Condition sendRecieveLock = lock.newCondition(); 
    private final Condition sendLock = lock.newCondition(); 
    private ArrayList<Message> incoming;
    private ArrayList<Message> outgoing;
    private Destination dst;
    private JMSProducer prod;
    private JMSConsumer cons;
    private int pendingReads;
    private boolean running;
    private Thread thisT;
    
    public JMSMessenger(Destination dst,JMSProducer prod,JMSConsumer cons)
    {
        incoming=new ArrayList<>();
        outgoing=new ArrayList<>();
        this.dst=dst;
        this.prod=prod;
        this.cons=cons;
        pendingReads=0;
        thisT=new Thread(this);
        running=false;
    }
    
    public void Start()
    {
        lock.lock();
        running=true;
        thisT.start();
        lock.unlock();
    }
    
    public void Stop()
    {
        lock.lock();
        running=false;    
        sendRecieveLock.signalAll();
        try{
            thisT.join();
        }catch (Exception e){e.printStackTrace();}
        lock.unlock();
    }
    
    @Override
    public void run() {
        while(running)
        {
            Message send=getMessageToSend();
            if(send!=null)
            {
                prod.send(dst, send);
            }
            Message recieve =cons.receiveNoWait();
            if(recieve!=null)
            {
                putRecievedMessage(recieve);
            }
            if(send==null && recieve==null && pendingReads==0)
            {
                block();
            }
        }
    }
    
    public void send(Message msg)
    {
        lock.lock();
        outgoing.add(msg);
        sendRecieveLock.signalAll();
        lock.unlock();
    }
    
    public Message recieve(int timeout)
    {
        lock.lock();
        pendingReads++;
        int waittime=0;
        try{
            while(incoming.isEmpty()){
                sendRecieveLock.signalAll();
                recieveLock.await(1000, TimeUnit.MILLISECONDS);
                waittime++;
                if(waittime==timeout){
                    lock.unlock();
                    return null;
                }
            }
        }catch (Exception e){e.printStackTrace();}
        Message ret=incoming.get(0);
        incoming.remove(0);
        pendingReads--;
        lock.unlock();
        return ret;
    }
    
    public Message filteredRecieve(String proprety,int value,int timeout)
    {
        lock.lock();
        pendingReads++;
        int waittime=0;
        Message ret=null;
        try{
            while(ret==null){
                for (int i=0; i < incoming.size();i++) {
                    if(incoming.get(i).getIntProperty(proprety)==value)
                    {
                        ret=incoming.get(i);
                        incoming.remove(i);
                        break;
                    }
                }
                if(ret==null)
                {
                    sendRecieveLock.signalAll();
                    recieveLock.await(1000, TimeUnit.MILLISECONDS);
                    waittime++;
                    if(waittime==timeout){
                        lock.unlock();
                        return null;
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
        pendingReads--;
        lock.unlock();
        return ret;
    }
    
    private Message getMessageToSend()
    {
        lock.lock();
        if(outgoing.isEmpty()){lock.unlock();return null;}
        Message ret=outgoing.get(0);
        outgoing.remove(0);
        lock.unlock();
        return ret;
    }
    
    private void putRecievedMessage(Message msg)
    {
        lock.lock();
        incoming.add(msg);
        recieveLock.signalAll();
        lock.unlock();
    }
    
    private void block()
    {
        lock.lock();
        try{
            sendRecieveLock.await(1000, TimeUnit.MILLISECONDS);
        }catch (Exception e){e.printStackTrace();}
        lock.unlock();
    }
    
}
