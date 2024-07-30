package entity;

public class HistoryEntity {
    String bookId, memberId, borrowDate, returnDate, borrowCondition, returnCondition, finedReason;
    Double fines;
    
    public HistoryEntity() {
    }

    public HistoryEntity(String bookId, String memberId, String borrowDate, String returnDate, String borrowCondition,
            String returnCondition, Double fines, String finedReason) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.borrowCondition = borrowCondition;
        this.returnCondition = returnCondition;
        this.finedReason = finedReason;
        this.fines = fines;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getBorrowCondition() {
        return borrowCondition;
    }

    public void setBorrowCondition(String borrowCondition) {
        this.borrowCondition = borrowCondition;
    }

    public String getReturnCondition() {
        return returnCondition;
    }

    public void setReturnCondition(String returnCondition) {
        this.returnCondition = returnCondition;
    }

    public String getFinedReason() {
        return finedReason;
    }

    public void setFinedReason(String finedReason) {
        this.finedReason = finedReason;
    }

    public Double getFines() {
        return fines;
    }

    public void setFines(Double fines) {
        this.fines = fines;
    }

    
}
