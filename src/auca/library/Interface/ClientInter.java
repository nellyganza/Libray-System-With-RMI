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

/**
 *
 * @author Nishimwe Elysee
 */
public interface ClientInter extends Remote{
    public void saveClient(ClientView b) throws RemoteException;
    public void updateClient(ClientView b) throws RemoteException;
    public void deleteClient(ClientView b) throws RemoteException;
    public List<ClientView> getClient() throws RemoteException;
    public ClientView findById(String id) throws RemoteException;
    public List<ClientView> findByName(String name) throws RemoteException;
}
