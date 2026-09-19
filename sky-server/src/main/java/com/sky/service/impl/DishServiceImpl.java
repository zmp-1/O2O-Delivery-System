package com.sky.service.impl;

import com.aliyuncs.ecs.model.v20140526.CreateDiskRequest;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.status;

@Service
@Slf4j
/**
 * 新增菜品和对应的口味
 */
public class DishServiceImpl implements DishService {
    @Autowired

    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
  @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        //像菜品表插入一条数据，想口味表插入n条数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        dishMapper.insert(dish);
        //获取insert语句生成的主键值

        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();

        if (flavors != null && flavors.size() > 0) {

            flavors.forEach(dishFlavor ->
            {
                dishFlavor.setDishId(dishId);

            });
            dishFlavorMapper.insertBatch(flavors);


        }

    }

    /**
     * 菜品的分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {


        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        return new PageResult(page.getPages(), page.getResult());
    }

    @Override
    public void deleteBatch() {

    }

    /**
     * 菜品的批量删除
     * @param ids
     */
    @Transactional
    public void deleteBatch( List<Long> ids) {
        //判断可不可以删除
        //1.删除的是否存在起售中的菜品
        for (long id : ids) {

            Dish dish = dishMapper.getbyId(id);

            if (dish.getStatus() == StatusConstant.ENABLE) {

                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //2.菜品是否被套餐包含
        List<Long> setmealIds =setmealDishMapper.getSetmealIdByDishId(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }


        /**for (Long id : ids) {
            dishMapper.deleteById(id);


           //删除口味
            dishFlavorMapper.deleteByDishId(id);

            //
        }
*/
        //根据菜品id集合批量删除我们的菜品数据
        dishMapper.deleteByIds(ids);







        //根据菜品id删除关联的口味数据
dishFlavorMapper.deleteByDishIds(ids);
    }


    /**根据id来查询菜品
     *
     * @param id
     * @return
     * @param
     */

    public  DishVO getByIdWithFlavor(Long id) {
        //根据id来查询菜品表中的属性
     Dish dish=   dishMapper.getbyId(id);
     //根据id来查询口味表当中的口味

       List<DishFlavor>  dishFlavors= dishFlavorMapper.getByDishId(id);


        DishVO dishVO =new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);








        return dishVO;
    }

    /**
     * 修改菜品的保存功能
     * @param dishDTO
     */


    public void updateByDishId(DishDTO dishDTO) {
        //修改菜品表的基本信息

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);

        //删除口味数据
dishFlavorMapper.deleteByDishIds(Collections.singletonList(dishDTO.getId()));



        //重复新插入口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null && flavors.size()>0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());

            });
            dishFlavorMapper.insertBatch(flavors);
        }



    }




    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */

    public List<Dish> list(Long categoryId) {
        Dish dish=Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                        .build();




        return dishMapper.list(dish);
    }

    /**
     * 修改菜品的起售停售
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        Dish dish=Dish.builder()
                .id(id)
                .status(status)
                .build();
        dishMapper.update(dish);










    }
}




























