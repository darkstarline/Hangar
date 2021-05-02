package gundam.services;

import gundam.dao.IFileInvokeDao;
import gundam.pojo.FileInfoBean;
import gundam.pojo.FileInvokeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileInvokeServiceImpl implements IFileInvokeService{
    @Autowired
    IFileInvokeDao fileInvokeDao;
    @Override
    public void saveFileInvoke(FileInvokeBean fileInvokeBean) throws Exception{
        fileInvokeDao.saveFileInvoke(fileInvokeBean);
    }
}
