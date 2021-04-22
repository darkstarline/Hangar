package gundam.controllers;


import com.alibaba.fastjson.JSON;
import gundam.common.JsonResult;
import gundam.pojo.GundamBean;
import gundam.services.IGundamService;
import gundam.vo.GundamVo;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class GundamStorageController {
    @Autowired
    IGundamService gundamService;
    @Post("save")
    public JSON saveBaseInfo(Invocation inv, GundamVo gundamVo, MultipartFile file) throws Exception{
        JsonResult json=new JsonResult();


        GundamBean gundamBean = new GundamBean();
//        gundamBean.setOrgamismId(1);
        gundamBean.setOrganismCodeName("666");
        gundamService.aircraftStorage(gundamBean);

        json.setSuccess(true);
        return json;
    }
    @Post("cover")
    public JSON saveCover(Invocation inv){
        //TODO 用户登录

        return null;
    }
}
