package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 新增菜品接口
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags="菜品相关接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
private SetmealService setmealService;
    @Autowired
   private RedisTemplate redisTemplate;




    @PostMapping
    @ApiOperation("菜品相关接口")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        String key="dish_"+dishDTO.getCategoryId();
        cleanCache(key);
        return Result.success();
    }

    /**
     * 菜品分页查询
     */
    @GetMapping("/page")
    @ApiOperation("菜品的分页查询接口")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品的分页查询：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);


        return Result.success(pageResult);
    }

    /**
     * 批量删除菜品
     *
     */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result delete(@RequestParam List<Long> ids) {


        log.info("批量删除菜品：{}", ids);

        dishService.deleteBatch();
//简化我么所有的一dish_开头的key删除
        cleanCache("dish_*");
        return Result.success();
    }
@GetMapping("/{id}")
    @ApiOperation("根据id来查询菜品")
    public  Result<DishVO> getById(@PathVariable Long  id){

        log.info("根据id查询菜品：{}",id);
         DishVO  dishVO=dishService.getByIdWithFlavor(id);


        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("修改菜品的保存功能")
public Result update(@RequestBody DishDTO dishDTO) {

        log.info("修改菜品：{}", dishDTO);
        dishService.updateByDishId(dishDTO);
        cleanCache("dish_*");


        return Result.success();
    }

    /**
     * 修改菜品的起售停售
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result<String> startOrStop(@PathVariable Integer status,Long id){
        log.info("菜品起售停售：status={},id={}",status,id);


        dishService.startOrStop(status,id);
        cleanCache("dish_*");
        return Result.success();





    }









    /**
         * 根据分类id查询菜品
          */


      @GetMapping("/list")
      @ApiOperation("根据分类id查询菜品")
        public Result<List<Dish>>list(Long categoryId){
          List<Dish> list=dishService.list(categoryId);



            return Result.success(list);
        }
    /**
     * 统一来清理缓存数据
     */
public void cleanCache(String pattern){

    Set  keys = redisTemplate.keys(pattern);
    redisTemplate.delete(keys);


}

}




















































