package service.custom;

import java.util.ArrayList;

import dto.MemberDto;
import service.SuperService;

public interface MemberService extends SuperService {
    String saveUser(MemberDto memberDto) throws Exception;
    String updateUser(MemberDto memberDto) throws Exception;
    String deleteUser(String id) throws Exception;
    MemberDto get(String userId) throws Exception;
    ArrayList<MemberDto> getAll() throws Exception;
}
