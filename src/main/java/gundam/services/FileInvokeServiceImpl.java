package gundam.services;

import gundam.dao.IFileInvokeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileInvokeServiceImpl implements IFileInvokeService{
    @Autowired
    IFileInvokeDao fileInvokeDao;
}
