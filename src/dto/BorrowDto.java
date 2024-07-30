package dto;

import java.util.ArrayList;

public class BorrowDto {
    String borrowId, borrowDate, memberId;
    ArrayList<Borrow_ReturnDetailDto> arrayList;
    
    public BorrowDto() {
    }

    public BorrowDto(String borrowId, String borrowDate, String memberId, ArrayList<Borrow_ReturnDetailDto> arrayList) {
        this.borrowId = borrowId;
        this.borrowDate = borrowDate;
        this.memberId = memberId;
        this.arrayList = arrayList;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String bookId) {
        this.borrowDate = bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public ArrayList<Borrow_ReturnDetailDto> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Borrow_ReturnDetailDto> arrayList) {
        this.arrayList = arrayList;
    }

    
}
