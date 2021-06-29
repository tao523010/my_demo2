package lanlyc.module.${model}.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import lanlyc.annotation.RepeatSubmit;
import lanlyc.annotation.Permission;
import lanlyc.common.BaseController;
import lanlyc.base.util.Response;

@RestController
@RequestMapping("/${model}")
public class ${class} extends BaseController {
	
	@RequestMapping("/list")
	@RepeatSubmit  //5秒内防重复提交
	@Permission  //添加权限拦截
	public Response getList(){
		Response re = Response.newResponse();
		return re;
	}
	
	@RequestMapping("/pageList")
	public Response pageList(){
		startPage();
		//查询分页数据
		//list<?> data = null//.....Dao.getPageMysql(sql,paramMap);
		return Response.setTableData(null/*data*/);
	}
	

}