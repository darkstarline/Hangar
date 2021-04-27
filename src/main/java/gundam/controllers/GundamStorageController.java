package gundam.controllers;


import com.alibaba.fastjson.JSON;
import gundam.common.DfsFactory;
import gundam.common.JsonResult;
import gundam.common.OperatorInfo;
import gundam.common.UserSession;
import gundam.constans.AppConstans;
import gundam.constans.ResourceConstans;
import gundam.pojo.FileInfoBean;
import gundam.pojo.GundamBean;
import gundam.services.IElasticsearchService;
import gundam.services.IFileInfoService;
import gundam.services.IGundamService;
import gundam.utils.UploadUtils;
import gundam.vo.GundamVo;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

public class GundamStorageController {
    @Autowired
    IGundamService gundamService;
    @Autowired
    IFileInfoService fileInfoService;
    @Autowired
    IElasticsearchService elasticsearchService;
    @Post("save")
    public JSON saveInfo(Invocation inv, GundamVo gundamVo, MultipartFile file) throws Exception{
        JsonResult json=new JsonResult();


        //TODO 参数校验


        UserSession userSession = (UserSession)inv.getRequest().getSession().getAttribute(AppConstans.USER_SESSION);
        OperatorInfo userInfo=userSession.getOperatorInfo();
        if(userInfo.getCode()!=null&&userInfo.getCode()!=""){
            gundamVo.setOpCode(userInfo.getCode());
        }else{
            json.setSuccess(false);
            json.setMessage("您没登陆是怎么进来的！");
            return json;
        }

        GundamBean gundamBean = new GundamBean();
        this.convertGundamVo2Entity(gundamVo,gundamBean);
        this.gundamService.aircraftStorage(gundamBean);

        //TODO 一次上传多个图片先留着
        //TODO 上传的文件需要建立像CMS一样的文件表，不过是查询的时候需要去根据

        //TODO 参数校验
//        attrInfo--->对应gundambean
        FileInfoBean attrInfo = UploadUtils.makeFileInfo(userInfo);
        this.fileInfoService.saveFileInfo(attrInfo);
        //从数据库中查询
        try {
            //TODO 先保存fileInfo，然后上传文件，上传成功后，回写gundam表，file表，rel表，最后上传完记录日志表

            attrInfo = this.fileInfoService.getFileInfo(attrInfo);

            FileInfoBean fileInfo = DfsFactory.getInstance().doUpload(file, attrInfo);

            this.fileInfoService.completeUpload(fileInfo);

            this.elasticsearchService.syncToElasticsearch(fileInfo);

            //json中添加返回信息
            json.setSuccess(true);

        } catch (Exception e) {
            /*log.error("file upload error.", e);
            json.setSuccess(false);
            json.setMessage("保存文件到服务器失败，请联系管理员.");
            log.error("fileId=" + (fileId != null ? fileId : "") + " token=" + (tokenText != null ? tokenText : "") + "保存文件到服务器失败，请联系管理员.");*/
        } finally {
            /*if (attrInfo != null && StringUtils.isNotEmpty(attrInfo.toString()) && StringUtils.isNotEmpty(attrInfo.getFileId())) {
                ICmsFileInvokeValue fileInvoke = new CmsFileInvokeBean();
                fileInvoke.setFileId(attrInfo.getFileId());
                fileInvoke.setOpType(OperateType.UPLOAD.getValue());
//            没有通过file_path访问文件的,file_path不记录
                fileInvoke.setSystemId(attrInfo.getSystemId());
                fileInvoke.setResultCode(String.valueOf(json.isSuccess()));
                fileInvoke.setResultMsg(json.getMessage());
                Timestamp now = new Timestamp(System.currentTimeMillis());
                fileInvoke.setCreateDate(now);
                fileInvoke.setDoneDate(now);
                ThreadUtils.newThread(fileInvoke, new Function<ICmsFileInvokeValue, Long>() {
                    @Override
                    public Long apply(ICmsFileInvokeValue arg0) {
                        try {
                            fileInvokeSV.saveFileInvoke(arg0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
            }*/
        }
        // callback
            /*String callback = inv.getRequest().getParameter("callback");
            if (StringUtils.isNotEmpty(callback)) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(callback).append("(").append(JSON.toJSONString(json)).append(");");
                return buffer.toString();
            }*/


        json.setSuccess(true);
        return json;
    }
    @Post("cover")
    public JSON saveCover(Invocation inv){

        return null;
    }
    public void convertGundamVo2Entity(GundamVo gundamVo,GundamBean gundamBean){
        if(gundamVo.getOrganismId()!=null){
            gundamBean.setCreateDate(new Timestamp(System.currentTimeMillis()));
        }
        //TODO ID的校验确认，REPLACE into是否替换
        gundamBean.setOrgamismId(gundamVo.getOrganismId());
        gundamBean.setOrganismCodeName(gundamVo.getOrganismCodeName());
        gundamBean.setOrganismNumber(gundamVo.getOrganismNumber());
        gundamBean.setJpnName(gundamVo.getJpnName());
        gundamBean.setEnName(gundamVo.getEnName());
        gundamBean.setCnName(gundamVo.getCnName());
        gundamBean.setAnimation(gundamVo.getAnimation());
        gundamBean.setOrganismType(gundamVo.getOrganismType());
        gundamBean.setBelong(gundamVo.getBelong());
        gundamBean.setManufacturer(gundamVo.getManufacturer());
        gundamBean.setOrganismSieze(gundamVo.getOrganismSize());
        gundamBean.setNetWeight(gundamVo.getNetWeight());
        gundamBean.setFullWeight(gundamVo.getFullWeight());
        gundamBean.setArmoredStructure(gundamVo.getArmoredStructure());
        gundamBean.setOutput(gundamVo.getOutput());
        gundamBean.setPropulsion(gundamVo.getPropulsion());
        gundamBean.setAcceleration(gundamVo.getAcceleration());
        gundamBean.setSensorRadius(gundamVo.getSensorRadius());
        gundamBean.setFixedArmed(gundamVo.getFixedArmed());
        gundamBean.setDubut(gundamVo.getDebut());
        gundamBean.setCockpit(gundamVo.getCockpit());
        gundamBean.setPilot(gundamVo.getPilot());
        gundamBean.setChooseWeapons(gundamVo.getChooseWeapons());
        gundamBean.setDegreeTime(gundamVo.getDegreeTime());
        gundamBean.setGroundSpeed(gundamVo.getGroundSpeed());
        gundamBean.setWaterSpeed(gundamVo.getWaterSpeed());
        gundamBean.setIntroduction(gundamVo.getWaterSpeed());
        //TODO 提前获取塞到gundamVo里
        gundamBean.setOpCode(gundamVo.getOpCode());

        gundamBean.setState(ResourceConstans.STATE.USED);

    }
}
