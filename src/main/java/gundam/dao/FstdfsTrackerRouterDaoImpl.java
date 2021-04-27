package gundam.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FstdfsTrackerRouterDaoImpl implements IFstdfsTrackerRouterDao{
    private SqlSessionFactory factory;
    public FstdfsTrackerRouterDaoImpl(SqlSessionFactory factory){
        this.factory=factory;
    }
}
