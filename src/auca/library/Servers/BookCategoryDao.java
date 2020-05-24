/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.Servers;
import auca.library.Interface.BookCategoryInter;
import auca.library.model.BookcategoryView;
import auca.library.util.HibernateUtil;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author NISHIMWE Elyse
 */
public class BookCategoryDao extends UnicastRemoteObject implements BookCategoryInter{
    static Session session = null;
    
    public BookCategoryDao() throws RemoteException {
        super();
    }
    public static void main(String[] args) throws RemoteException{
        try {
            Registry reg = LocateRegistry.createRegistry(2001);
            reg.rebind("ServerBookCategory",new BookCategoryDao());
            System.out.println("BookCategory Server is Running now .....");
        } catch (AccessException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    @Override
    public void saveBookcategoryView(BookcategoryView bc) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(bc);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Data Saved SuccessFully !!!");
    }
    @Override
    public void updateBookcategoryView(BookcategoryView bc) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(bc);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Data Updated SuccessFully !!!");
    }
    @Override
    public void deleteBookcategoryView(BookcategoryView bc) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(bc);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Data Deleted SuccessFully !!!");
    }
    @Override
    public List<BookcategoryView> getBookcategoryView() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from BookcategoryView");
        List<BookcategoryView> bookcat = q.list();
        tx.commit();
        session.close();
        return bookcat;
    }    
    @Override
    public List<BookcategoryView> getDataIntoBookViewCatCmb(){
      session  = HibernateUtil.getSessionFactory().openSession();
      Transaction tx = session.beginTransaction();
        Criteria crt = session.createCriteria(BookcategoryView.class);
        ProjectionList plist =  Projections.projectionList();
        plist.add(Projections.property("categoryname"));
        crt.setProjection(plist);
        List<BookcategoryView> catnames = crt.list();
        return catnames;
    }
    @Override
    public BookcategoryView findById(String id){
        BookcategoryView b = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            b = (BookcategoryView) session.get(BookcategoryView.class, id);
            tx.commit();
            session.close();
            }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return b;
    }
    @Override
    public List<BookcategoryView> findByTitle(String title)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Criteria crt = session.createCriteria(BookcategoryView.class);
        crt.add(Restrictions.eq("categoryname", title));
        List<BookcategoryView> b = crt.list();
        tx.commit();
        session.close();
        return b;
    }
}
