package entity;

public class Borrow_ReturnDetailEntity {
    String borrowId, bookId, borrowCondition, returnCondition, returnDate, finedReason;
    Double fines;
    
    public Borrow_ReturnDetailEntity() {
    }

    public Borrow_ReturnDetailEntity(String borrowId, String bookId, String borrowCondition, String returnCondition,
            String returnDate, Double fines, String finedReason) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.borrowCondition = borrowCondition;
        this.returnCondition = returnCondition;
        this.returnDate = returnDate;
        this.finedReason = finedReason;
        this.fines = fines;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
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
