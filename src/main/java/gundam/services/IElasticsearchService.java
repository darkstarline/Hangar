package gundam.services;

import gundam.pojo.FileInfoBean;

public interface IElasticsearchService {
    public void syncToElasticsearch(FileInfoBean fileInfo) throws Exception;
//    public Page<Map<String, Object>> searchFormElasticsearch(FileInfoBean fileInfo, Pagination pagination, String searchAfter) throws Exception;
}
