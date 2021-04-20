package gundam.controllers;

import com.alibaba.fastjson.JSON;
import gundam.common.JsonResult;
import gundam.common.OperatorInfo;
import gundam.common.UserSession;
import gundam.constans.AppConstans;
import gundam.pojo.OperatorBean;
import gundam.services.IOperatorService;
import gundam.utils.StringUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

@Path("/")
public class LoginController {
    @Autowired
    IOperatorService operatorService;
    private static String USER="user";
    private static String PASSWORD="password";
    private static String OPERATOR="operator";
    @Post("login")
    public JSON login(Invocation inv) throws Exception{
        JsonResult json=new JsonResult();
        String user=inv.getParameter(USER);
        String password=inv.getParameter(PASSWORD);
        if(StringUtils.isEmpty(user)||StringUtils.isEmpty(password)) {
            json.setSuccess(false);
            json.setMessage("用户名/邮箱或密码不全");
            return json;
        }
        OperatorBean bean = new OperatorBean();
        if(StringUtils.isEmail(user)){
            bean.setEmail(user);
        }else{
            bean.setCode(user);
        }
        bean.setPassword(password);
       JsonResult jsonResult=operatorService.login(bean);
       if(jsonResult.isSuccess()){
           OperatorBean operatorBean= (OperatorBean) jsonResult.get(OPERATOR);
           OperatorInfo operatorInfo= new OperatorInfo();
           operatorInfo.setCode(operatorBean.getCode());
           operatorInfo.setIsAdmin(operatorBean.getIsAdmin());
           operatorInfo.setCreateDate(operatorBean.getCreateDate().toString());

           UserSession userSession = new UserSession();
           userSession.setOperatorInfo(operatorInfo);
           HttpSession session = inv.getRequest().getSession();
           inv.getRequest().getSession().setAttribute(AppConstans.USER_SESSION, userSession);
       }
        return jsonResult;
    }
}
