package gundam.pojo;

import java.sql.Timestamp;

public class FileInvokeBean {
    private Integer invokeId;
    private String fileId;
    private String fileName;
    private String resultCode;
    private String resultMsg;
    private String opType;
    private Timestamp createDate;

    public Integer getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(Integer invokeId) {
        this.invokeId = invokeId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "fileInvokeBean{" +
                "invokeId=" + invokeId +
                ", fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", opType='" + opType + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
