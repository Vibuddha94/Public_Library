package dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.MemberDao;
import entity.MemberEntity;

public class MemberDaoImpl implements MemberDao {

    @Override
    public boolean create(MemberEntity t) throws Exception {
        return CrudUtil.executeUpdte("INSERT INTO member VALUES(?,?,?,?,?,?)", t.getMemberId(),t.getFirstName(),t.getLastName(),t.getDob(),t.getAddress(),t.getContNumber());
    }

    @Override
    public boolean update(MemberEntity t) throws Exception {
        return CrudUtil.executeUpdte("UPDATE member SET F_Name = ?, L_Name = ?, DOB = ?, Address = ?, Contact_Number = ? WHERE MemberID = ?", t.getFirstName(),t.getLastName(),t.getDob(),t.getAddress(),t.getContNumber(),t.getMemberId());
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.executeUpdte("DELETE FROM member WHERE MemberId = ?", id);
    }

    @Override
    public MemberEntity get(String id) throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM member WHERE MemberID = ?", id);
        if (resultSet.next()) {
            MemberEntity memberEntity = new MemberEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            return memberEntity;
        }
        return null;
    }

    @Override
    public ArrayList<MemberEntity> getAll() throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM member");
        ArrayList<MemberEntity> memberEntities = new ArrayList<>();
        while (resultSet.next()) {
            MemberEntity memberEntity = new MemberEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            memberEntities.add(memberEntity);
        }
        return memberEntities;
    }
    
}
