package gundam.services;

import gundam.pojo.FileInfoBean;

public interface IFileInfoService {
    public void saveFileInfo(FileInfoBean fileInfoBean) throws Exception;
    public FileInfoBean getFileInfo(FileInfoBean fileInfoBean) throws Exception;
    public void completeUpload(FileInfoBean fileInfoBean) throws Exception;
}
