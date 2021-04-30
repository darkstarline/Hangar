package gundam.dao;

import gundam.constans.ResourceConstans;
import gundam.pojo.BsStaticData;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BsStaticDataImpl implements IBsStaticData{
    private SqlSessionFactory factory;
    public BsStaticDataImpl(SqlSessionFactory factory){
        this.factory=factory;
    }

    @Override
    public List<BsStaticData> getAllBsStaticData() throws Exception {
        SqlSession session = factory.openSession();
        BsStaticData bsStaticData = new BsStaticData();
        bsStaticData.setState(ResourceConstans.STATE.USED);
        List<BsStaticData> bs= session.selectList("bsStaticData.getBeans",bsStaticData);
        session.close();
        return bs;
    }
}
