package gundam.dao;

import gundam.pojo.GundamFileRelBean;

public interface IGundamFileRelDao {
    public void saveGundamFileRel(GundamFileRelBean gundamFileRelBean) throws Exception;

    GundamFileRelBean getBeans(GundamFileRelBean gundamFileRel) throws Exception;
}
