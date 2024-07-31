package dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.Borrow_ReturnDetailDao;
import entity.Borrow_ReturnDetailEntity;

public class Borrow_ReturnDetailDaoImpl implements Borrow_ReturnDetailDao {

    @Override
    public boolean create(Borrow_ReturnDetailEntity t) throws Exception {
        return CrudUtil.executeUpdte("INSERT INTO borrow_returndetail VALUES(?,?,?,?,?,?,?)", t.getBorrowId(),t.getBookId(),t.getBorrowCondition(),t.getReturnCondition(),t.getReturnDate(),t.getFines(),t.getFinedReason());
    }

    @Override
    public boolean update(Borrow_ReturnDetailEntity t) throws Exception {
        return CrudUtil.executeUpdte("UPDATE borrow_returndetail SET Return_Condition = ?, Return_Date = ?, Fine = ?, Fine_Reason = ? WHERE BorrowID = ? AND BookID = ?", t.getReturnCondition(),t.getReturnDate(),t.getFines(),t.getFinedReason(),t.getBorrowId(),t.getBookId());
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public Borrow_ReturnDetailEntity get(String id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Borrow_ReturnDetailEntity> getAll() throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM borrow_returndetail");
        ArrayList<Borrow_ReturnDetailEntity> detailEntities = new ArrayList<>();
        while (resultSet.next()) {
            detailEntities.add(new Borrow_ReturnDetailEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), Double.valueOf(resultSet.getString(6)), resultSet.getString(7)));
        }
        return detailEntities;
    }
    
}
