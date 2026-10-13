package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //添加购物车的时候，判断当球按加入购物车中的商品是否存在

        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
      //  Long userId = BaseContext.getCurrentId();
       Long  userId=1L;
        shoppingCart.setUserId(userId);


        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);


        //存在的话，只需要把数据加1，也就是不执行insert方法而是执行修改的update方法
        if (list != null && list.size() > 0) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);


            //不同的用户有不同的购物车，


        } else{
            //如果不存在，需要插入一条购物车数据

            //判断这次调价到购物车的是菜品还是套餐
           Long dishId=shoppingCartDTO.getDishId();


            if(dishId!=null) {
                //本次添加的是菜品
//从菜品表来查询DTO对象里没有的东西
                Dish dish = dishMapper.getbyId(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());


            } else{//本次添加进来的套餐

                Long setmealId=shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());

            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            shoppingCartMapper.insert(shoppingCart);
            }


        }

    /**
     * 查看购物车
     * @return
     */
    public List<ShoppingCart> showShoppingCart() {

        //Long userId = BaseContext.getCurrentId();//获取当前微信用户的id

Long userId=1L;
       ShoppingCart shoppingCart= ShoppingCart.builder()
                       .userId(1L)
                       .build();


       List<ShoppingCart> list= shoppingCartMapper.list(shoppingCart);
        return list;
    }

    /**
     * 清空购物车
     */


    public void cleanShoppingCart() {
        //获取当前微信用户的id
        //Long userId=Basecontext.getCurrentId();
        Long userId=1L;
        shoppingCartMapper.deleteByUserId(1L);


    }


}
