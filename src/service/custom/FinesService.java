package service.custom;

import dto.FinesDto;
import service.SuperService;

public interface FinesService extends SuperService{
    String updateUser(FinesDto finesDto) throws Exception;
    FinesDto get(Integer id) throws Exception;
}
