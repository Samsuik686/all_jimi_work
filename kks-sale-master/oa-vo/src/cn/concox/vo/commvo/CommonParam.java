package cn.concox.vo.commvo;

/**
 * 页面请求通用参数类
 * 
 * @author huangwm
 * 
 */
public class CommonParam {

    protected Integer currentpage;// 当前页数

    protected Integer pageSize;// 页数大小

    public Integer getCurrentpage() {
        return currentpage == null ? 1 : currentpage;
    }

    public void setCurrentpage(Integer currentpage) {
        this.currentpage = currentpage;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
