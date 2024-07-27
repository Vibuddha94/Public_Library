package service.custom;

import java.util.ArrayList;

import dto.CategoryDto;
import service.SuperService;

public interface CategoryService extends SuperService {
    ArrayList<CategoryDto> getAll() throws Exception;
}
