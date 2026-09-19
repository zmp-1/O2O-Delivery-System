package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品的id来查询套餐的id ，多对多的关系
     */
    List<Long> getSetmealIdByDishId(List<Long> ids);
}
