package gundam.common;

import org.apache.commons.httpclient.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * _______________HHHHHHHHH_______________________
 * ______________HHHHHHHHHHHH_____________________
 * ______________HHHHHHHHHHHHH____________________
 * _____________HH__HHHHHHHHHHH___________________
 * ____________HHH__HHHHHH_HHHHH__________________
 * ____________HHH_HHHHHHH___HHHH_________________
 * ___________HHH__HHHHHHHHHH_HHHH________________
 * __________HHHH__HHHHHHHHHHH_HHHH_______________
 * ________HHHHH___HHHHHHHHHHH__HHHHH_____________
 * _______HHHHHH___HHH_HHHHHHHH___HHHHH___________
 * _______HHHHH___HHH___HHHHHHHH___HHHHHH_________
 * ______HHHHHH___HHH__HHHHHHHHHHH___HHHHHH_______
 * _____HHHHHH___HHHH_HHHHHHHHHHHHHH__HHHHHH______
 * ____HHHHHHH__HHHHHHHHHHHHHHHHHHHHH_HHHHHHH_____
 * ____HHHHHHH__HHHHHHHHHHHHHHHHHHHHHHHHHHHHHH____
 * ___HHHHHHH__HHHHHH_HHHHHHHHHHHHHHHHH_HHHHHHH___
 * ___HHHHHHH__HHHHHH_HHHHHH_HHHHHHHHH___HHHHHH___
 * ___HHHHHHH____HH__HHHHHH___HHHHHH_____HHHHHH___
 * ___HHHHHHH________HHHHHH____HHHHH_____HHHHH____
 * ____HHHHHH________HHHHH_____HHHHH_____HHHH_____
 * _____HHHHH________HHHH______HHHHH_____HHH______
 * ______HHHHH______;HHH________HHH______H________
 * ________HH_______HHHH________HHHH______________
 *
 * @author: darkstarline
 * @date: 2021/5/10 14:23
 * @description:
 */
public class ElasticsearchNodes {
    //参考Elasticsearch  RestHighLevel里获取连接的方式(超简易版本)
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static ElasticsearchNodes instance = null;

    private volatile List<HttpHost> nodesList = new ArrayList<HttpHost>();
    private final AtomicInteger lastNodeIndex = new AtomicInteger(0);

    private boolean bInit = false;
    private IDictSV dictSV = (IDictSV) ServiceFactory.getService(IDictSV.class);
    static{
        instance = new ElasticsearchNodes();
    }

    private ElasticsearchNodes(){

    }

    public static ElasticsearchNodes getInstance(){
        if(null == instance){
            instance = new ElasticsearchNodes();
        }
        return instance;
    }

    public void init(){
        if(!bInit){
            String esBaseUrl = this.dictSV.getDictValue(AppConstants.URL, "elasticsearch_url");
            //无增删该需求,Arrays.asList可行
            List<String> esUrls = Arrays.asList(esBaseUrl.split(","));
            for(String url : esUrls){
                String[] httpAndPort = url.split(":");
                HttpHost httpHost = new HttpHost(httpAndPort[0],Integer.parseInt(httpAndPort[1]), "http");
                this.nodesList.add(httpHost);
            }
            bInit=true;
        }
    }

    public String nextNodesString() throws Exception{
        this.init();
        HttpHost httpHost = this.nextNodes();
        return httpHost.getSchemeName()+"://"+httpHost.getHostName()+":"+httpHost.getPort();
    }

    public HttpHost nextNodes() throws Exception{
        this.init();
        List<HttpHost> nodeList = this.nodesList;
        return selectNodes(nodeList ,this.lastNodeIndex);
    }

    //TODO 待改进,即其中一个node挂了加入deadNodes,使用时不影响业务
    static HttpHost selectNodes(List<HttpHost> nodeList ,AtomicInteger lastNodeIndex) throws Exception {
        List<HttpHost> livingNodes = new ArrayList(nodeList.size());
        Iterator var6 = ((List)nodeList).iterator();

        while(var6.hasNext()) {
            HttpHost node = (HttpHost) var6.next();
            if(node!=null && StringUtils.isNotEmpty(node.getHostName()) && node.getPort()!=0){
                livingNodes.add(node);
            }
        }
        final ArrayList selectedDeadNodes;
        if(!livingNodes.isEmpty()){
            selectedDeadNodes = new ArrayList(livingNodes);
            if (!selectedDeadNodes.isEmpty()) {
                Collections.rotate(selectedDeadNodes, lastNodeIndex.getAndIncrement());
                return (HttpHost)selectedDeadNodes.get(0);
            }
        }
//        throw new IOException("NodeSelector [" + nodeSelector + "] rejected all nodes, living " + livingNodes + " and dead " + deadNodes);
        throw new Exception("No available Elasticsearch connections");
    }

}
