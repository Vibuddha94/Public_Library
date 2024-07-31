package service.custom;

import java.util.ArrayList;

import dto.BorrowDto;
import dto.Borrow_ReturnDetailDto;
import service.SuperService;

public interface BorrowService extends SuperService {
     String save(BorrowDto borrowDto) throws Exception;
     String update(ArrayList<Borrow_ReturnDetailDto> arrayList) throws Exception;
     ArrayList<Borrow_ReturnDetailDto> getDetail(String borrowId) throws Exception;
     ArrayList<BorrowDto> getAllBorrowings() throws Exception;
}
