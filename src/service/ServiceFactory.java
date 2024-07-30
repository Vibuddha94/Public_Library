package service;

import service.impl.BookServiceImpl;
import service.impl.BorrowServiceImpl;
import service.impl.CategoryServiceImpl;
import service.impl.FinesServiceImpl;
import service.impl.HistoryServiceImpl;
import service.impl.MemberServiceImpl;
import service.impl.UserServiceImpl;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory() {

    }

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }

    public SuperService getService(ServiceType servicetype) {
        switch (servicetype) {
            case USER:
                return new UserServiceImpl();
            case CATEGORY:
                return new CategoryServiceImpl();
            case MEMBER:
                return new MemberServiceImpl();
            case BOOK:
                return new BookServiceImpl();
            case BORROWING:
                return new BorrowServiceImpl();
            case FINE:
                return new FinesServiceImpl();
            case HISTORY:
                return new HistoryServiceImpl();
            default:
                return null;
        }
    }

    public enum ServiceType {
        USER, CATEGORY, MEMBER, BOOK, BORROWING, FINE, HISTORY
    }
}
