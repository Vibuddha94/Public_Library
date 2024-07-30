package dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.HistoryDao;
import entity.HistoryEntity;

public class HistoryDaoImpl implements HistoryDao {

    @Override
    public ArrayList<HistoryEntity> getMemberHistory(String memId) throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT br.BookID,b.Borrow_Date,br.Return_Date,br.Borrow_Condition,br.Return_Condition,br.Fine,br.Fine_Reason FROM Borrowing b INNER JOIN Borrow_returndetail br ON b.BorrowID = br.BorrowID WHERE b.MemberID = ?", memId);
        ArrayList<HistoryEntity> historyEntities = new ArrayList<>();
        while (resultSet.next()) {
            historyEntities.add(new HistoryEntity(resultSet.getString(1), null,resultSet.getString(2) , resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), Double.valueOf(resultSet.getString(6)), resultSet.getString(7)));
        }
        return historyEntities;
    }

    @Override
    public ArrayList<HistoryEntity> getBookHistory(String bookId) throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT b.MemberID,b.Borrow_Date,br.Return_Date,br.Borrow_Condition,br.Return_Condition,br.Fine,br.Fine_Reason FROM Borrowing b INNER JOIN Borrow_returndetail br ON b.BorrowID = br.BorrowID WHERE br.BookID = ?", bookId);
        ArrayList<HistoryEntity> historyEntities = new ArrayList<>();
        while (resultSet.next()) {
            historyEntities.add(new HistoryEntity(null, resultSet.getString(1),resultSet.getString(2) , resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), Double.valueOf(resultSet.getString(6)), resultSet.getString(7)));
        }
        return historyEntities;
    }
    
}
