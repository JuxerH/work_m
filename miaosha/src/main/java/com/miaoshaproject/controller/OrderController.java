package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.OrderVO;
import com.miaoshaproject.dataobject.OrderDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller(value = "order")
@RequestMapping(value = "/order")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    //封装下单请求
    @RequestMapping(value = "/createorder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="promoId",required = false)Integer promoId,
                                        @RequestParam(name="amount")Integer amount) throws BusinessException {

        //获取登录信息（Boolean)
        Boolean isLogin = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
//        System.out.println(isLogin.booleanValue());
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登陆，不能下单");
        }
//        Boolean isLogin = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
//        if (isLogin == null || !isLogin.booleanValue()) {
//            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "用户还未登录，不能下单");
//        }
        UserModel userModel = (UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(),itemId,promoId,amount);
        return CommonReturnType.create(null);

    }

    @RequestMapping(value = "/showAllRecord",method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType showAllRecord()throws BusinessException{
        List<OrderModel> orderDOList=orderService.listOrder();
        List<OrderVO> orderVOList=new ArrayList<OrderVO>();
        try{
            for(int i=0;i<orderDOList.size();i++) {
                OrderModel orderModel=orderDOList.get(i);
                OrderVO orderVO=convertFromModel(orderModel);
                orderVOList.add(orderVO);
            }
        }catch (BusinessException e)
        {
            throw e;
        }

        CommonReturnType commonReturnType=CommonReturnType.create(orderVOList);
        return commonReturnType;
    }

    private OrderVO convertFromModel(OrderModel orderModel) throws BusinessException {
        if (orderModel==null)return null;
        OrderVO orderVO=new OrderVO();
        BeanUtils.copyProperties(orderModel,orderVO);
       try {
           orderVO.setUserName(userService.getUserById(orderModel.getUserId()).getName());
           orderVO.setItemName(itemService.getItemById(orderModel.getItemId()).getTitle());

       }catch(BusinessException e)
       {
           throw e;
       }
        return orderVO;
    }
}

