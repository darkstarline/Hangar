import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class zsbtest {
    @Test
    public void  list2Json(){
        List<testPojo> list=new ArrayList<testPojo>();
        testPojo one=new testPojo();
        testPojo two=new testPojo();
        testPojo three=new testPojo();
        list.add(one);
        list.add(two);
        list.add(three);
        Gson gson=new Gson();
        String getList=gson.toJson(list);
        System.out.println(getList);
    }
}
