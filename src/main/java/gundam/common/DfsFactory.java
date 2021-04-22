package gundam.common;

import org.csource.fastdfs.ClientGlobal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.text.DecimalFormat;

public class DfsFactory {
    //TODO 修改成自己的
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static DfsFactory instance = null;

    private boolean bInit = false;

    static{
        instance = new DfsFactory();
    }

    private DfsFactory(){

    }

    public static DfsFactory getInstance(){
        if(null == instance){
            instance = new DfsFactory();
        }
        return instance;
    }

    public void init(){
        if(!bInit){
            String configFilePath = "classpath:fdfs_client.conf";
            PathMatchingResourcePatternResolver resolover = new PathMatchingResourcePatternResolver();
            Resource resource = resolover.getResource(configFilePath);
            try {
                ClientGlobal.init(resource.getInputStream());
            } catch (Exception e) {
                log.error("init fastdfs client config file error.", e);
                e.printStackTrace();
            }
            bInit=true;
        }
    }

   /* public ICmsFileInfoValue doUpload(MultipartFile file, ICmsFileInfoValue fileInfo, String encryptType, String strDefaultKey) throws Exception{
        init();
        ICmsFileInfoValue attrInfo = this.prepareAttachmentInfo(file, fileInfo);
        TrackerServer trackerServer = null;

        //TrackerClient tracker = new TrackerClient();
        //taoyf modify 业务隔离
//		TrackerClient tracker = TrackerClientRouter.instance().getTrackerClient(fileInfo.getRouterId()); //改用连接池需要注释行
        try {
//			trackerServer = tracker.getConnection();//改用连接池需要注释行

            trackerServer=TrackerClientRouter.instance().getTrackerClientFromPool(); //连接池获取trackerServer


            if(trackerServer == null){
                throw new Exception("无法连接到fastdfs主机");
            }

            StorageServer storageServer = null;
            StorageClient client = new StorageClient(trackerServer, storageServer);

            byte[] data = file.getBytes();
            if(EncryptMethod.DES.getValue().equals(encryptType)){
//				data = new DES().encrypt(data);
                data = new DES(strDefaultKey).encrypt(data);
            } else if(EncryptMethod.AES.getValue().equals(encryptType)){
//				data = new AES().encrypt(data);
                data = new AES(strDefaultKey).encrypt(data);
            }
            // results[0]: groupName, results[1]: remoteFilename.
            String[] results = client.upload_file(null, data, Files.getFileExtension(attrInfo.getFileName()), null);
            if(null == results || 2 != results.length){
                log.error("Upload file [{}] fail.", attrInfo.getFileName());
                throw new Exception("Upload file [{"+attrInfo.getFileName()+"}] fail.");
            } else {
                attrInfo.setDfsId(results[0] + "/" + results[1]);
            }
            TrackerClientRouter.instance().releaseTrackerClientToPool(trackerServer);  //连接池回收
        } catch (Exception e) {
            log.error("上传失败：",e);
            throw e;
        } finally{
            try {
                if(null != trackerServer){
//					trackerServer.close(); //改用连接池需要注释行
                }
            } catch (Exception e) {
                log.error(" tracker server close error:", e);
            }
        }
        return attrInfo;
    }*/

    /*public String doUpload(byte[] data, ICmsFileInfoValue fileInfo, String encryptType, String strDefaultKey) throws Exception{

        TrackerServer trackerServer = null;

        //taoyf modify 业务隔离
//		TrackerClient tracker = TrackerClientRouter.instance().getTrackerClient(fileInfo.getRouterId());
        try {
//			trackerServer = tracker.getConnection();
            trackerServer=TrackerClientRouter.instance().getTrackerClientFromPool(); //连接池获取trackerServer
            if(trackerServer == null){
                throw new Exception("无法连接到fastdfs主机");
            }

            StorageServer storageServer = null;
            StorageClient client = new StorageClient(trackerServer, storageServer);

            if(EncryptMethod.DES.getValue().equals(encryptType)){
                data = new DES(strDefaultKey).encrypt(data);
            } else if(EncryptMethod.AES.getValue().equals(encryptType)){
                data = new AES(strDefaultKey).encrypt(data);
            }
            String[] results = client.upload_file(null, data, Files.getFileExtension(fileInfo.getFileName()), null);
            TrackerClientRouter.instance().releaseTrackerClientToPool(trackerServer);  //连接池回收
            if(null == results || 2 != results.length){
                log.error("Upload file [{}] fail.", fileInfo.getFileName());
                throw new Exception("Upload file [{"+fileInfo.getFileName()+"}] fail.");
            } else {
                return results[0] + "/" + results[1];
            }
        } catch (Exception e) {
            log.error("上传失败：",e);
            throw e;
        } finally{
            try {
                if(null != trackerServer){
//					trackerServer.close();//改用连接池需要注释行
                }
            } catch (Exception e) {
                log.error(" tracker server close error:", e);
            }
        }
    }*/

