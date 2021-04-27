package gundam.utils;

import gundam.common.OperatorInfo;
import gundam.constans.ResourceConstans;
import gundam.pojo.FileInfoBean;

import java.sql.Timestamp;

public class UploadUtils {
    public static FileInfoBean makeFileInfo(OperatorInfo userInfo) {
        FileInfoBean fileInfo = new FileInfoBean();
        String fileId = DataUtils.formatNow("yyyyMMdd") + SecurityUtils.getUUID();
        fileInfo.setFileId(fileId);
        fileInfo.setFileStatus(ResourceConstans.FILESTATUS.WAIT_UPLOAD);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        fileInfo.setCreateDate(now);
        fileInfo.setOpCode(userInfo.getCode());
        fileInfo.setState(ResourceConstans.STATE.USED);
        fileInfo.setRouterId(0);
        return fileInfo;
    }
}
