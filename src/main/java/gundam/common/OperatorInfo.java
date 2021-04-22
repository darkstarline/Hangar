package gundam.common;

import java.io.Serializable;

public class OperatorInfo implements Serializable {
    //TODO 暂时先这么多，有需要再补
    private Integer id;
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String isAdmin;
    private String createDate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "OperatorInfo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
