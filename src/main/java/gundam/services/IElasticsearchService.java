package gundam.services;

import gundam.pojo.FileInfoBean;
import gundam.pojo.GundamBean;

public interface IElasticsearchService {
    public void syncToElasticsearch(GundamBean gundamBean) throws Exception;
    public Page<Map<String, Object>> searchFormElasticsearch(FileInfoBean fileInfo, Pagination pagination) throws Exception;
}
