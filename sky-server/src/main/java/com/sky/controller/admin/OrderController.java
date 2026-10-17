package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 订单管理
 */
@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Slf4j
@Api(tags = "订单管理接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单搜索
     *
     * @param ordersPageQueryDTO
     * @return
     */
    @GetMapping("/conditionSearch")
    @ApiOperation("订单搜索")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageResult pageResult = orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 各个状态的订单数量统计
     *
     * @return
     */
    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单数量统计")
    public Result<OrderStatisticsVO> statistics() {
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

//业务规则
//订单详情要展示订单的基本信息  状态，订单号，下单信息，收货人，电话，收货地址，金额
//订单详情页面需要展示订单明细数据   商品名称，数量，单价

    /**
     * 订单详情
     *
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> details(@PathVariable("id") Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }


    /**
     * 接单
     * @param
     * @return
     */
    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {

orderService.confirm(ordersConfirmDTO);
return Result.success();
    }

/**
 * 拒单
 * 业务规则：
 * 1.上家具但就是将订单的状态修改
 * 2.只有订单的状态是待接单的时候才能拒单
 * 3.商家拒单时需要指定拒单原因
 * 4.上家具但是。如果用户完成支付，需要为用户退款
 *
 */


        @PutMapping("/rejection")
        @ApiOperation("拒单")


        public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {

            orderService.rejection(ordersRejectionDTO);
            return Result.success();
        }

    /**
     * 取消订单
     * @param
     * @return
     * @throws Exception
     */
    @PutMapping("/cancel")
    @ApiOperation("取消订单")


    public Result cancel(@RequestBody OrdersCancelDTO orderscancelDTO) throws Exception {

        orderService.cancel(orderscancelDTO);
        return Result.success();
    }
















    }






































