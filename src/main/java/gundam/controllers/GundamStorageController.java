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
        //TODO 先保存信息，再同步到ES，同时fastDFS保存文件回写数据库
        //TODO 明天就先把登录搞了吧，这个熟悉。
        //TODO 先试试mybatis能不能保存到数据库
        //TODO 阿里连接池的集成
        return null;
    }
    @Post("cover")
    public JSON saveCover(Invocation inv){


        return null;
    }
}
