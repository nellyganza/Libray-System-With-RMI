/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.Servers;

import auca.library.Interface.ClientInter;
import auca.library.model.ClientView;
import auca.library.util.HibernateUtil;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author NISHIMWE Elyse
 */
public class ClientDao extends UnicastRemoteObject implements ClientInter{
     public ClientDao() throws RemoteException {
        super();
    }
    public static void main(String[] args) throws RemoteException{
        try {
            Registry reg = LocateRegistry.createRegistry(2004);
            reg.rebind("ServerClient",new ClientDao());
            System.out.println("Client Server is Running now .....");
        } catch (AccessException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    static Session session = null;
     @Override
    public void saveClient(ClientView b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(b);
        tx.commit();
        session.close();
    }
     @Override
    public void updateClient(ClientView b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(b);
        tx.commit();
        session.close();
    }
     @Override
    public void deleteClient(ClientView b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(b);
        tx.commit();
        session.close();
    }
     @Override
    public List<ClientView> getClient() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Criteria crt = session.createCriteria(ClientView.class);
        List<ClientView> book = crt.list();
        tx.commit();
        session.close();
        return book;
    }    
     @Override
    public ClientView findById(String id){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        ClientView c = (ClientView) session.get(ClientView.class, id);
        tx.commit();
        session.close();
        return c;
    }
     @Override
    public List<ClientView> findByName(String name){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from ClientView where FirstName||' '||LastName = '"+name+"'");
        List<ClientView> c = q.list();
        tx.commit();
        session.close();
        return c;
    }
}
