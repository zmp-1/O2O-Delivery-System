package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {


    /**
     * 新增菜品和口味表
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

/**
 *
 * 菜品的分页查询
 */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch();
}






