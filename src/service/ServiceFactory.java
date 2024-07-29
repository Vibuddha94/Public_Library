package service;

import service.impl.BookServiceImpl;
import service.impl.CategoryServiceImpl;
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
                return null;
            case FINE:
                return null;
            default:
                return null;
        }
    }

    public enum ServiceType {
        USER, CATEGORY, MEMBER, BOOK, BORROWING, FINE
    }
}
