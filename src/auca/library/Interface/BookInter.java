/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.Interface;

import auca.library.model.BookView;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Nishimwe Elysee
 */
public interface BookInter extends Remote{
    public void saveBook(BookView b) throws RemoteException;
    public void updateBook(BookView b) throws RemoteException;
    public void deleteBook(BookView b) throws RemoteException;
    public List<BookView> getBookData() throws RemoteException;
    public BookView findById(String id) throws RemoteException;
    public List<BookView> findByTitle(String title) throws RemoteException;
    public String getBookId(String title) throws RemoteException;
    public String getId(String className, String extcol, String col, String newid) throws RemoteException;
}
