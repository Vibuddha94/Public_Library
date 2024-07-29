package dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.FinesDao;
import entity.FinesEntity;

public class FinesDaoImpl implements FinesDao {

    @Override
    public boolean create(FinesEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean update(FinesEntity t) throws Exception {
        return CrudUtil.executeUpdte("UPDATE fine SET Late = ?, Damage = ?, Lost = ? WHERE ID = 1", t.getLate(),t.getDamage(),t.getLost());
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return false;
    }

    @Override
    public FinesEntity get(Integer id) throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM fine WHERE ID = ?" , id);
        if (resultSet.next()) {
            return new FinesEntity(Integer.valueOf(resultSet.getString(1)), Double.valueOf(resultSet.getString(2)), Double.valueOf(resultSet.getString(3)), Double.valueOf(resultSet.getString(4)));
        }
        return null;
    }

    @Override
    public ArrayList<FinesEntity> getAll() throws Exception {
        return null;
    }
    
}
