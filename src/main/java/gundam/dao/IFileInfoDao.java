package gundam.dao;

import gundam.pojo.FileInfoBean;

public interface IFileInfoDao {
    public void saveFileInfo(FileInfoBean fileInfoBean) throws Exception;
    public FileInfoBean getFileInfo(FileInfoBean fileInfoBean) throws Exception;
}
