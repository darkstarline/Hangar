package gundam.services;

import gundam.constans.ResourceConstans;
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

    @Override
    public GundamFileRelBean getBean(GundamFileRelBean gundamFileRel) throws Exception {
        gundamFileRel.setState(ResourceConstans.STATE.USED);
//        List<GundamFileRelBean> list = gundamFileRelDao.getBeans(gundamFileRel);
//        return list.get(0);
        return gundamFileRelDao.getBeans(gundamFileRel);
    }
}
