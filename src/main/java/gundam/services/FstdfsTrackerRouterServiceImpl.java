package gundam.services;

import gundam.dao.IFstdfsTrackerRouterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FstdfsTrackerRouterServiceImpl implements IFstdfsTrackerRouterService{
    @Autowired
    IFstdfsTrackerRouterDao fstdfsTrackerRouterDao;
}
