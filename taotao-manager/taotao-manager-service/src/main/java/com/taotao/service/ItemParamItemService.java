package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;

public interface ItemParamItemService {

	String getItemParamByItemId(Long itemId);

	EUDataGridResult getItemList(Integer page, Integer rows);
}
