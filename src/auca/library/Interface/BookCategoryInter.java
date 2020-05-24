/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.Interface;

import auca.library.model.BookcategoryView;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Nishimwe Elysee
 */
public interface BookCategoryInter extends Remote{
    public void saveBookcategoryView(BookcategoryView bc) throws RemoteException;
    public void updateBookcategoryView(BookcategoryView bc) throws RemoteException;
    public void deleteBookcategoryView(BookcategoryView bc) throws RemoteException;
    public List<BookcategoryView> getBookcategoryView() throws RemoteException;
    public List<BookcategoryView> getDataIntoBookViewCatCmb() throws RemoteException;
    public BookcategoryView findById(String id) throws RemoteException;
    public List<BookcategoryView> findByTitle(String title) throws RemoteException;
}
