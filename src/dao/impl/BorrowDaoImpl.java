package dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.BorrowDao;
import entity.BorrowEntity;

public class BorrowDaoImpl implements BorrowDao {

    @Override
    public boolean create(BorrowEntity t) throws Exception {
        return false;
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
        return null;
    }
    
}
