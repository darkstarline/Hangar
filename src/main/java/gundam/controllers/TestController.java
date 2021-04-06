package gundam.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Post;


public class TestController {


    @Post("t3")
    public String ttt(Invocation inv) throws Exception{
        System.out.println("ttttt");
        return "test outside";
    }

}
