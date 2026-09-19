package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

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

    /**
     * 根据id来查询菜品
     * @param id
     * @return
     * @param
     */
     DishVO getByIdWithFlavor(Long id);


    /**
     * 修改菜品的更新保存功能
     */
    void updateByDishId(DishDTO dishDTO);




    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     *
     *
     * 修改菜品的起售停售
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}


