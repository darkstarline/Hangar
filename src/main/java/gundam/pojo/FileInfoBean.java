package gundam.pojo;

import java.sql.Timestamp;

public class FileInfoBean {
    private String fileId;
    private String dfsId;
    private String fileName;
    private String fileStatus;
    private String contentType;
    private String fileSize;
    private String sizeDesc;
    private Timestamp createDate;
    private String opCode;
    private String state;
    private Integer routerId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getDfsId() {
        return dfsId;
    }

    public void setDfsId(String dfsId) {
        this.dfsId = dfsId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getSizeDesc() {
        return sizeDesc;
    }

    public void setSizeDesc(String sizeDesc) {
        this.sizeDesc = sizeDesc;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getRouterId() {
        return routerId;
    }

    public void setRouterId(Integer routerId) {
        this.routerId = routerId;
    }

    @Override
    public String toString() {
        return "fileInfoBean{" +
                "fileId='" + fileId + '\'' +
                ", dfsId='" + dfsId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileStatus='" + fileStatus + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", sizeDesc='" + sizeDesc + '\'' +
                ", createDate=" + createDate +
                ", opCode='" + opCode + '\'' +
                ", state='" + state + '\'' +
                ", routerId=" + routerId +
                '}';
    }
}
