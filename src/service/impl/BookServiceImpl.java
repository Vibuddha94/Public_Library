package service.impl;

import java.util.ArrayList;

import dao.DaoFactory;
import dao.DaoFactory.DaoType;
import dao.custom.BooksDao;
import dto.BooksDto;
import entity.BooksEntity;
import service.custom.BookService;

public class BookServiceImpl implements BookService {

    BooksDao booksDao = (BooksDao) DaoFactory.getInstance().getDao(DaoType.BOOK);

    @Override
    public String save(BooksDto booksDto) throws Exception {
        return booksDao.create(getEntity(booksDto)) ? "Success" : "Fail";
    }

    @Override
    public String update(BooksDto booksDto) throws Exception {
        return booksDao.update(getEntity(booksDto)) ? "Success" : "Fail";
    }

    @Override
    public String delete(String id) throws Exception {
        return booksDao.delete(id) ? "Success" : "Fail";
    }

    @Override
    public BooksDto get(String id) throws Exception {
        if (booksDao.get(id) != null) {
            return getDto(booksDao.get(id));
        }
        return null;
    }

    @Override
    public ArrayList<BooksDto> getAll() throws Exception {
        ArrayList<BooksEntity> booksEntities = booksDao.getAll();
        ArrayList<BooksDto> booksDtos = new ArrayList<>();
        for (BooksEntity booksEntity : booksEntities) {
            booksDtos.add(getDto(booksEntity));
        }
        return booksDtos;
    }

    private BooksDto getDto(BooksEntity booksEntity) {
        return new BooksDto(booksEntity.getBookId(), booksEntity.getCatCode(), booksEntity.getName(),
                booksEntity.getStatus(), booksEntity.getCondition(), booksEntity.getAuthor(), booksEntity.getPrice());
    }

    private BooksEntity getEntity(BooksDto booksDto) {
        return new BooksEntity(booksDto.getBookId(), booksDto.getCatCode(), booksDto.getName(), booksDto.getStatus(),
                booksDto.getCondition(), booksDto.getAuthor(), booksDto.getPrice());
    }

}
