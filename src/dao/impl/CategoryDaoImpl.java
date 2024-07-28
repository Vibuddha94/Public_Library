package dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.CategoryDao;
import entity.CategoryEntity;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public boolean create(CategoryEntity t) throws Exception {
        return CrudUtil.executeUpdte("INSERT INTO category VALUES(?,?)", t.getId(),t.getName());
    }

    @Override
    public boolean update(CategoryEntity t) throws Exception {
        return CrudUtil.executeUpdte("UPDATE category SET Category = ? WHERE CatCode = ?", t.getName(),t.getId());
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.executeUpdte("DELETE FROM category WHERE CatCode = ?", id);
    }

    @Override
    public CategoryEntity get(String id) throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM category WHERE CatCode = ?", id);
        if (resultSet.next()) {
            CategoryEntity categoryEntity = new CategoryEntity(resultSet.getString(1), resultSet.getString(2));
            return categoryEntity;
        }
        return null;
    }

    @Override
    public ArrayList<CategoryEntity> getAll() throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM category");
        ArrayList<CategoryEntity> categoryEntities = new ArrayList<>();
        while (resultSet.next()) {
            CategoryEntity categoryEntity = new CategoryEntity(resultSet.getString(1), resultSet.getString(2));
            categoryEntities.add(categoryEntity);
        }

        return categoryEntities;
    }

}
