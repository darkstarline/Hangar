package gundam.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import gundam.common.Page;
import gundam.common.Pagenation;
import gundam.constans.AppConstans;
import gundam.dto.ESResult;
import gundam.pojo.GundamBean;
import gundam.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ElasticsearchServiceImpl implements IElasticsearchService{
    private static volatile boolean esEnable=false;
    private static volatile String esBaseUrl="";
    @Override
    public void syncToElasticsearch(GundamBean gundamBean) throws Exception {
        //TODO 静态参数表数据加载到redis缓存中。。。。。。。。
        //TODO 初始化查表然后接入redis缓存
        //TODO 连接池类在spring初始化时加载
        if(esEnable) {
            try {
//                String esBaseUrl = this.dictSV.getDictValue(AppConstants.URL, "elasticsearch_url");
                //結果还是得查表
                /** 同步到ES **/
                String url = esBaseUrl + "/" + AppConstans.GUNDAM + "/_doc" + "?pretty";
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                StringBuilder sb = new StringBuilder();
                sb.append("{");
                sb.append("\"organismNumber\" : \"").append(gundamBean.getOrganismNumber()).append("\",");
                sb.append("\"organismCodeName\" : \"").append(gundamBean.getOrganismCodeName()).append("\",");
                sb.append("\"jpnName\" : \"").append(gundamBean.getJpnName()).append("\",");
                sb.append("\"enName\" : \"").append(gundamBean.getEnName()).append("\",");
                sb.append("\"cnName\" : \"").append(gundamBean.getCnName()).append("\",");
                sb.append("\"animation\" : \"").append(gundamBean.getAnimation()).append("\",");
                sb.append("\"organismType\" : \"").append(gundamBean.getOrganismType()).append("\",");
                sb.append("\"belong\" : \"").append(gundamBean.getBelong()).append("\",");
                sb.append("\"manufacturer\" : \"").append(gundamBean.getManufacturer()).append("\",");
                sb.append("\"organismSieze\" : \"").append(gundamBean.getOrganismSieze()).append("\",");
                sb.append("\"netWeight\" : \"").append(gundamBean.getNetWeight()).append("\",");
                sb.append("\"fullWeight\" : \"").append(gundamBean.getFullWeight()).append("\",");
                sb.append("\"armoredStructure\" : \"").append(gundamBean.getArmoredStructure()).append("\",");
                sb.append("\"output\" : \"").append(gundamBean.getOutput()).append("\",");
                sb.append("\"propulsion\" : \"").append(gundamBean.getPropulsion()).append("\",");
                sb.append("\"acceleration\" : \"").append(gundamBean.getAcceleration()).append("\",");
                sb.append("\"sensorRadius\" : \"").append(gundamBean.getSensorRadius()).append("\",");
                sb.append("\"fixedArmed\" : \"").append(gundamBean.getFixedArmed()).append("\",");
                sb.append("\"dubut\" : \"").append(gundamBean.getDubut()).append("\",");
                sb.append("\"cockpit\" : \"").append(gundamBean.getCockpit()).append("\",");
                sb.append("\"pilot\" : \"").append(gundamBean.getPilot()).append("\",");
                sb.append("\"chooseWeapons\" : \"").append(gundamBean.getChooseWeapons()).append("\",");
                sb.append("\"degreeTime\" : \"").append(gundamBean.getDegreeTime()).append("\",");
                sb.append("\"groundSpeed\" : \"").append(gundamBean.getGroundSpeed()).append("\",");
                sb.append("\"waterSpeed\" : \"").append(gundamBean.getWaterSpeed()).append("\",");
                sb.append("\"introduction\" : \"").append(gundamBean.getIntroduction()).append("\",");
//                sb.append("\"cover\" : \"").append(gundamBean.getCover()).append("\",");
//                sb.append("\"state\" : \"").append(gundamBean.getState()).append("\",");
//                sb.append("\"opCode\" : \"").append(gundamBean.getOpCode()).append("\",");

                try {
                    String createDate = df.format(gundamBean.getCreateDate());
//                    sb.append("\"createDate\" : \"").append(createDate).append("\",");
                    sb.append("\"createDate\" : \"").append(createDate);
                } catch (Exception e) {
//                    sb.append("\"createDate\" : \"").append(df.format(new Date())).append("\",");
                    sb.append("\"createDate\" : \"").append(df.format(new Date()));
                }
                sb.append("}");
                String result = HttpUtils.doJsonPost(url, sb.toString(), "UTF-8");
            } catch (Exception e) {

            }
        }
    }

    @Override
    public Page<Map<String, Object>> searchFormElasticsearch(GundamBean gundamBean, Pagenation pagination) throws Exception {
        //TODO 待改
        //检索用
        /*Page<Map<String, Object>> page = new Page<Map<String, Object>>();
        if(pagination.getPageSize()==0){
            pagination.setPageSize(100);
        }
        if(pagination.getCurPage()==0){
            pagination.setCurPage(1);
        }
        if(esEnable){
            String url = esBaseUrl + "/" + AppConstans.GUNDAM + "/_doc" + "?pretty";

            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"query\"").append(":").append("{");
            sb.append("\"bool\"").append(":").append("{");
            sb.append("\"must\"").append(":").append("[");
            if(StringUtils.isNotEmpty(fileInfoValue.getSystemId())){
                sb.append("{").append("\"term\"").append(":").append("{");
                sb.append("\"systemId\"").append(":").append("\"").append(fileInfoValue.getSystemId()).append("\"");
                sb.append("}").append("}");
                sb.append(",");
            }
            sb.append("]").append(",");
            sb.append("\"should\"").append(":").append("[");
            if(StringUtils.isNotEmpty(fileInfoValue.getFileId())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"fileName\"").append(":").append("\"*").append(fileInfoValue.getFileName()).append("*\"").append("}").append("}");
            }
            sb.append("]").append(",");
            //mininum_should_match默认就是1,设置成0没有意义
//            sb.append("\"minimum_should_match\"").append(":").append(0).append(",");
            sb.append("\"boost\"").append(":").append(1.0);
            sb.append("}");
//            sb.append(",");
            sb.append("}").append(",");
            sb.append("\"size\"").append(":").append(pagination.getPageSize()).append(",");
        }*/
        return null;
    }
    @Override
    public Map<String, Object> searchFormElasticsearch(GundamBean gundamBean) throws Exception {
        //自用
        List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
        if(esEnable){
            String url = esBaseUrl + "/" + AppConstans.GUNDAM + "/_search" + "?pretty";

            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"query\"").append(":").append("{");
            sb.append("\"bool\"").append(":").append("{");
            sb.append("\"must\"").append(":").append("[");
            if(StringUtils.isNotEmpty(gundamBean.getOrganismCodeName())){
                sb.append("{").append("\"term\"").append(":").append("{");
                sb.append("\"organismCodeName\"").append(":").append("\"").append(gundamBean.getOrganismCodeName()).append("\"");
                sb.append("}").append("}").append(",");
            }
            //之后又需要再写
            sb.append("]").append(",");
            sb.append("\"boost\"").append(":").append(1.0);
            sb.append("}").append("}").append("}");
            String result = HttpUtils.doJsonPost(url, sb.toString(), "UTF-8");
            JSONObject jsonResult = JSON.parseObject(result);
            ESResult esResult=ESResult.originalParse(jsonResult);
            resultMapList = esResult.getHits();
        }
        return resultMapList.get(0);
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
        //TODO 从redis中获取参数
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
