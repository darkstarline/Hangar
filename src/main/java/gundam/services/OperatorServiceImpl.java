package gundam.services;

import gundam.common.JsonResult;
import gundam.constans.ResourceConstans;
import gundam.dao.IOperatorDao;
import gundam.pojo.OperatorBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorServiceImpl implements IOperatorService{
    @Autowired
    IOperatorDao operatorDao;

    @Override
    public JsonResult login(OperatorBean bean) throws Exception{
//        OperatorBean bean=new OperatorBean();
        JsonResult json=new JsonResult();
        OperatorBean beanFind=operatorDao.getBean(bean);
        if(beanFind == null){
            json.setSuccess(false);
            json.setMessage("用户名或者密码错误!", null);
        }else{
            if(ResourceConstans.OPERATOR.LOCK_FLAG_YES == beanFind.getLockFlag()){
                json.setSuccess(false);
                json.setMessage("该用户已经被锁定了, 无法登陆!", null);
            }

            if(!ResourceConstans.OPERATOR.STATE_USED.equals(beanFind.getState())){
                json.setSuccess(false);
                json.setMessage("该用户已经失效, 无法登陆!", null);
            }

			/*MD5 md5 = new MD5();
			String md5Password = md5.toDigest(password);*/

            if(bean.getPassword().equals(beanFind.getPassword())){
                //if(md5Password.equals(bean.getPassword())){
                json.setSuccess(true);
                json.put("operator", beanFind);
            }else{
                json.setSuccess(false);
                json.setMessage("用户名或者密码错误!", null);
            }
        }
        return json;
    }
}
