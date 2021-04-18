package gundam.controllers;

import gundam.services.TestService;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;


public class TestController {

    @Autowired
    TestService testService;

    @Post("t3")
    public String ttt(Invocation inv) throws Exception{
        System.out.println("ttttt");
        testService.testService();
        return "test outside";
    }

}
