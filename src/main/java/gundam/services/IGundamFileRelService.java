package gundam.services;

import gundam.pojo.GundamFileRelBean;

public interface IGundamFileRelService {
    public void saveGundamFileRel(GundamFileRelBean gundamFileRelBean) throws Exception;

    GundamFileRelBean getBean(GundamFileRelBean gundamFileRel) throws Exception;
}
