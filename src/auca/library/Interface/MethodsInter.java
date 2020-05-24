/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.Interface;

import auca.library.model.ClientView;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Nishimwe Elysee
 */
public interface MethodsInter extends Remote{
    public String encoding(String password) throws RemoteException;
    public String deencoding(String password) throws RemoteException;
    public List<Object> getBookCat() throws RemoteException;
    public String getId(String className, String extcol, String col, String newid) throws RemoteException;
    public String getRegNo(String name) throws RemoteException;
    public String getName(String names,String ClassName,String existencecol,String findcol) throws RemoteException;
    public Object getNameOfClient(String id) throws RemoteException;
    public void increseOrDecrese(String bookid,String operationcategory) throws RemoteException;
    public Integer getNUmberofCopiesRem(String bookid) throws RemoteException;
    public List<Object> getBookIntoCmb() throws RemoteException;
    public List<Object> getNameIntoCmb() throws RemoteException;
    public List<ClientView> getBookIntoCmbIn(String id) throws RemoteException;
    public List<Object> getNameIntoCmbIn() throws RemoteException;
    public boolean checkIfHaveBook(String cid,String bid) throws RemoteException;
    public String getOperationId(String date) throws RemoteException;
}
