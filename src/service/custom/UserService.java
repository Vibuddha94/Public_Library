package service.custom;

import java.util.ArrayList;

import dto.UserDto;
import service.SuperService;

public interface UserService extends SuperService{
    String saveUser(UserDto userDto) throws Exception;
    String updateUser(UserDto userDto) throws Exception;
    String deleteUser(String id) throws Exception;
    UserDto get(String userId) throws Exception;
    ArrayList<UserDto> getAll() throws Exception;
}
