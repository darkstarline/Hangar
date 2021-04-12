package gundam.controllers;

import com.google.gson.Gson;
import gundam.vo.GundamVo;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Post;

public class GundamStorageController {
    @Post("save")
    public String saveBaseInfo(Invocation inv, GundamVo gundamVo) throws Exception{
        gundamVo.toString();
        System.out.println("ttttt");
        return "test outside";
    }
    @Post("cover")
    public Gson saveCover(){
        Gson json=new Gson();
        return null;
    }
}
