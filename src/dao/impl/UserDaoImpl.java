package dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.UserDao;
import entity.UserEntity;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean create(UserEntity t) throws Exception {
        return CrudUtil.executeUpdte("INSERT INTO user VALUES(?,?,?,?,?,?,?,?)", t.getUserId(),t.getFirstName(),t.getLastName(),t.getDob(),t.getAddress(),t.getContactNumber(),t.getPassword(),t.getIsAdmin());
    }

    @Override
    public boolean update(UserEntity t) throws Exception {
        return CrudUtil.executeUpdte("UPDATE user SET F_Name = ?, L_Name = ?, DOB = ?, Address = ?, Contact_Number = ?, Password = ?, IsAdmin = ? WHERE UserID = ?", t.getFirstName(),t.getLastName(),t.getDob(),t.getAddress(),t.getContactNumber(),t.getPassword(),t.getIsAdmin(),t.getUserId());
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.executeUpdte("DELETE FROM user WHERE UserID = ?", id);
    }

    @Override
    public UserEntity get(String id) throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECET*FROM user WHERE UserID=?", id);
        if (resultSet.next()) {
            UserEntity userEntity = new UserEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getBoolean(8));
            return userEntity;
        }
        return null;
    }

    @Override
    public ArrayList<UserEntity> getAll() throws Exception {
        ArrayList<UserEntity> userEntities = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM user");
        while (resultSet.next()) {
            UserEntity userEntity = new UserEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getBoolean(8));
            userEntities.add(userEntity);
        }
        return userEntities;
    }
    
}
