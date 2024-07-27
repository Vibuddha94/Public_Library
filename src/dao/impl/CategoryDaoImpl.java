package dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.CategoryDao;
import entity.CategoryEntity;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public boolean create(CategoryEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean update(CategoryEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public CategoryEntity get(String id) throws Exception {
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
