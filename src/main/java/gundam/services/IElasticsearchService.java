package gundam.services;

import gundam.common.Page;
import gundam.common.Pagenation;
import gundam.pojo.GundamBean;

import java.util.Map;

public interface IElasticsearchService {
    public void syncToElasticsearch(GundamBean gundamBean) throws Exception;
    public Page<Map<String, Object>> searchFormElasticsearch(GundamBean gundamBean, Pagenation pagination) throws Exception;
    public Map<String, Object> searchFormElasticsearch(GundamBean gundamBean) throws Exception;
}
