package service.impl;

import dao.DaoFactory;
import dao.DaoFactory.DaoType;
import dao.custom.FinesDao;
import dto.FinesDto;
import entity.FinesEntity;
import service.custom.FinesService;

public class FinesServiceImpl implements FinesService {

    FinesDao finesDao = (FinesDao) DaoFactory.getInstance().getDao(DaoType.FINE);

    @Override
    public String updateUser(FinesDto finesDto) throws Exception {
       return finesDao.update(new FinesEntity(finesDto.getId(), finesDto.getLate(), finesDto.getDamage(), finesDto.getLost())) ? "Success" : "Fail" ;
    }

    @Override
    public FinesDto get(Integer id) throws Exception {
        FinesEntity finesEntity = finesDao.get(id);
        if (finesEntity != null) {
            return new FinesDto(finesEntity.getId(), finesEntity.getLate(), finesEntity.getDamage(), finesEntity.getLost());
        }
        return null;
    }
    
}
