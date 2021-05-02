package gundam.controllers;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import gundam.common.DfsFactory;
import gundam.common.JsonResult;
import gundam.common.OperatorInfo;
import gundam.common.UserSession;
import gundam.constans.AppConstans;
import gundam.constans.ResourceConstans;
import gundam.pojo.FileInfoBean;
import gundam.pojo.FileInvokeBean;
import gundam.pojo.GundamBean;
import gundam.pojo.GundamFileRelBean;
import gundam.services.*;
import gundam.utils.ThreadUtils;
import gundam.utils.UploadUtils;
import gundam.vo.GundamVo;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Post;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    IGundamFileRelService iGundamFileRelService;
    @Autowired
    IFileInvokeService fileInvokeService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
        try {
            //同步到ES
            this.elasticsearchService.syncToElasticsearch(gundamBean);
        }catch (Exception e){
            log.error("sync to elasticsearch error.", e);
            json.setSuccess(false);
            json.setMessage("同步到ES失败，请联系管理员.");
            //TODO 这种调用函数多的，之后需要优化掉
            log.error("gundamNumber=" +(((gundamBean.getOrganismNumber()!=null&&!"".equals(gundamBean.getOrganismNumber())))?gundamBean.getOrganismNumber():"fileId都么得"));
            return json;
        }

        //TODO 一次上传多个图片先留着
        //TODO 上传的文件需要建立像CMS一样的文件表，不过是查询的时候需要去根据

        //TODO 参数校验
//        attrInfo--->对应gundambean
        FileInfoBean attrInfo = UploadUtils.makeFileInfo(userInfo);
        this.fileInfoService.saveFileInfo(attrInfo);
        //从数据库中查询
        try {
            //TODO 先保存fileInfo，然后上传文件，上传成功后，回写gundam表，file表，rel表，最后上传完记录日志表
            //查询文件信息 之后可以直接用上面的fileInfo
            attrInfo = this.fileInfoService.getFileInfo(attrInfo);

            //上传文件
            FileInfoBean fileInfo = DfsFactory.getInstance().doUpload(file, attrInfo);

            //回写file表
            this.fileInfoService.completeUpload(fileInfo);

            //写rel表
            GundamFileRelBean gundamFileRelBean = this.makeGundamFileRelInfo(gundamBean,fileInfo);
            this.iGundamFileRelService.saveGundamFileRel(gundamFileRelBean);

            //json中添加返回信息
            json.setSuccess(true);

        } catch (Exception e) {
            log.error("file upload error.", e);
            json.setSuccess(false);
            json.setMessage("保存文件到服务器失败/回写信息失败，请联系管理员.");
            log.error("fileId=" +(((attrInfo.getFileId()!=null&&!"".equals(attrInfo.getFileId())))?attrInfo.getFileId():"fileId都么得"));
            return json;
        } finally {
            if (attrInfo != null && StringUtils.isNotEmpty(attrInfo.toString()) && StringUtils.isNotEmpty(attrInfo.getFileId())) {
                final FileInvokeBean fileInvokeBean = new FileInvokeBean();
                fileInvokeBean.setFileId(attrInfo.getFileId());
                fileInvokeBean.setFileName(attrInfo.getFileName());
                fileInvokeBean.setResultCode(String.valueOf(json.isSuccess()));
                fileInvokeBean.setResultMsg(json.getMessage());
                fileInvokeBean.setOpType(ResourceConstans.OPTYPE.UPLOAD);
                Timestamp now = new Timestamp(System.currentTimeMillis());
                fileInvokeBean.setCreateDate(now);
                ThreadUtils.newThread(fileInvokeBean, new Function<FileInvokeBean, Long>() {
                    @Override
                    public Long apply(FileInvokeBean arg0) {
                        try {
//                            fileInvokeSV.saveFileInvoke(arg0);
                            fileInvokeService.saveFileInvoke(fileInvokeBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
            }
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

    private GundamFileRelBean makeGundamFileRelInfo(GundamBean gundamBean, FileInfoBean fileInfo) {
        GundamFileRelBean gundamFileRelBean = new GundamFileRelBean();
        gundamFileRelBean.setFileId(fileInfo.getFileId());
        gundamFileRelBean.setDfsId(fileInfo.getDfsId());
        gundamFileRelBean.setOrganismNumber(gundamBean.getOrganismNumber());
        gundamFileRelBean.setFileType(ResourceConstans.GUMDAMFILEREL.COVER);
        gundamFileRelBean.setState(ResourceConstans.STATE.USED);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        gundamFileRelBean.setCreateDate(now);
        return gundamFileRelBean;
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
