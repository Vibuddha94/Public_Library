package service.custom;

import java.util.ArrayList;

import dto.BorrowDto;
import dto.Borrow_ReturnDetailDto;
import service.SuperService;

public interface BorrowService extends SuperService {
     String save(BorrowDto borrowDto) throws Exception;
     String update(BorrowDto borrowDto) throws Exception;
     ArrayList<Borrow_ReturnDetailDto> getAllDetail() throws Exception;
     ArrayList<BorrowDto> getAllBorrowings() throws Exception;
}
