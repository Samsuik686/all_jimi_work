package cn.concox.app.common.page;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.concox.comm.vo.APIContent;
/**
 * 分页控制器
 * @author Li.Shangzhi
 * @date 2016-09-06 10:33:14
 */
@Controller
public class Comm {
	@RequestMapping("/form/queryCurrentPageSize.api")
	@ResponseBody 
	public APIContent getCurrentPageSize(HttpServletRequest req) {
		 Object objTotal=req.getSession().getAttribute("totalValue");
		 return new APIContent(objTotal);    
	}     
	 
	@RequestMapping("/form/queryCurrentPageSizeTwo.api")
	@ResponseBody 
	public APIContent queryCurrentPageSizeTwo(HttpServletRequest req) {
		 Object obj=req.getSession().getAttribute("totalValueTwo");
		     
		 return new APIContent(obj);
	}     
}