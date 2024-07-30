package dao.custom;

import java.util.ArrayList;

import dao.SuperDao;
import entity.HistoryEntity;

public interface HistoryDao extends SuperDao {
    ArrayList<HistoryEntity> getMemberHistory(String memId) throws Exception;
    ArrayList<HistoryEntity> getBookHistory(String bookId) throws Exception;
}
