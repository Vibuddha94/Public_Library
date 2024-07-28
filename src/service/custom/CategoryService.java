package service.custom;

import java.util.ArrayList;

import dto.CategoryDto;
import service.SuperService;

public interface CategoryService extends SuperService {
    String save(CategoryDto categoryDto) throws Exception;
    String update(CategoryDto categoryDto) throws Exception;
    String delete(String id) throws Exception;
    CategoryDto get(String id) throws Exception;
    ArrayList<CategoryDto> getAll() throws Exception;
}
