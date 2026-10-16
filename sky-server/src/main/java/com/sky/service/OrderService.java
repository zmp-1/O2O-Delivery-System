package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderService {


    /**
     * 用户下单
     *
     * @param orderSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO orderSubmitDTO);


    /**
     * 用户端-订单支付
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     */
    void paySuccess(String outTradeNo);

    /**
     * 用户端-订单分页查询
     *
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    PageResult pageQuery4User(int page, int pageSize, Integer status);

     /**
     * 用户端-查询订单详情
     * @param id
     * @return
     */
    OrderVO details(Long id);

    /**
     * 用户端-取消订单
     */


    void userCancelById(Long id) throws Exception;

    /**
     *
     * 用户端-再来一单
     * @param id
     */
    void repetition(Long id);

    /**
     * 管理端-订单搜索
     * @param
     */


    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);


    /**
     * 管理端-各个状态的订单数量统计
     * @return
     */
    OrderStatisticsVO statistics();













}