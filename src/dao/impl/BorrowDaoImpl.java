package dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.BorrowDao;
import entity.BorrowEntity;

public class BorrowDaoImpl implements BorrowDao {

    @Override
    public boolean create(BorrowEntity t) throws Exception {
        return CrudUtil.executeUpdte("INSERT INTO borrowing VALUES(?,?,?)", t.getBorrowId(),t.getBorrowDate(),t.getMemberId());
    }

    @Override
    public boolean update(BorrowEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public BorrowEntity get(String id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<BorrowEntity> getAll() throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM borrowing");
        ArrayList<BorrowEntity> borrowEntities = new ArrayList<>();
        while (resultSet.next()) {
            borrowEntities.add(new BorrowEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
        }
        return borrowEntities;
    }
    
}
