package gundam.services;

import gundam.pojo.FileInfoBean;
import gundam.pojo.FileInvokeBean;

public interface IFileInvokeService {
    public void saveFileInvoke(FileInvokeBean fileInvokeBean) throws Exception;
}
