package gundam.services;

import gundam.dao.IGundamFileRelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GundamFileRelServiceImpl implements IGundamFileRelService{
    @Autowired
    IGundamFileRelDao gundamFileRelDao;
}
