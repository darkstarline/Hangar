package gundam.dao;

import gundam.pojo.OperatorBean;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OperatorDaoImpl implements IOperatorDao {
    private SqlSessionFactory factory;
    public OperatorDaoImpl(SqlSessionFactory factory){
        this.factory=factory;
    }
    @Override
    public OperatorBean getBean(OperatorBean operator) throws Exception {
        SqlSession session = factory.openSession();
        OperatorBean op= session.selectOne("operator.getBean",operator);
        session.close();
        return op;
    }
}
