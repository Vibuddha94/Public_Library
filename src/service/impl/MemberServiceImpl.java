package service.impl;

import java.util.ArrayList;

import dao.DaoFactory;
import dao.DaoFactory.DaoType;
import dao.custom.MemberDao;
import dto.MemberDto;
import entity.MemberEntity;
import service.custom.MemberService;

public class MemberServiceImpl implements MemberService {

    MemberDao memberDao = (MemberDao) DaoFactory.getInstance().getDao(DaoType.MEMBER);

    @Override
    public String saveUser(MemberDto memberDto) throws Exception {
        return memberDao.create(getEntity(memberDto)) ? "Success" : "Fail";
    }

    @Override
    public String updateUser(MemberDto memberDto) throws Exception {
        return memberDao.update(getEntity(memberDto)) ? "Success" : "Fail";
    }

    @Override
    public String deleteUser(String id) throws Exception {
        return memberDao.delete(id) ? "Success" : "Fail";
    }

    @Override
    public MemberDto get(String userId) throws Exception {
        if (memberDao.get(userId) != null) {
            return getDto(memberDao.get(userId));
        }
        return null;
    }

    @Override
    public ArrayList<MemberDto> getAll() throws Exception {
        ArrayList<MemberEntity> memberEntities = memberDao.getAll();
        ArrayList<MemberDto> memberDtos = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntities) {
            MemberDto memberDto = getDto(memberEntity);
            memberDtos.add(memberDto);
        }
        return memberDtos;
    }

    private MemberEntity getEntity(MemberDto memberDto) throws Exception{
        return new MemberEntity(memberDto.getMemberId(), memberDto.getFirstName(), memberDto.getLastName(), memberDto.getDob(), memberDto.getAddress(), memberDto.getContNumber());
    }

    private MemberDto getDto(MemberEntity memberEntity) throws Exception{
        return new MemberDto(memberEntity.getMemberId(), memberEntity.getFirstName(), memberEntity.getLastName(), memberEntity.getDob(), memberEntity.getAddress(), memberEntity.getContNumber());
    }
    
}
