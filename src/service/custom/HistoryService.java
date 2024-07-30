package service.custom;

import java.util.ArrayList;

import service.SuperService;
import tableModel.HistoryTM;

public interface HistoryService extends SuperService {
    ArrayList<HistoryTM> getMemberHistory(String memId) throws Exception;
    ArrayList<HistoryTM> getBookHistory(String bookId) throws Exception;
}
