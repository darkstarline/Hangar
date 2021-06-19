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
    private static volatile String esBaseUrl="http://192.168.79.213:9200";//暂时先这样，之后去redis里取
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
                    sb.append("\"createDate\" : \"").append(createDate).append("\"");
                } catch (Exception e) {
//                    sb.append("\"createDate\" : \"").append(df.format(new Date())).append("\",");
                    sb.append("\"createDate\" : \"").append(df.format(new Date())).append("\"");
                }
                sb.append("}");
                System.out.println(sb.toString());
                String result = HttpUtils.doJsonPost(url, sb.toString(), "UTF-8");
                System.out.println(result);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public Page<Map<String, Object>> searchFormElasticsearch(GundamBean gundamBean, Pagenation pagination) throws Exception {
        //TODO 待改
//        List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
        //检索用
        Page<Map<String, Object>> page = new Page<Map<String, Object>>();
        /*if(pagination.getPageSize()==0){
            pagination.setPageSize(100);
        }
        if(pagination.getCurPage()==0){
            pagination.setCurPage(1);
        }*/
        if(esEnable){

            int shouldCount = 0;

            String url = esBaseUrl + "/" + AppConstans.GUNDAM + "/_search" + "?pretty";

            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"query\"").append(":").append("{");
            sb.append("\"bool\"").append(":").append("{");
           /* sb.append("\"must\"").append(":").append("[");
            if(StringUtils.isNotEmpty(fileInfoValue.getSystemId())){
                sb.append("{").append("\"term\"").append(":").append("{");
                sb.append("\"systemId\"").append(":").append("\"").append(fileInfoValue.getSystemId()).append("\"");
                sb.append("}").append("}");
                sb.append(",");
            }
            sb.append("]").append(",");*/
            sb.append("\"should\"").append(":").append("[");
            if(StringUtils.isNotEmpty(gundamBean.getOrganismNumber())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"organismNumber\"").append(":").append("\"*").append(gundamBean.getOrganismNumber()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getOrganismNumber())&&StringUtils.isNotEmpty(gundamBean.getOrganismCodeName())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getOrganismCodeName())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"organismCodeName\"").append(":").append("\"*").append(gundamBean.getOrganismCodeName()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getOrganismCodeName())&&StringUtils.isNotEmpty(gundamBean.getJpnName())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getJpnName())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"jpnName\"").append(":").append("\"*").append(gundamBean.getJpnName()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getJpnName())&&StringUtils.isNotEmpty(gundamBean.getEnName())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getEnName())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"enName\"").append(":").append("\"*").append(gundamBean.getEnName()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getEnName())&&StringUtils.isNotEmpty(gundamBean.getCnName())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getCnName())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"cnName\"").append(":").append("\"*").append(gundamBean.getCnName()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getCnName())&&StringUtils.isNotEmpty(gundamBean.getAnimation())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getAnimation())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"animation\"").append(":").append("\"*").append(gundamBean.getAnimation()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getAnimation())&&StringUtils.isNotEmpty(gundamBean.getOrganismSieze())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getOrganismSieze())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"organismSieze\"").append(":").append("\"*").append(gundamBean.getOrganismSieze()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getOrganismSieze())&&StringUtils.isNotEmpty(gundamBean.getOrganismType())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getOrganismType())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"organismType\"").append(":").append("\"*").append(gundamBean.getOrganismType()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getOrganismType())&&StringUtils.isNotEmpty(gundamBean.getBelong())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getBelong())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"belong\"").append(":").append("\"*").append(gundamBean.getBelong()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getBelong())&&StringUtils.isNotEmpty(gundamBean.getManufacturer())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getManufacturer())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"manufacturer\"").append(":").append("\"*").append(gundamBean.getManufacturer()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getManufacturer())&&StringUtils.isNotEmpty(gundamBean.getNetWeight())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getNetWeight())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"netWeight\"").append(":").append("\"*").append(gundamBean.getNetWeight()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getNetWeight())&&StringUtils.isNotEmpty(gundamBean.getFullWeight())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getFullWeight())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"fullWeight\"").append(":").append("\"*").append(gundamBean.getFullWeight()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getFullWeight())&&StringUtils.isNotEmpty(gundamBean.getArmoredStructure())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getArmoredStructure())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"armoredStructure\"").append(":").append("\"*").append(gundamBean.getArmoredStructure()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getArmoredStructure())&&StringUtils.isNotEmpty(gundamBean.getOutput())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getOutput())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"output\"").append(":").append("\"*").append(gundamBean.getOutput()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getOutput())&&StringUtils.isNotEmpty(gundamBean.getPropulsion())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getPropulsion())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"propulsion\"").append(":").append("\"*").append(gundamBean.getPropulsion()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getPropulsion())&&StringUtils.isNotEmpty(gundamBean.getAcceleration())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getAcceleration())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"acceleration\"").append(":").append("\"*").append(gundamBean.getAcceleration()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getAcceleration())&&StringUtils.isNotEmpty(gundamBean.getSensorRadius())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getSensorRadius())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"sensorRadius\"").append(":").append("\"*").append(gundamBean.getSensorRadius()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getSensorRadius())&&StringUtils.isNotEmpty(gundamBean.getFixedArmed())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getFixedArmed())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"fixedArmed\"").append(":").append("\"*").append(gundamBean.getFixedArmed()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getFixedArmed())&&StringUtils.isNotEmpty(gundamBean.getDubut())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getDubut())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"dubut\"").append(":").append("\"*").append(gundamBean.getDubut()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getDubut())&&StringUtils.isNotEmpty(gundamBean.getCockpit())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getCockpit())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"cockpit\"").append(":").append("\"*").append(gundamBean.getCockpit()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getCockpit())&&StringUtils.isNotEmpty(gundamBean.getPilot())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getPilot())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"pilot\"").append(":").append("\"*").append(gundamBean.getPilot()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getPilot())&&StringUtils.isNotEmpty(gundamBean.getChooseWeapons())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getChooseWeapons())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"chooseWeapons\"").append(":").append("\"*").append(gundamBean.getChooseWeapons()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getChooseWeapons())&&StringUtils.isNotEmpty(gundamBean.getDegreeTime())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getDegreeTime())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"degreeTime\"").append(":").append("\"*").append(gundamBean.getDegreeTime()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getDegreeTime())&&StringUtils.isNotEmpty(gundamBean.getGroundSpeed())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getGroundSpeed())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"groundSpeed\"").append(":").append("\"*").append(gundamBean.getGroundSpeed()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getGroundSpeed())&&StringUtils.isNotEmpty(gundamBean.getWaterSpeed())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getWaterSpeed())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"waterSpeed\"").append(":").append("\"*").append(gundamBean.getWaterSpeed()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            if(StringUtils.isNotEmpty(gundamBean.getWaterSpeed())&&StringUtils.isNotEmpty(gundamBean.getIntroduction())){ sb.append(","); }
            if(StringUtils.isNotEmpty(gundamBean.getIntroduction())){
                sb.append("{").append("\"wildcard\"").append(":").append("{").append("\"introduction\"").append(":").append("\"*").append(gundamBean.getIntroduction()).append("*\"").append("}").append("}");
                shouldCount++;
            }
            sb.append("]").append(",");
            /*if(shouldCount == 0){
                sb.append("\"match_all\"").append(":").append("{}").append(",");
            }*/
            //mininum_should_match默认就是1,设置成0没有意义
            sb.append("\"minimum_should_match\"").append(":").append(shouldCount==0?0:1).append(",");
            sb.append("\"boost\"").append(":").append(1.0);
            sb.append("}");
//            sb.append(",");
            sb.append("}").append(",");
            sb.append("\"from\"").append(":").append(pagination.getPageSize()*(pagination.getCurPage()-1)).append(",");
            sb.append("\"size\"").append(":").append(pagination.getPageSize());
            sb.append("}");

            String result = HttpUtils.doJsonPost(url, sb.toString(), "UTF-8");
            System.out.println(result);
            JSONObject jsonResult = JSON.parseObject(result);
            ESResult esResult=ESResult.originalParse(jsonResult);
            List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
            resultMapList = esResult.getHits();
            page.setDataList(resultMapList);
            pagination.setTotalSize(Integer.parseInt(esResult.getTotal()));
            page.setPagination(pagination);
        }
        return page;
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
            if(StringUtils.isNotEmpty(gundamBean.getOrganismNumber())){
                sb.append("{").append("\"term\"").append(":").append("{");
                sb.append("\"organismNumber\"").append(":").append("\"").append(gundamBean.getOrganismNumber()).append("\"");
                sb.append("}").append("}");
            }
            //之后又需要再写
            sb.append("]").append(",");
            sb.append("\"boost\"").append(":").append(1.0);
            sb.append("}").append("}").append("}");
            String result = HttpUtils.doJsonPost(url, sb.toString(), "UTF-8");
            System.out.println(result);
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
//        return null;
        return "true";
    }
}
