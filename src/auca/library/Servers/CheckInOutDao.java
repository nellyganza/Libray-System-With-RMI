/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.Servers;

import auca.library.Interface.CheckInOutInter;
import auca.library.model.CheckinoutView;
import auca.library.util.HibernateUtil;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
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
public class CheckInOutDao extends UnicastRemoteObject implements CheckInOutInter{
      static Session session = null;
      
      public CheckInOutDao() throws RemoteException {
        super();
    }
    public static void main(String[] args) throws RemoteException{
        try {
            Registry reg = LocateRegistry.createRegistry(2003);
            reg.rebind("ServerCheckInOut",new CheckInOutDao());
            System.out.println("CheckInOut Server is Running now .....");
        } catch (AccessException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
      @Override
    public void saveCheckInOut(CheckinoutView b) {
          
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(b);
        tx.commit();
        session.close();
    }
      @Override
    public void updateCheckInOut(CheckinoutView b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(b);
        tx.commit();
        session.close();
    }
      @Override
    public void deleteCheckInOut(CheckinoutView b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(b);
        tx.commit();
        session.close();
    }
      @Override
    public List<CheckinoutView> getCheckInOut() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Criteria crt = session.createCriteria(CheckinoutView.class);
        List<CheckinoutView> book = crt.list();
        tx.commit();
        session.close();
        return book;
    }    
      @Override
    public CheckinoutView findById(String enity, String id){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        CheckinoutView b = (CheckinoutView) session.get(enity, id);
        tx.commit();
        session.close();
        return b;
    }
    
      @Override
    public String getOperationNumber(String cid,String bid){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx= session.beginTransaction();
        Query c = session.createSQLQuery("select opnumber from checkinoutView where bookView_bookid='"+bid+"' and clientView_regno = '"+cid+"' and operationcategory = 'CHECK OUT' and status = 'have' and rownum=1");
        Object opnumber = c.uniqueResult();
        System.out.println(opnumber);
        tx.commit();
        session.close();
        return opnumber.toString();
    }
    
      @Override
    public void updateOperation(String cid,String bid){
        String opnum = getOperationNumber(cid,bid);
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("update checkinoutView set status = 'returned' where opnumber = '"+opnum+"'");
        q.executeUpdate();
        tx.commit();
        session.close();
    }
      @Override
    public List<CheckinoutView> getReportOfData(String colname,String id,String opcat){
      List<CheckinoutView> data = new ArrayList<>();
       try{ 
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from CheckinoutView where "+colname+" = '"+id+"' and operationcategory = '"+opcat+"'");
        data = q.list();
        tx.commit();
        session.close();
    }catch(HibernateException ex){
           JOptionPane.showMessageDialog(null, ex.getMessage());
    }
        return data;
    }
      @Override
    public List<CheckinoutView> getReportOfDataDate(Date date1,Date date2,String opcat){
        List<CheckinoutView> data = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Criteria c = session.createCriteria(CheckinoutView.class);
        c.add(Restrictions.between("datetime", date1, date2));
        c.add(Restrictions.eq("operationcategory", opcat));
        data = c.list();
        tx.commit();
        session.close();
        return data;
    }
      @Override
    public List<Object[]> getReportOfDataCategory(String cat,String opcat){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("select checkinoutview.opnumber,checkinoutview.clientView_regno,checkinoutview.bookView_bookid,checkinoutview.datetime,checkinoutview.operationcategory,checkinoutview.status from checkinoutview  join bookview  on checkinoutview.bookView_bookid=bookview.bookid join bookcategoryview  on bookview.bookcategoryView_categoryid=bookcategoryview.categoryid  where categoryname = '"+cat+"' and operationcategory= '"+opcat+"'");
        List<Object[]> data = q.list();
        tx.commit();
        session.close();
        return data;
    }
}
