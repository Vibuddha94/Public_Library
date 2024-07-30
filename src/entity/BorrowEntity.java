package entity;

public class BorrowEntity {
    String borrowId, borrowDate, memberId;

    public BorrowEntity() {
    }

    public BorrowEntity(String borrowId, String borrowDate, String memberId) {
        this.borrowId = borrowId;
        this.borrowDate = borrowDate;
        this.memberId = memberId;
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

    
}
