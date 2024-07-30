package service.impl;

import java.util.ArrayList;

import dao.DaoFactory;
import dao.DaoFactory.DaoType;
import dao.custom.BorrowDao;
import dao.custom.Borrow_ReturnDetailDao;
import dto.BorrowDto;
import dto.Borrow_ReturnDetailDto;
import entity.BorrowEntity;
import entity.Borrow_ReturnDetailEntity;
import service.custom.BorrowService;

public class BorrowServiceImpl implements BorrowService{

    BorrowDao borrowDao = (BorrowDao) DaoFactory.getInstance().getDao(DaoType.BORROWING);
    Borrow_ReturnDetailDao borrow_ReturnDetailDao = (Borrow_ReturnDetailDao) DaoFactory.getInstance().getDao(DaoType.BORROW_RETURN);

    @Override
    public String save(BorrowDto borrowDto) throws Exception {
        return null;
    }

    @Override
    public String update(BorrowDto borrowDto) throws Exception {
        return null;
    }

    @Override
    public ArrayList<BorrowDto> getAll() throws Exception {
        ArrayList<Borrow_ReturnDetailEntity> borrow_ReturnDetailEntities = borrow_ReturnDetailDao.getAll();
        ArrayList<Borrow_ReturnDetailDto> borrow_ReturnDetailDtos = new ArrayList<>();
        for (Borrow_ReturnDetailEntity borrow_ReturnDetailEntity : borrow_ReturnDetailEntities) {
            Borrow_ReturnDetailDto borrow_ReturnDetailDto = new Borrow_ReturnDetailDto(borrow_ReturnDetailEntity.getBorrowId(), borrow_ReturnDetailEntity.getBookId(), borrow_ReturnDetailEntity.getBorrowCondition(), borrow_ReturnDetailEntity.getReturnCondition(), borrow_ReturnDetailEntity.getReturnDate(), borrow_ReturnDetailEntity.getFines(), borrow_ReturnDetailEntity.getFinedReason());
            borrow_ReturnDetailDtos.add(borrow_ReturnDetailDto);
        } 

        ArrayList<BorrowDto> borrowDtos = new ArrayList<>();
        ArrayList<BorrowEntity> borrowEntities = borrowDao.getAll();
        for (BorrowEntity borrowEntity : borrowEntities) {
            borrowDtos.add(new BorrowDto(borrowEntity.getBorrowId(), borrowEntity.getBorrowDate(), borrowEntity.getMemberId(), null));
        }
        borrowDtos.getFirst().setArrayList(borrow_ReturnDetailDtos);

        return borrowDtos;
    }

    
    
}
