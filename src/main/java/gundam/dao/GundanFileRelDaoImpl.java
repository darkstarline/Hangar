package gundam.dao;

import gundam.pojo.GundamFileRelBean;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GundanFileRelDaoImpl implements IGundamFileRelDao {
    private SqlSessionFactory factory;
    public GundanFileRelDaoImpl(SqlSessionFactory factory){
        this.factory=factory;
    }

    @Override
    public void saveGundamFileRel(GundamFileRelBean gundamFileRelBean) throws Exception {
        SqlSession session = factory.openSession();
        session.insert("gundamFileRel.save",gundamFileRelBean);
        session.close();
    }
}
