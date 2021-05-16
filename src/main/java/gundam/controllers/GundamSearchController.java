package gundam.controllers;

import com.alibaba.fastjson.JSON;
import gundam.common.JsonResult;
import gundam.common.Page;
import gundam.common.Pagenation;
import gundam.constans.ResourceConstans;
import gundam.pojo.GundamBean;
import gundam.services.IElasticsearchService;
import gundam.vo.SearchVo;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Post;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class GundamSearchController {
    //TODO 数据处理
    @Autowired
    IElasticsearchService elasticsearchService;
    @Post("search")
    public JSON searchInfo(Invocation inv, SearchVo searchVo, Pagenation pagenation) throws Exception{
        JsonResult json=new JsonResult();
        GundamBean gundamBean = new GundamBean();
        this.convertSearchVo2Entity(searchVo,gundamBean);
        if(StringUtils.isNotEmpty(searchVo.getKeyWord())){
            this.convertKeyWord2Entity(searchVo.getKeyWord(),gundamBean);
        }
        Page<Map<String, Object>> page = this.elasticsearchService.searchFormElasticsearch(gundamBean,pagenation);
        json.setSuccess(true);
        json.put("dataList",page.getDataList());
        return json;
    }

    @Post("euro")
    public JSON searchInfo(Invocation inv,SearchVo searchVo) throws Exception{
        JsonResult json=new JsonResult();
        GundamBean gundamBean = new GundamBean();
        this.convertSearchVo2Entity(searchVo,gundamBean);
        Pagenation pagenation = new Pagenation();
        Map<String, Object> data = this.elasticsearchService.searchFormElasticsearch(gundamBean);
        json.setSuccess(true);
        json.put("data",data);
        return json;
    }
    private void convertSearchVo2Entity(SearchVo searchVo, GundamBean gundamBean) {
        gundamBean.setOrganismCodeName(searchVo.getOrganismCodeName());
        gundamBean.setOrganismNumber(searchVo.getOrganismNumber());
        gundamBean.setJpnName(searchVo.getJpnName());
        gundamBean.setEnName(searchVo.getEnName());
        gundamBean.setCnName(searchVo.getCnName());
        gundamBean.setAnimation(searchVo.getAnimation());
        gundamBean.setOrganismType(searchVo.getOrganismType());
        gundamBean.setBelong(searchVo.getBelong());
        gundamBean.setManufacturer(searchVo.getManufacturer());
        gundamBean.setOrganismSieze(searchVo.getOrganismSize());
        gundamBean.setNetWeight(searchVo.getNetWeight());
        gundamBean.setFullWeight(searchVo.getFullWeight());
        gundamBean.setArmoredStructure(searchVo.getArmoredStructure());
        gundamBean.setOutput(searchVo.getOutput());
        gundamBean.setPropulsion(searchVo.getPropulsion());
        gundamBean.setAcceleration(searchVo.getAcceleration());
        gundamBean.setSensorRadius(searchVo.getSensorRadius());
        gundamBean.setFixedArmed(searchVo.getFixedArmed());
        gundamBean.setDubut(searchVo.getDebut());
        gundamBean.setCockpit(searchVo.getCockpit());
        gundamBean.setPilot(searchVo.getPilot());
        gundamBean.setChooseWeapons(searchVo.getChooseWeapons());
        gundamBean.setDegreeTime(searchVo.getDegreeTime());
        gundamBean.setGroundSpeed(searchVo.getGroundSpeed());
        gundamBean.setWaterSpeed(searchVo.getWaterSpeed());
        gundamBean.setIntroduction(searchVo.getWaterSpeed());
        gundamBean.setState(ResourceConstans.STATE.USED);
    }

    private void convertKeyWord2Entity(String keyWord, GundamBean gundamBean) {
        gundamBean.setOrganismCodeName(keyWord);
        gundamBean.setOrganismNumber(keyWord);
        gundamBean.setJpnName(keyWord);
        gundamBean.setEnName(keyWord);
        gundamBean.setCnName(keyWord);
        gundamBean.setAnimation(keyWord);
        gundamBean.setOrganismType(keyWord);
        gundamBean.setBelong(keyWord);
        gundamBean.setManufacturer(keyWord);
        gundamBean.setOrganismSieze(keyWord);
        gundamBean.setNetWeight(keyWord);
        gundamBean.setFullWeight(keyWord);
        gundamBean.setArmoredStructure(keyWord);
        gundamBean.setOutput(keyWord);
        gundamBean.setPropulsion(keyWord);
        gundamBean.setAcceleration(keyWord);
        gundamBean.setSensorRadius(keyWord);
        gundamBean.setFixedArmed(keyWord);
        gundamBean.setDubut(keyWord);
        gundamBean.setCockpit(keyWord);
        gundamBean.setPilot(keyWord);
        gundamBean.setChooseWeapons(keyWord);
        gundamBean.setDegreeTime(keyWord);
        gundamBean.setGroundSpeed(keyWord);
        gundamBean.setWaterSpeed(keyWord);
        gundamBean.setIntroduction(keyWord);
        gundamBean.setState(ResourceConstans.STATE.USED);
    }
}
