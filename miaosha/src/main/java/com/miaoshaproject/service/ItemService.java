package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.ItemModel;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ItemService {
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    List<ItemModel>listItem();

    ItemModel getItemById(Integer id);

    boolean decreaseStock(Integer itemId,Integer amount) throws BusinessException;

    //商品下单后对应销量增加
    void increaseSales(Integer itemId,Integer amount) throws BusinessException;

    boolean returnStock(Integer itemId,Integer itemAmount);
}
