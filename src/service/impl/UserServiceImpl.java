package service.impl;

import java.util.ArrayList;

import dao.DaoFactory;
import dao.DaoFactory.DaoType;
import dao.custom.UserDao;
import dto.UserDto;
import entity.UserEntity;
import service.custom.UserService;

public class UserServiceImpl implements UserService {
    
    UserDao userDao = (UserDao) DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public String saveUser(UserDto userDto) throws Exception {
        Boolean resp = userDao.create(getEntity(userDto));
        return resp ? "Success" : "Fail" ;
    }

    @Override
    public String updateUser(UserDto userDto) throws Exception {
        Boolean resp = userDao.update(getEntity(userDto));
        return resp ? "Success" : "Fail" ;
    }

    @Override
    public String deleteUser(String id) throws Exception {
        Boolean resp = userDao.delete(id);
        return resp ? "Success" : "Fail" ;
    }

    @Override
    public UserDto get(String userId) throws Exception {
        if (userDao.get(userId) != null) {
            return getDto(userDao.get(userId));
        }
        return null;
    }

    @Override
    public ArrayList<UserDto> getAll() throws Exception {
        ArrayList<UserEntity> userEntities = userDao.getAll();
        if (userEntities != null) {
            ArrayList<UserDto> userDtos = new ArrayList<>();
            for (UserEntity userEntity : userEntities) {
                userDtos.add(getDto(userEntity));
            }
            return userDtos;
        }

        return null;
        
    }

    private UserEntity getEntity(UserDto userDto) throws Exception{
        UserEntity userEntity = new UserEntity(userDto.getUserId(), userDto.getFirstName(), userDto.getLastName(), userDto.getDob(), userDto.getAddress(), userDto.getContactNumber(), userDto.getPassword(), userDto.getIsAdmin());
        return userEntity;
    }

    private UserDto getDto(UserEntity userEntity) throws Exception{
        UserDto userDto = new UserDto(userEntity.getUserId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDob(), userEntity.getAddress(), userEntity.getContactNumber(), userEntity.getPassword(), userEntity.getIsAdmin());
        return userDto;
    }
    
}
