package gundam.services;

import gundam.dao.IGundamFileRelDao;
import gundam.pojo.GundamFileRelBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GundamFileRelServiceImpl implements IGundamFileRelService{
    @Autowired
    IGundamFileRelDao gundamFileRelDao;

    @Override
    public void saveGundamFileRel(GundamFileRelBean gundamFileRelBean) throws Exception {
        gundamFileRelDao.saveGundamFileRel(gundamFileRelBean);
    }
}
