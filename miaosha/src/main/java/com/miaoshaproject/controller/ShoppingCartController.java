package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.CartVO;
import com.miaoshaproject.controller.viewobject.ItemVO;
import com.miaoshaproject.dao.ItemDOMapper;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.CartService;
import com.miaoshaproject.service.model.CartModel;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.CoderMalfunctionError;
import java.util.List;
import java.util.stream.Collectors;

@Controller(value = "cart")
@RequestMapping(value = "/cart")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ShoppingCartController extends BaseController {
    @Autowired
    private CartService cartService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private ItemDOMapper itemDOMapper;

    @RequestMapping(value = "/addcart",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType addCart(@RequestParam(name="itemId")Integer itemId,
                                     @RequestParam(name="promoId",required = false)Integer promoId,
                                     @RequestParam(name="amount")Integer amount) throws BusinessException {
        UserModel userModel=(UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        CartModel cartModel=cartService.addCart(userModel.getId(),itemId,amount,promoId);
        return CommonReturnType.create(cartModel);
    }
    @RequestMapping(value = "/listcart", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listCart() throws BusinessException{
       Integer userId=Integer.valueOf( this.httpServletRequest.getSession().getAttribute("userId").toString());
        List<CartModel> cartModelList = cartService.listCart(userId);
        List<CartVO> cartVOList = cartModelList.stream().map(cartModel  -> {
            CartVO cartVO = convertVOFromModel(cartModel);
            return cartVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(cartVOList);
    }

    @RequestMapping(value = "/clear_cart")
    @ResponseBody
    public CommonReturnType clearCart()throws BusinessException {
        String status = "notSuccess";
        Integer userId = Integer.valueOf(this.httpServletRequest.getSession().getAttribute("userId").toString());
        status = cartService.clearCart(userId) ? "success" : "NotSuccess";
        CommonReturnType r = CommonReturnType.create(status);
        return r;
    }

    @RequestMapping(value = "/purchase")
    @ResponseBody
    @Transactional
    public CommonReturnType purchase()throws BusinessException{
        Integer userId=Integer.valueOf(httpServletRequest.getSession().getAttribute("userId").toString());
        String result= cartService.purchase(userId)?"success":"notSuccess";
        return CommonReturnType.create(result);
    }

    public CartVO convertVOFromModel(CartModel cartModel){
        if(cartModel==null){
            return null;
        }
        else {
            CartVO cartVO=new CartVO();
            BeanUtils.copyProperties(cartModel,cartVO);
            cartVO.setImagUrl(itemDOMapper.selectByPrimaryKey(cartModel.getItermId()).getImgUrl());
            return cartVO;
        }
    }
}
