package gundam.services;

import gundam.common.JsonResult;
import gundam.pojo.OperatorBean;

public interface IOperatorService {
    public JsonResult login(OperatorBean bean) throws Exception;
}
