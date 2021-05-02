package gundam.common;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
    private static final long serialVersionUID = -8195928773261747073L;

    private List<T> dataList = null;

    private Pagenation pagination = null;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Pagenation getPagination() {
        return pagination;
    }

    public void setPagination(Pagenation pagination) {
        this.pagination = pagination;
    }

}
