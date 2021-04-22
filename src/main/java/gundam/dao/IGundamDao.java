package gundam.dao;

import gundam.pojo.GundamBean;

public interface IGundamDao {
    public void saveGundam(GundamBean gundamBean) throws Exception;
}
