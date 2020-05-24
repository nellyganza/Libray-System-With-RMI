/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.Servers;

import auca.library.Interface.MethodsInter;
import auca.library.model.CheckinoutView;
import auca.library.model.ClientView;
import auca.library.util.HibernateUtil;
import java.nio.charset.StandardCharsets;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Base64;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

/**
 *
 * @author NISHIMWE Elyse
 */
public class Methods extends UnicastRemoteObject implements MethodsInter{

    private Session session = null;
    public Methods() throws RemoteException {
        super();
    }
    public static void main(String[] args) throws RemoteException{
        try {
            Registry reg = LocateRegistry.createRegistry(2005);
            reg.rebind("ServerMethods",new Methods());
            System.out.println("Other Methods  Server is Running now .....");
        } catch (AccessException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    @Override
    public String encoding(String password){
        Base64.Encoder en = Base64.getEncoder();
        return en.encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }
    @Override
    public String deencoding(String password){
        Base64.Decoder en = Base64.getDecoder();
        return new String(en.decode(password)); 
    }

 
    @Override
    public List<Object> getBookCat() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select categoryname from Bookcategory");
        List<Object> catnames = q.list();
        tx.commit();
        session.close();
        return catnames;
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
    @Override
    public String getRegNo(String name) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select regno from ClientView where FirstName||' '||LastName = '"+name+"'");
        Object names = q.uniqueResult();
        tx.commit();
        session.close();
        return names.toString();
    }
    @Override
    public String getName(String names,String ClassName,String existencecol,String findcol){
        String name = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select "+names+" from "+ClassName+" where "+existencecol+" = '"+findcol+"'");
        List<String> list = q.list();
        tx.commit();
        session.close();
        for(String s:list)
            name=s;
        return name;
    }
    @Override
        public Object getNameOfClient(String id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select FirstName||'  '||LastName as Name from ClientView where regno = '"+id+"'");
        Object catnames = q.uniqueResult();
        tx.commit();
        session.close();
        return catnames;
    }  
    @Override
    public void increseOrDecrese(String bookid,String operationcategory){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q;
        if(operationcategory.equals("CHECK OUT")){
            q = session.createQuery("update BookView set numberofcopies = numberofcopies-1 where bookid = '"+bookid+"'");
        }
        else
        {
            q = session.createQuery("update BookView set numberofcopies = numberofcopies+1 where bookid = '"+bookid+"'");
        }
        q.executeUpdate();
        tx.commit();
        session.close();
    }
    @Override
    public Integer getNUmberofCopiesRem(String bookid){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select numberofcopies from BookView  where bookid = '"+bookid+"'");
        List<Integer> num = q.list();
        tx.commit();
        session.close();
        Integer n = null;
        for(Integer i:num)
            n=i;
        return n;
    }
    @Override
    public List<Object> getBookIntoCmb() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select title from Book");
        List<Object> catnames = q.list();
        tx.commit();
        session.close();
        return catnames;
    }
    @Override
    public List<Object> getNameIntoCmb() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select FirstName||'  '||LastName as Name from Client");
        List<Object> catnames = q.list();
        tx.commit();
        session.close();
        return catnames;
    }  
    
    @Override
    public List<ClientView> getBookIntoCmbIn(String id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("select title from Book where bookid in (select bookid from Checkinout where regno = '"+id+"' and status = 'Given')");
        List<ClientView> catnames = q.list();
        tx.commit();
        session.close();
        System.out.println(catnames);
        return catnames;
    }
    @Override
    public List<Object> getNameIntoCmbIn() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("select FirstName||'  '||LastName as Name from Client where regno in (select regno from Checkinout where status like 'Given')");
        List<Object> catnames = q.list();
        tx.commit();
        session.close();
        return catnames;
    }
    @Override
    public boolean checkIfHaveBook(String cid,String bid){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx= session.beginTransaction();
        Query c = session.createSQLQuery("select status from checkinoutView where bookview_bookid='"+bid+"' and clientview_regno = '"+cid+"' and operationcategory = 'CHECK OUT' and status = 'have' and rownum=1");
        Object status = c.uniqueResult();
        tx.commit();
        session.close();
        if(status!=null)
            return status.equals("have");
        else
            return false;
    }
    @Override
    public String getOperationId(String date){
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria crt = session.createCriteria(CheckinoutView.class);
        Integer totalResult = ((Number) crt.setProjection(Projections.rowCount()).uniqueResult()).intValue()+1;
        session.close();
        String opid = "OP"+totalResult+date;
        System.out.println(opid);
        return opid;
    }
}
