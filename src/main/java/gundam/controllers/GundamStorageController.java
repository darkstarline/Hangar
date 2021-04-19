package gundam.controllers;


import com.alibaba.fastjson.JSON;
import gundam.common.JsonResult;
import gundam.vo.GundamVo;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.web.multipart.MultipartFile;

public class GundamStorageController {
    @Get("save")
    @Post("save")
    public JSON saveBaseInfo(Invocation inv, GundamVo gundamVo, MultipartFile file) throws Exception{
        JsonResult json=new JsonResult();
        json.setSuccess(true);
        return json;
    }
    @Post("cover")
    public JSON saveCover(Invocation inv){
        //TODO 用户登录

        return null;
    }
}
