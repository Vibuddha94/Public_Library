package service.impl;

import java.sql.Connection;
import java.util.ArrayList;

import dao.DaoFactory;
import dao.DaoFactory.DaoType;
import dao.custom.BooksDao;
import dao.custom.BorrowDao;
import dao.custom.Borrow_ReturnDetailDao;
import db.DBConnection;
import dto.BorrowDto;
import dto.Borrow_ReturnDetailDto;
import entity.BooksEntity;
import entity.BorrowEntity;
import entity.Borrow_ReturnDetailEntity;
import service.custom.BorrowService;

public class BorrowServiceImpl implements BorrowService {

    BorrowDao borrowDao = (BorrowDao) DaoFactory.getInstance().getDao(DaoType.BORROWING);
    Borrow_ReturnDetailDao borrow_ReturnDetailDao = (Borrow_ReturnDetailDao) DaoFactory.getInstance().getDao(DaoType.BORROW_RETURN);
    BooksDao booksDao = (BooksDao) DaoFactory.getInstance().getDao(DaoType.BOOK);

    @Override
    public String save(BorrowDto borrowDto) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
           connection.setAutoCommit(false); 
           BorrowEntity borrowEntity = new BorrowEntity(borrowDto.getBorrowId(), borrowDto.getBorrowDate(), borrowDto.getMemberId());
           if (borrowDao.create(borrowEntity)) {
            boolean isBorrowReturnDetailSaved = true;
            for (Borrow_ReturnDetailDto detailDto : borrowDto.getArrayList()) {
                if (!borrow_ReturnDetailDao.create(getDetailEntity(detailDto))) {
                    isBorrowReturnDetailSaved = false;
                }
            }
            if (isBorrowReturnDetailSaved) {
                boolean isBookUpdated = true;
                for (Borrow_ReturnDetailDto detailDto : borrowDto.getArrayList()) {
                    BooksEntity booksEntity = booksDao.get(detailDto.getBookId());
                    if (booksEntity != null) {
                        booksEntity.setStatus("Issued");
                        booksEntity.setCondition(detailDto.getBorrowCondition());
                        if (!booksDao.update(booksEntity)) {
                            isBookUpdated = false;
                        }
                    }
                }
                if (isBookUpdated) {
                    connection.commit();
                    return "Success";
                } else {
                    connection.rollback();
                    return "Error while updating book...";   
                }
            } else {
                connection.rollback();
                return "Error while saving borrow details...";                
            }
           } else {
            connection.rollback();
            return "Error while saving borrowing...";            
           }
        
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public String update(ArrayList<Borrow_ReturnDetailDto> arrayList) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false); 
            boolean isBorrowReturnDetailSaved = true;
            for (Borrow_ReturnDetailDto detailDto : arrayList) {
                if (!borrow_ReturnDetailDao.update(getDetailEntity(detailDto))) {
                    isBorrowReturnDetailSaved = false;
                }
            } 
            if (isBorrowReturnDetailSaved) {
                boolean isBookUpdated = true;
                for (Borrow_ReturnDetailDto detailDto : arrayList) {
                    BooksEntity booksEntity = booksDao.get(detailDto.getBookId());
                    booksEntity.setStatus(detailDto.getReturnCondition().equals("Lost") ? "Lost" : "Available");
                    booksEntity.setCondition(detailDto.getReturnCondition());
                    if (!booksDao.update(booksEntity)) {
                        isBookUpdated = false;
                    }
                }
                if (isBookUpdated) {
                    connection.commit();
                    return "Success";
                } else {
                    connection.rollback();
                    return "Error while updating book..."; 
                }
            } else{
                connection.rollback();
                return "Error while saving return...";  
            }
            
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public ArrayList<Borrow_ReturnDetailDto> getDetail(String borrowId) throws Exception {
        ArrayList<Borrow_ReturnDetailEntity> detailEntities = borrow_ReturnDetailDao.getAll();
        ArrayList<Borrow_ReturnDetailDto> detailDtos = new ArrayList<>();
        for (Borrow_ReturnDetailEntity detailEntity : detailEntities) {
            if (detailEntity.getBorrowId().equals(borrowId)) {
                detailDtos.add(getDetailDto(detailEntity));
            }
        }
        
        return detailDtos.isEmpty() ? null : detailDtos;
    }

    @Override
    public ArrayList<BorrowDto> getAllBorrowings() throws Exception {
        ArrayList<BorrowEntity> borrowEntities = borrowDao.getAll();
        ArrayList<BorrowDto> borrowDtos = new ArrayList<>();
        for (BorrowEntity borrowEntity : borrowEntities) {
            borrowDtos.add(new BorrowDto(borrowEntity.getBorrowId(), borrowEntity.getBorrowDate(), borrowEntity.getMemberId(), null));
        }
        return borrowDtos;
    }

    private Borrow_ReturnDetailEntity getDetailEntity(Borrow_ReturnDetailDto dto){
        return new Borrow_ReturnDetailEntity(dto.getBorrowId(), dto.getBookId(), dto.getBorrowCondition(), dto.getReturnCondition(), dto.getReturnDate(), dto.getFines(), dto.getFinedReason());
    }

    private Borrow_ReturnDetailDto getDetailDto(Borrow_ReturnDetailEntity detailEntity){
        return new Borrow_ReturnDetailDto(detailEntity.getBorrowId(), detailEntity.getBookId(), detailEntity.getBorrowCondition(), detailEntity.getReturnCondition(), detailEntity.getReturnDate(), detailEntity.getFines(), detailEntity.getFinedReason());
    }

    
    

}
