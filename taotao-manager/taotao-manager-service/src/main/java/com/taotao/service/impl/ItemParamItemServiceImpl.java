package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TbItemParamList;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemParamItemService;

/**
 * 展示商品规格参数
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Autowired
	private TbItemCatMapper itemcatMapper;
	
	@Override
	public String getItemParamByItemId(Long itemId) {
		//根据商品id查询规格参数
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list == null || list.size() == 0) {
			return "";
		}
		//取规格参数信息
		TbItemParamItem itemParamItem = list.get(0);
		String paramData = itemParamItem.getParamData();
		//生成html
		// 把规格参数json数据转换成java对象
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append("    <tbody>\n");
		for(Map m1:jsonList) {
			sb.append("        <tr>\n");
			sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
			sb.append("        </tr>\n");
			List<Map> list2 = (List<Map>) m1.get("params");
			for(Map m2:list2) {
				sb.append("        <tr>\n");
				sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
				sb.append("            <td>"+m2.get("v")+"</td>\n");
				sb.append("        </tr>\n");
			}
		}
		sb.append("    </tbody>\n");
		sb.append("</table>");
		return sb.toString();
	}

	@Override
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		        //查询商品列表
				TbItemParamExample example = new TbItemParamExample();
				//分页处理
				PageHelper.startPage(page, rows);
				List<TbItemParam> list_itemParam = itemParamMapper.selectByExampleWithBLOBs(example);
				List<TbItemParamList> list_ItemParamList= new ArrayList<TbItemParamList>(); 
				
				for (int i = 0; i < list_itemParam.size(); i++) {
					TbItemParamList list =new TbItemParamList();
					list.setId(list_itemParam.get(i).getId());
					list.setItemCatId(list_itemParam.get(i).getItemCatId());
					list.setItemCatName(itemcatMapper.selectByPrimaryKey(list_itemParam.get(i).getItemCatId()).getName());
					list.setParamData(list_itemParam.get(i).getParamData());
					list.setCreated(list_itemParam.get(i).getCreated());
					list.setUpdated(list_itemParam.get(i).getUpdated());
					list_ItemParamList.add(list);
				}
	
				//创建一个返回值对象
				EUDataGridResult result = new EUDataGridResult();
				result.setRows(list_ItemParamList);
				//取记录总条数
				PageInfo<TbItemParamList> pageInfo = new PageInfo<>(list_ItemParamList);
				result.setTotal(pageInfo.getTotal());
				return result;
	}

	
}
