package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.CartModel;
import com.miaoshaproject.service.model.ItemModel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
public interface CartService {
    CartModel addCart(Integer UserId,Integer ItemId,Integer ItemNum,Integer promId)throws BusinessException;
    List<CartModel> listCart(Integer userId);
    boolean clearCart(Integer userId) throws BusinessException;
    boolean purchase(Integer userId);
    //    CartModel getCartByItemId(Integer ItemId);
}

