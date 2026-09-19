package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

/**
 * 批量插入口味数据的方法
 */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根基菜品id来删除对应的口味数据
     * @param dishId
     */

   // @Delete("delete from dish_flavor where dish_id=#{dishId}")

    void deleteByDishId(Long dishId);

    /**
     * 根据菜品id集合删除对应的口味数据
     * @param dishIds
     */
    void deleteByDishIds(List<Long> dishIds);


    /**
     * 根据菜品id来查询口味数据
     * @return
     */

    @Select("select *from dish_flavor where dish_id=#{dishId}")


   List<DishFlavor> getByDishId(Long dishId);

}


/**
 * 修改菜品的第一步 查询菜品
 * @Select("select from dishflavor where dish+id=#{id}")
 *   ....
 *
 */









