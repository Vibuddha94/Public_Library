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
        return false;
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
        return null;
    }
    
}