    /*public String doUpload(String encodeTassKey, String fileName)throws Exception{
        init();
//		TrackerClient tracker = new TrackerClient();
//		TrackerServer trackerServer = tracker.getConnection();
        TrackerServer trackerServer=TrackerClientRouter.instance().getTrackerClientFromPool(); //连接池获取trackerServer
        StorageServer storageServer = null;

        StorageClient client = new StorageClient(trackerServer, storageServer);
        String[] results = client.upload_file(null, encodeTassKey.getBytes(), Files.getFileExtension(fileName), null);
        TrackerClientRouter.instance().releaseTrackerClientToPool(trackerServer);  //连接池回收
        return results[0] + "/" + results[1];
    }*/

    /*public byte[] doDownload(Long routerId, String dfsId, String encryptType, String strDefaultKey) throws Exception {
        init();
        TrackerServer trackerServer = null;

        //TrackerClient tracker = new TrackerClient();
        //taoyf modify 业务隔离
//		TrackerClient tracker = TrackerClientRouter.instance().getTrackerClient(routerId);

        try {
//			trackerServer = tracker.getConnection();
            trackerServer=TrackerClientRouter.instance().getTrackerClientFromPool(); //连接池获取trackerServer
            StorageServer storageServer = null;
            StorageClient client = new StorageClient(trackerServer, storageServer);

            String groupName = dfsId.substring(0, dfsId.indexOf("/"));
            byte[] data = client.download_file(groupName, dfsId.substring(groupName.length()+1));

            if (data == null || data.length == 0) {
                log.error("----dfsId:" + dfsId + "------第一次data数组长度为空") ;
                data = client.download_file(groupName, dfsId.substring(groupName.length()+1));
            }
            log.error("----dfsId:" + dfsId + "------解密前data数组长度：" + (data == null ? "数据为空" : data.length )) ;
            if(EncryptMethod.DES.getValue().equals(encryptType) && StringUtils.isNotBlank(strDefaultKey)){
//				data = new DES().decrypt(data);
                data = new DES(strDefaultKey).decrypt(data);
            } else if(EncryptMethod.AES.getValue().equals(encryptType) && StringUtils.isNotBlank(strDefaultKey)){
//				data = new AES().decrypt(data);
                data = new AES(strDefaultKey).decrypt(data);
            }
            TrackerClientRouter.instance().releaseTrackerClientToPool(trackerServer);  //连接池回收
            return data;

        } catch (Exception e) {
            log.error("下载失败",e);
            throw e;
        } finally{
            try {
                trackerServer.close();//改用连接池需要注释行
            } catch (Exception e) {
                log.error(" tracker server close error:", e);
            }
        }
    }*/

   /* public byte[] doDownload(String dfsId)throws Exception{
        init();
//		TrackerClient tracker = new TrackerClient();
//		TrackerServer trackerServer = tracker.getConnection();
        TrackerServer trackerServer=TrackerClientRouter.instance().getTrackerClientFromPool(); //连接池获取trackerServer
        StorageServer storageServer = null;
        StorageClient client = new StorageClient(trackerServer, storageServer);

        String groupName = dfsId.substring(0, dfsId.indexOf("/"));
        byte[] data = client.download_file(groupName, dfsId.substring(groupName.length()+1));
        TrackerClientRouter.instance().releaseTrackerClientToPool(trackerServer);  //连接池回收
        return data;
    }*/

   /* public ICmsFileInfoValue prepareAttachmentInfo(MultipartFile file, ICmsFileInfoValue fileInfo) throws Exception{
        String fullName = file.getOriginalFilename();
        fileInfo.setFileName(URLDecoder.decode(fullName, "UTF-8"));
        fileInfo.setContentType(file.getContentType());
        fileInfo.setFileSize(file.getSize());
        fileInfo.setSizeDesc(readableFileSize(file.getSize()));
        return fileInfo;
    }*/

    public static String readableFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
