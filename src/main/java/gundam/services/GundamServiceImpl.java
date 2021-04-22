package gundam.services;

import gundam.dao.IGundamDao;
import gundam.pojo.GundamBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GundamServiceImpl implements IGundamService {
    @Autowired
    IGundamDao gundamDao;
    @Override
    public void aircraftStorage(GundamBean gundamBean) throws Exception {
        gundamDao.saveGundam(gundamBean);
    }
}
