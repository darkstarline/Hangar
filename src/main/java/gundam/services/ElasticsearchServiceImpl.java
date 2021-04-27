package gundam.services;

import gundam.constans.AppConstans;
import gundam.pojo.FileInfoBean;
import gundam.utils.HttpUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class ElasticsearchServiceImpl implements IElasticsearchService{
    private static volatile boolean esEnable=false;
    private static volatile String esBaseUrl="";
    @Override
    public void syncToElasticsearch(FileInfoBean fileInfo) throws Exception {
        //TODO 静态参数表数据加载到redis缓存中。。。。。。。。
        if(esEnable) {
            try {
//                String esBaseUrl = this.dictSV.getDictValue(AppConstants.URL, "elasticsearch_url");
                //結果还是得查表
                /** 同步到ES **/
                String url = esBaseUrl + "/" + AppConstans.GUNDAM + "/_doc" + "?pretty";
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                StringBuilder sb = new StringBuilder();
                sb.append("{");
//                sb.append("\"fileName\" : \"").append(fileInfo.getFileName()).append("\",");
//                sb.append("\"fileId\" : \"").append(fileInfo.getFileId()).append("\",");
//                sb.append("\"systemId\" : \"").append(fileInfo.getSystemId()).append("\",");
//                sb.append("\"catalogId\" : \"").append(fileInfo.getCatalogId()).append("\",");
//                sb.append("\"busiType\" : \"").append(fileInfo.getBusiType()).append("\",");
//                sb.append("\"contentType\" : \"").append(fileInfo.getContentType()).append("\",");
//                sb.append("\"description\" : \"").append(fileInfo.getDescription()).append("\",");
//                sb.append("\"sizeDesc\" : \"").append(fileInfo.getSizeDesc()).append("\",");
                /*try {
                    String createDate = df.format(fileInfo.getCreateDate());
                    sb.append("\"createDate\" : \"").append(createDate).append("\",");
                } catch (Exception e) {
                    sb.append("\"createDate\" : \"").append(df.format(new Date())).append("\",");
                }
                try {
                    String doneDate = df.format(fileInfo.getDoneDate());
                    sb.append("\"doneDate\" : \"").append(doneDate).append("\"");
                } catch (Exception e) {
                    sb.append("\"doneDate\" : \"").append(df.format(new Date())).append("\"");
                }*/
                sb.append("}");
                String result = HttpUtils.doJsonPost(url, sb.toString(), "UTF-8");
            } catch (Exception e) {

            }
        }
    }
    static {
        try {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        String enable=isEnable();
                        esEnable=Boolean.parseBoolean(enable);
                    } catch (Exception e) {
                        esEnable=false;
                        e.printStackTrace();
                    }
                }
            },0,1000*1800);
        }catch (Exception e){
            System.out.println("定时扫描线程启动失败");
        }
    }
    public static String isEnable() throws Exception {
        /*IBaseSV objIBaseSV = (IBaseSV) ServiceFactory.getService(IBaseSV.class);
        IBOBsStaticDataValue[] objIBSStaticDataValue = objIBaseSV.getAllBsStaticData();
        HashMap codeTypeMap = new HashMap();

        for(int i = 0; i < objIBSStaticDataValue.length; ++i) {
            if(AppConstants.SWITCH.equals(objIBSStaticDataValue[i].getCodeType())&&"es_enable".equals(objIBSStaticDataValue[i].getCodeValue())) {
                return objIBSStaticDataValue[i].getCodeName();
            }
        }*/
        return null;
    }
}
