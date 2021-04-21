package gundam.common;

import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TrackerClientRouter {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static TrackerClientRouter instance;

    private TrackerGroup defaultTrackerGroup;
    private Map<Long, TrackerGroup> trackerGroupRouter;


    //	暂行线程池
    private FastDFSConnPool fastDFSConnPool;
    private boolean USE_New=false;   //true : 每次建立新连接    false:用线程池

    private TrackerClientRouter() {

        init();

    }

    private void init() {
        if(USE_New) {
            /*// 初始化 默认集群
            defaultTrackerGroup = ClientGlobal.getG_tracker_group();

            // 初始化 全部集群
            trackerGroupRouter = new HashMap<Long, TrackerGroup>();
            IFastdfsRouterSV fastdfsRouterSv = (IFastdfsRouterSV) ServiceFactory.getService(IFastdfsRouterSV.class);
            try {
//			FstdfsTrackerRouterBean[] routerBeans = fastdfsRouterSv.getFastdfsRouters();
                FstdfsTrackerRouterBean routerBean = new FstdfsTrackerRouterBean();
                routerBean.setRouterId(0);
                routerBean.setRouterName("本机测试");
                routerBean.setTrackerServerGroupUrl("192.168.79.213:22122");
                routerBean.setState("U");
                FstdfsTrackerRouterBean[] routerBeans = new FstdfsTrackerRouterBean[]{routerBean};
                if (routerBeans != null) {
                    for (FstdfsTrackerRouterBean one : routerBeans) {

                        long routerId = one.getRouterId();
                        String trackerServerGroupUrl = one.getTrackerServerGroupUrl();
                        TrackerGroup trackerGroup = structureTrackerGroup(trackerServerGroupUrl);

                        trackerGroupRouter.put(routerId, trackerGroup);

                    }
                }

            } catch (Exception e) {
                log.error("获取fastdfs 路由信息错误", e);
            }*/
        }else {
//		初始化线程池
            try {
                Properties props = new Properties();
                props= PropertiesLoaderUtils.loadAllProperties("fastDFSConnPool.properties");

                fastDFSConnPool = new FastDFSConnPool()
//						.confFileName("./././././fdfs_client.conf")
                        .maxPoolSize(Integer.parseInt(props.getProperty("maxPoolSize")))   //初始化线程池参数,之后改到参数文件里进行配置
                        .minPoolSize(Integer.parseInt(props.getProperty("minPoolSize")))
                        .reConnNum(Integer.parseInt(props.getProperty("reConnNum")))
                        .waitTimes(Integer.parseInt(props.getProperty("waitTimes"))).build();
            } catch (Exception e) {
                log.error("初始化fastDFS线程池失败", e);
            }
        }
    }

    private TrackerGroup structureTrackerGroup(String trackerServers) {
        List<InetSocketAddress> list = new ArrayList<InetSocketAddress>();
        String spr1 = ",";
        String spr2 = ":";
        String[] arr1 = trackerServers.trim().split(spr1);
        for (String addrStr : arr1) {
            String[] arr2 = addrStr.trim().split(spr2);
            String host = arr2[0].trim();
            int port = Integer.parseInt(arr2[1].trim());
            list.add(new InetSocketAddress(host, port));
        }
        InetSocketAddress[] trackerAddresses = list.toArray(new InetSocketAddress[list.size()]);
        return new TrackerGroup(trackerAddresses);
    }

    public void refreshConfig() {
        init();
    }

    public static TrackerClientRouter instance() {

        if (instance == null) {
            synchronized (TrackerClientRouter.class) {

                if (instance == null) {
                    instance = new TrackerClientRouter();
                }
            }
        }

        return instance;
    }


    public TrackerClient getTrackerClient(Long routerId){

        TrackerGroup trackerGroup = trackerGroupRouter.get(routerId);
        if(trackerGroup == null){
            trackerGroup = defaultTrackerGroup;
        }

        return new TrackerClient(trackerGroup);

    }

    public TrackerServer getTrackerClientFromPool(){
        return fastDFSConnPool.checkOut();
    }
    public void releaseTrackerClientToPool(TrackerServer trackerServer){
        fastDFSConnPool.checkIn(trackerServer);
    }
}
