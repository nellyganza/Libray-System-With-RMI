/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.Servers;

import auca.library.Interface.AccountInter;
import auca.library.util.HibernateUtil;
import auca.library.model.Account;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author NISHIMWE Elyse
 */
public class AccountDao extends UnicastRemoteObject implements AccountInter{
    public AccountDao() throws RemoteException {
        super();
    }
    public static void main(String[] args) throws RemoteException{
        try {
            Registry reg = LocateRegistry.createRegistry(2000);
            reg.rebind("ServerAccount",new AccountDao());
            System.out.println("Account Server is Running now .....");
        } catch (AccessException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    
    
    Session session = null;
    @Override
    public void createAccount(Account acc){
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(acc);
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    @Override
    public void updateAccount(Account acc){
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
                session.update(acc);
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    @Override
     public void changePassword(String  username,String newpass){
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
                session.createQuery("update Account set password = '"+newpass+"' where username = '"+username+"'").executeUpdate();
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    @Override
    public void deleteAccount(Account acc){
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(acc);
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    @Override
    public List<Account> displayAccount(){
        List<Account> accounts =null;
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            accounts = session.createCriteria(Account.class).list();
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return accounts;
    }
    @Override
    public List<Account> findByEmail(String email){
        List<Account> accounts =null;
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            accounts = session.createCriteria(Account.class).add(Restrictions.eq("email", email)).list();
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return accounts;
    }
    @Override
    public String findByUsername(String username){
        String password = null;
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            List accounts = session.createCriteria(Account.class).add(Restrictions.eq("username", username)).setProjection(Projections.property("password")).list();
            tx.commit();
            for(Object a:accounts){
                password = a.toString();
            }
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return password;
    }
    @Override
    public List<Account> findByPhoneNumber(String phonenumber){
        List<Account> accounts =null;
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            accounts =  session.createCriteria(Account.class).add(Restrictions.eq("phonenumber", phonenumber)).list();
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return accounts;
    }
}
