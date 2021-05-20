package gundam.dao;

import gundam.pojo.GundamFileRelBean;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public GundamFileRelBean getBeans(GundamFileRelBean gundamFileRel) throws Exception {
        SqlSession session = factory.openSession();
        List<GundamFileRelBean> fileInfo=session.selectList("gundamFileRel.getBeans",gundamFileRel);
        session.close();
        return fileInfo.get(0);
    }
}
