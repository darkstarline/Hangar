package gundam.controllers;

import com.alibaba.fastjson.JSON;
import gundam.vo.GundamVo;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.web.multipart.MultipartFile;

public class GundamStorageController {
    @Get("save")
    @Post("save")
    public JSON saveBaseInfo(Invocation inv, GundamVo gundamVo, MultipartFile file) throws Exception{

        return null;
    }
    @Post("cover")
    public JSON saveCover(Invocation inv){


        return null;
    }
}
