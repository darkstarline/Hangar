package gundam.pojo;

import java.sql.Timestamp;

public class FsdfsTrackerRouterBean {
    private Integer routerId;
    private String routerName;
    private String trackerServerUrl;
    private String description;
    private Timestamp createDate;

    public Integer getRouterId() {
        return routerId;
    }

    public void setRouterId(Integer routerId) {
        this.routerId = routerId;
    }

    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    public String getTrackerServerUrl() {
        return trackerServerUrl;
    }

    public void setTrackerServerUrl(String trackerServerUrl) {
        this.trackerServerUrl = trackerServerUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "fsdfsTrackerRouterBean{" +
                "routerId=" + routerId +
                ", routerName='" + routerName + '\'' +
                ", trackerServerUrl='" + trackerServerUrl + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
