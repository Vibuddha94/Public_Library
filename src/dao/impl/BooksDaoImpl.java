package dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.BooksDao;
import entity.BooksEntity;

public class BooksDaoImpl implements BooksDao {

    @Override
    public boolean create(BooksEntity t) throws Exception {
        return CrudUtil.executeUpdte("INSERT INTO books VALUES (?,?,?,?,?,?,?)", t.getBookId(),t.getCatCode(),t.getName(),t.getStatus(),t.getCondition(),t.getAuthor(),t.getPrice());
    }

    @Override
    public boolean update(BooksEntity t) throws Exception {
        return CrudUtil.executeUpdte("UPDATE books SET CatCode = ?, Name = ?, Status = ?, Book_Condition = ?, Author = ?, BookPrice = ? WHERE BookID = ?", t.getCatCode(),t.getName(),t.getStatus(),t.getCondition(),t.getAuthor(),t.getPrice(),t.getBookId());
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.executeUpdte("DELETE FROM books WHERE BookID = ?", id);
    }

    @Override
    public BooksEntity get(String id) throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM books WHERE BookID = ?", id);
        if (resultSet.next()) {
            return new BooksEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDouble(7));
        }
        return null;
    }

    @Override
    public ArrayList<BooksEntity> getAll() throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM books");
        ArrayList<BooksEntity> booksEntities = new ArrayList<>();
        while (resultSet.next()) {
            BooksEntity booksEntity = new BooksEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDouble(7));
            booksEntities.add(booksEntity);
        }
        return booksEntities;
    }
    
}
