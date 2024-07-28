package service.impl;

import java.util.ArrayList;

import dao.DaoFactory;
import dao.DaoFactory.DaoType;
import dao.custom.CategoryDao;
import dto.CategoryDto;
import entity.CategoryEntity;
import service.custom.CategoryService;

public class CategoryServiceImpl implements CategoryService {

    CategoryDao categoryDao = (CategoryDao) DaoFactory.getInstance().getDao(DaoType.CATEGORY);

    @Override
    public String save(CategoryDto categoryDto) throws Exception {
        return categoryDao.create(getEntity(categoryDto)) ? "Success" : "Fail";
    }

    @Override
    public String update(CategoryDto categoryDto) throws Exception {
        return categoryDao.update(getEntity(categoryDto)) ? "Success" : "Fail";
    }

    @Override
    public String delete(String id) throws Exception {
        return categoryDao.delete(id) ? "Success" : "Fail";
    }

    @Override
    public CategoryDto get(String id) throws Exception {
        if (categoryDao.get(id) != null) {
            return getDto(categoryDao.get(id));
        }
        return null;
    }

    @Override
    public ArrayList<CategoryDto> getAll() throws Exception {
        ArrayList<CategoryEntity> categoryEntities = categoryDao.getAll();
        ArrayList<CategoryDto> categoryDtos = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            CategoryDto categoryDto = getDto(categoryEntity);
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    private CategoryDto getDto(CategoryEntity categoryEntity) {
        return new CategoryDto(categoryEntity.getId(), categoryEntity.getName());
    }

    private CategoryEntity getEntity(CategoryDto categoryDto) {
        return new CategoryEntity(categoryDto.getId(), categoryDto.getName());
    }

}
