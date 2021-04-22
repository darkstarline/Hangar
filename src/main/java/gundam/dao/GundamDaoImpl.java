package gundam.dao;

import gundam.pojo.GundamBean;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GundamDaoImpl implements IGundamDao{
    private SqlSessionFactory factory;
    public GundamDaoImpl(SqlSessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void saveGundam(GundamBean gundamBean) throws Exception {
        SqlSession session = factory.openSession();
        session.insert("gundam.save",gundamBean);
        session.close();
    }
}
