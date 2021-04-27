package gundam.services;

import gundam.constans.ResourceConstans;
import gundam.dao.IFileInfoDao;
import gundam.pojo.FileInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileInfoServiceImpl implements IFileInfoService{
    @Autowired
    IFileInfoDao fileInfoDao;

    @Override
    public void saveFileInfo(FileInfoBean fileInfoBean) throws Exception{
        fileInfoDao.saveFileInfo(fileInfoBean);
    }

    @Override
    public FileInfoBean getFileInfo(FileInfoBean fileInfoBean) throws Exception {
        return fileInfoDao.getFileInfo(fileInfoBean);
    }

    @Override
    public void completeUpload(FileInfoBean fileInfo) throws Exception {
        fileInfo.setFileStatus(ResourceConstans.FILESTATUS.IN_USE);
        fileInfoDao.saveFileInfo(fileInfo);
    }
}
