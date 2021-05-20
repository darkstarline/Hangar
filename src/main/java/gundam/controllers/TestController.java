package gundam.controllers;

import com.alibaba.fastjson.JSON;
import gundam.services.TestService;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


public class TestController {

    @Autowired
    TestService testService;

    @Post("t1")
    @Get("t1")
    public JSON ttt(Invocation inv) throws Exception{
        String tttt=inv.getRequest().getParameter("MS");
        System.out.println(tttt);
        String filePath = "/home/darkstarline/Desktop/test.jpeg";
        InputStream in = new FileInputStream(filePath);
        byte[] data = toByteArray(in);
        in.close();

//        System.out.println(Arrays.toString(data));

        HttpServletResponse response = inv.getResponse();
        OutputStream outputStream = response.getOutputStream();
        try{
            outputStream.write(data);
            outputStream.flush();
        }catch (Exception e){
            System.out.println("吊毛，上传文件失败了！"+e.toString());
        }finally {
            outputStream.close();
        }
        return null;
//        return data;
//        System.out.println("ttttt");
//        testService.testService();
//        return "test outside";
    }
    private byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
}
