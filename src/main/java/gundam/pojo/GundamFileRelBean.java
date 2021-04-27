package gundam.pojo;

import java.sql.Timestamp;

public class GundamFileRelBean {
    private String fileId;
    private String dfsId;
    private String organismNumber;
    private String fileType;
    private Timestamp createDate;
    private String state;

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

    public String getOrganismNumber() {
        return organismNumber;
    }

    public void setOrganismNumber(String organismNumber) {
        this.organismNumber = organismNumber;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "gundamFileRelBean{" +
                "fileId='" + fileId + '\'' +
                ", dfsId='" + dfsId + '\'' +
                ", organismNumber='" + organismNumber + '\'' +
                ", fileType='" + fileType + '\'' +
                ", createDate=" + createDate +
                ", state='" + state + '\'' +
                '}';
    }
}
