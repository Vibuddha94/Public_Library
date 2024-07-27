package dao;

import dao.impl.CategoryDaoImpl;
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
                return null;
            case BOOK:
                return null;
            case BORROWING:
                return null;
            case FINE:
                return null;
            default:
                return null;
        }
    }

    public enum DaoType {
        USER, CATEGORY, MEMBER, BOOK, BORROWING, FINE
    }

}
