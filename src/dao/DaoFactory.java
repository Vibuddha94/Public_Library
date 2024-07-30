package dao;

import dao.impl.BooksDaoImpl;
import dao.impl.BorrowDaoImpl;
import dao.impl.Borrow_ReturnDetailDaoImpl;
import dao.impl.CategoryDaoImpl;
import dao.impl.FinesDaoImpl;
import dao.impl.HistoryDaoImpl;
import dao.impl.MemberDaoImpl;
import dao.impl.UserDaoImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;

    public DaoFactory() {

    }

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public SuperDao getDao(DaoType daoType) {
        switch (daoType) {
            case USER:
                return new UserDaoImpl();
            case CATEGORY:
                return new CategoryDaoImpl();
            case MEMBER:
                return new MemberDaoImpl();
            case BOOK:
                return new BooksDaoImpl();
            case BORROWING:
                return new BorrowDaoImpl();
            case BORROW_RETURN:
                return new Borrow_ReturnDetailDaoImpl();    
            case FINE:
                return new FinesDaoImpl();
            case HISTORY:
                return new HistoryDaoImpl();
            default:
                return null;
        }
    }

    public enum DaoType {
        USER, CATEGORY, MEMBER, BOOK, BORROWING, FINE, BORROW_RETURN, HISTORY
    }

}
