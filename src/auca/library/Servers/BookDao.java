/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.Servers;

import auca.library.Interface.BookInter;
import auca.library.model.BookView;
import auca.library.model.BookcategoryView;
import auca.library.util.HibernateUtil;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author NISHIMWE Elyse
 */
public class BookDao extends UnicastRemoteObject implements BookInter{
    static Session session = null;
    public BookDao() throws RemoteException {
        super();
    }
    public static void main(String[] args) throws RemoteException{
        try {
            Registry reg = LocateRegistry.createRegistry(2002);
            reg.rebind("ServerBook",new BookDao());
            System.out.println("Book Server is Running now .....");
        } catch (AccessException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    @Override
    public void saveBook(BookView b) {
    try{  
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(b);
        tx.commit();
        session.close();
    }catch(HibernateException ex){
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
    JOptionPane.showMessageDialog(null, "Data Saved SuccessFully !!!");
    }
    @Override
    public void updateBook(BookView b) {
    try{    
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(b);
        tx.commit();
        session.close();
        }catch(HibernateException ex){
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
        JOptionPane.showMessageDialog(null, "Data Updated SuccessFully !!!");
    }
    @Override
    public void deleteBook(BookView b) {
    try{    session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(b);
        tx.commit();
        session.close();
        }catch(HibernateException ex){
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
    JOptionPane.showMessageDialog(null, "Data Deleted SuccessFully !!!");
    }
    @Override
   public List<BookView> getBookData(){
        List<BookView> books = new ArrayList<>();
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from BookView");
        books = q.list();
        tx.commit();
        session.close();
        return books;
    } 
    @Override
    public BookView findById(String id){
        BookView b = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            b = (BookView) session.get(BookView.class, id);
            tx.commit();
            session.close();
            }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return b;
    }
    @Override
    public List<BookView> findByTitle(String title)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Criteria crt = session.createCriteria(BookView.class);
        crt.add(Restrictions.eq("title", title));
        List<BookView> b = crt.list();
        tx.commit();
        session.close();
        return b;
    }
    @Override
    public String getBookId(String title){
      session  = HibernateUtil.getSessionFactory().openSession();
      Transaction tx = session.beginTransaction();
        Criteria crt = session.createCriteria(BookcategoryView.class);
        crt.add(Restrictions.like("categoryname", title));
         List bcat =  crt.list();
         String bc = null;
         for(Iterator it = bcat.iterator();it.hasNext();){
             bc = it.next().toString();
         }
        tx.commit();
        session.close();
        return bc;
    }
    @Override
    public String getId(String className, String extcol, String col, String newid) {
        String id = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select " + newid + " from " + className + " where " + extcol + "='" + col + "'");
        List<String> list = q.list();
        tx.commit();
        session.close();
        for(String l:list)
            id=l;
        return id;
    }
}
