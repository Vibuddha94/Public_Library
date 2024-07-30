package service.impl;

import java.util.ArrayList;

import dao.DaoFactory;
import dao.DaoFactory.DaoType;
import dao.custom.HistoryDao;
import entity.HistoryEntity;
import service.custom.HistoryService;
import tableModel.HistoryTM;

public class HistoryServiceImpl implements HistoryService {

    HistoryDao historyDao = (HistoryDao) DaoFactory.getInstance().getDao(DaoType.HISTORY);

    @Override
    public ArrayList<HistoryTM> getMemberHistory(String memId) throws Exception {
        ArrayList<HistoryEntity> historyEntities = historyDao.getMemberHistory(memId);
        ArrayList<HistoryTM> historyTMs = new ArrayList<>();
        for (HistoryEntity historyEntity : historyEntities) {
            historyTMs.add(getTM(historyEntity));
        }
        return historyTMs;
    }

    @Override
    public ArrayList<HistoryTM> getBookHistory(String bookId) throws Exception {
        ArrayList<HistoryEntity> historyEntities = historyDao.getBookHistory(bookId);
        ArrayList<HistoryTM> historyTMs = new ArrayList<>();
        for (HistoryEntity historyEntity : historyEntities) {
            historyTMs.add(getTM(historyEntity));
        }
        return historyTMs;
    }

    private HistoryTM getTM(HistoryEntity entity){
        return new HistoryTM(entity.getBookId(), entity.getMemberId(), entity.getBorrowDate(), entity.getReturnDate(), entity.getBorrowCondition(), entity.getReturnCondition(), entity.getFines(), entity.getFinedReason());
    }
    
}
