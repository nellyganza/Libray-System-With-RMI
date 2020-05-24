/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.Interface;

import auca.library.model.CheckinoutView;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nishimwe Elysee
 */
public interface CheckInOutInter extends Remote{
    public void saveCheckInOut(CheckinoutView b) throws RemoteException;
    public void updateCheckInOut(CheckinoutView b) throws RemoteException;
    public void deleteCheckInOut(CheckinoutView b) throws RemoteException;
    public List<CheckinoutView> getCheckInOut() throws RemoteException;
    public CheckinoutView findById(String enity, String id) throws RemoteException;
    public String getOperationNumber(String cid,String bid) throws RemoteException;
    public void updateOperation(String cid,String bid) throws RemoteException;
    public List<CheckinoutView> getReportOfData(String colname,String id,String opcat) throws RemoteException;
    public List<CheckinoutView> getReportOfDataDate(Date date1,Date date2,String opcat) throws RemoteException;
    public List<Object[]> getReportOfDataCategory(String cat,String opcat) throws RemoteException;
}
