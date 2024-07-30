package service.custom;

import java.util.ArrayList;

import dto.BorrowDto;

public interface BorrowService {
     String save(BorrowDto borrowDto) throws Exception;
     String update(BorrowDto borrowDto) throws Exception;
     ArrayList<BorrowDto> getAll() throws Exception;
}
