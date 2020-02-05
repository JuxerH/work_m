package com.miaoshaproject.service.impI;

import com.miaoshaproject.controller.viewobject.ItemVO;
import com.miaoshaproject.dao.ItemDOMapper;
import com.miaoshaproject.dao.ShoppingCartDOMapper;
import com.miaoshaproject.dataobject.ItemDO;
import com.miaoshaproject.dataobject.ItemStockDO;
import com.miaoshaproject.dataobject.ShoppingCartDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.*;
import com.miaoshaproject.service.model.CartModel;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.PromoModel;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartDOMapper shoppingCartDOMapper;
    @Autowired
    private PromoService promoService;
    @Autowired
    private OrderService orderService;

    @Override
    @Transactional
    public CartModel addCart(Integer UserId, Integer ItemId, Integer ItemNum, Integer promId) throws BusinessException {
        ItemModel itemModel = itemService.getItemById(ItemId);
        UserModel userModel = userService.getUserById(UserId);
        CartModel cartModel = new CartModel();
        if (shoppingCartDOMapper.selectByItemId(ItemId, UserId) == null) {

            boolean result = itemService.decreaseStock(ItemId, ItemNum);
            if (!result) {
                throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
            }

            cartModel.setItermId(ItemId);
            cartModel.setUserId(UserId);
            cartModel.setItermNum(ItemNum);
            PromoModel promoModel = promoService.getPromoById(ItemId);
            if (promoModel != null && promoModel.getStatus().intValue() == 2) {
                cartModel.setItermPrice(promoModel.getPromoItemPrice().doubleValue());
            } else cartModel.setItermPrice(itemModel.getPrice().doubleValue());
            cartModel.setItermTitle(itemModel.getTitle());
            cartModel.setItermTotal(cartModel.getItermPrice().doubleValue() * cartModel.getItermNum().intValue());
            cartModel.setUserName(userModel.getName());
            shoppingCartDOMapper.insertSelective(convertFromCartModel(cartModel));
        } else {
            cartModel = convertCartModelFromDataObject(shoppingCartDOMapper.selectByItemId(ItemId, UserId));
            int existNum = cartModel.getItermNum().intValue();
            double existTotal = cartModel.getItermTotal();
            cartModel.setItermNum(existNum + ItemNum);
            PromoModel promoModel = promoService.getPromoById(ItemId);
            if (promoModel != null && promoModel.getStatus().intValue() == 2) {
                cartModel.setItermTotal(existTotal + promoModel.getPromoItemPrice().doubleValue() * ItemNum);
            } else cartModel.setItermTotal(existTotal + itemModel.getPrice().doubleValue() * ItemNum);
            shoppingCartDOMapper.updateByItemId(convertFromCartModel(cartModel));
        }
        return cartModel;
    }

    @Override
    public List<CartModel> listCart(Integer userId) {
        List<ShoppingCartDO> shoppingCartDOList = shoppingCartDOMapper.listCartDO(userId);
        List<CartModel> cartModelList = shoppingCartDOList.stream().map(shoppingCartDO -> {
            CartModel cartModel = this.convertCartModelFromDataObject(shoppingCartDO);
            return cartModel;
        }).collect(Collectors.toList());
        return cartModelList;

    }

    @Override
    public boolean clearCart(Integer userId) {
        boolean clearResult = false;
        clearResult = shoppingCartDOMapper.deleteByUserId(userId) > 0 ? true : false;
        List<ShoppingCartDO> shoppingCartDOList = shoppingCartDOMapper.listCartDO(userId);
        for (int i = 0; i < shoppingCartDOList.size(); i++) {
            Integer id = shoppingCartDOList.get(i).getItermId();
            Integer amount = shoppingCartDOList.get(i).getItermNum();
            if (itemService.returnStock(id, amount) == false) return false;
        }
        return clearResult;
    }

    @Override
    public boolean purchase(Integer userId) {
        boolean result=true;
        List<ShoppingCartDO>shoppingCartDOList=shoppingCartDOMapper.listCartDO(userId);
        for (int i=0;i<shoppingCartDOList.size();i++){
            ItemModel itemModel=itemService.getItemById( shoppingCartDOList.get(i).getItermId());
            try {
                if(orderService.createOrder(userId,shoppingCartDOList.get(i).getItermId(),itemModel.getPromoModel().getId(),shoppingCartDOList.get(i).getItermNum())==null)
                    result=false;
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        return result;

    }
//    public CartModel getCartByItemId(Integer ItemId) {
//        ;
//    }

    public CartModel convertCartModelFromDataObject(ShoppingCartDO shoppingCartDO) {
        if (shoppingCartDO == null) return null;
        else {
            CartModel cartModel = new CartModel();
            BeanUtils.copyProperties(shoppingCartDO, cartModel);
            return cartModel;
        }
    }

    public ShoppingCartDO convertFromCartModel(CartModel cartModel) {
        if (cartModel == null) return null;
        else {
            ShoppingCartDO shoppingCartDO = new ShoppingCartDO();
            BeanUtils.copyProperties(cartModel, shoppingCartDO);
            return shoppingCartDO;
        }
    }
}
