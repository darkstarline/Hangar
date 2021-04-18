package gundam.services;

import gundam.dao.ITest;
import gundam.pojo.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ITestServiceImpl implements TestService{
    @Autowired
    private ITest itest;

    public void testService(){
        List<Test> test=itest.testSelect();
        System.out.println(test.toString());
    }
}
