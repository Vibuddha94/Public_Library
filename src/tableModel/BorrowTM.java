package tableModel;

public class BorrowTM {
    String bookId, isuueCondition, returnDate;

    public BorrowTM(String bookId, String isuueCondition, String returnDate) {
        this.bookId = bookId;
        this.isuueCondition = isuueCondition;
        this.returnDate = returnDate;
    }

    public BorrowTM() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIsuueCondition() {
        return isuueCondition;
    }

    public void setIsuueCondition(String isuueCondition) {
        this.isuueCondition = isuueCondition;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

}
