package gundam.dao;

import gundam.pojo.Test;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestImpl implements ITest{
    private SqlSessionFactory factory;
    public TestImpl(SqlSessionFactory factory){
        this.factory=factory;
    }
    @Override
    public List<Test> testSelect() {
        SqlSession session = factory.openSession();
        List<Test> test= session.selectList("test.testSelect");
        session.close();
        return null;
    }
}
