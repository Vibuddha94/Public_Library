package tableModel;

public class ReturnTM {
    String bookId, returnCondition, finedReason;
    Double fines;
    
    public ReturnTM(String bookId, String returnCondition, String finedReason, Double fines) {
        this.bookId = bookId;
        this.returnCondition = returnCondition;
        this.finedReason = finedReason;
        this.fines = fines;
    }

    public ReturnTM() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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
