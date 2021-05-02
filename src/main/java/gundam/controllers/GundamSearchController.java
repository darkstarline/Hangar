package gundam.controllers;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import gundam.common.DfsFactory;
import gundam.common.JsonResult;
import gundam.common.OperatorInfo;
import gundam.common.UserSession;
import gundam.constans.AppConstans;
import gundam.constans.ResourceConstans;
import gundam.pojo.FileInfoBean;
import gundam.pojo.FileInvokeBean;
import gundam.pojo.GundamBean;
import gundam.pojo.GundamFileRelBean;
import gundam.services.IElasticsearchService;
import gundam.utils.ThreadUtils;
import gundam.utils.UploadUtils;
import gundam.vo.GundamVo;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Post;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

public class GundamSearchController {
    @Autowired
    IElasticsearchService elasticsearchService;
    @Post("search")
    public JSON searchInfo(Invocation inv, String organismNumber) throws Exception{
        JsonResult json=new JsonResult();
        GundamBean gundamBean = new GundamBean();
        List<GundamBean> list = this.elasticsearchService.searchFormElasticsearch()
        json.setSuccess(true);
        return json;
    }
}
