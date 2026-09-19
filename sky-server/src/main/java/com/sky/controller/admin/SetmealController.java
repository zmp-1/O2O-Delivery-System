package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
     * 套餐管理
     */
    @RestController
    @RequestMapping("/admin/setmeal")
    @Api(tags = "套餐相关接口")
    @Slf4j
    public class SetmealController {

        @Autowired
        private SetmealService setmealService;

        /**
         * 新增套餐
         * @param setmealDTO
         * @return
         */
        @PostMapping
        @ApiOperation("新增套餐")
        public Result save(@RequestBody SetmealDTO setmealDTO) {
            setmealService.saveWithDish(setmealDTO);
            return Result.success();
        }
        /**
         * 批量删除套餐
         */
        @DeleteMapping
        @ApiOperation("批量删除套餐")

        public Result delete(@RequestParam  List<Long> ids){
            setmealService.deleteBatch(ids);

            return Result.success();
        }

    /**
     * 套餐起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售停售")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        setmealService.startOrStop(status, id);
        return Result.success();
    }













    }














