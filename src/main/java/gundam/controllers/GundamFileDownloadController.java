package gundam.controllers;

import com.alibaba.fastjson.JSON;
import gundam.common.DfsFactory;
import gundam.pojo.GundamFileRelBean;
import gundam.services.IFileInvokeService;
import gundam.services.IGundamFileRelService;
import gundam.vo.DownloadVo;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class GundamFileDownloadController {
    @Autowired
    IGundamFileRelService gundamFileRelService;
    @Autowired
    IFileInvokeService fileInvokeService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Post("download")
    @Get("download")
    public JSON downloadFile(Invocation inv, DownloadVo downloadVo) throws Exception{
//        JsonResult json=new JsonResult();
        String organismNumber = downloadVo.getMS();
        String fileType = downloadVo.getTP();

        GundamFileRelBean gundamFileRel = new GundamFileRelBean();
        gundamFileRel.setOrganismNumber(organismNumber);
        gundamFileRel.setFileType(fileType);
        gundamFileRel = gundamFileRelService.getBean(gundamFileRel);

        if(StringUtils.isNotEmpty(gundamFileRel.getFileId())&&StringUtils.isNotEmpty(gundamFileRel.getDfsId())){
//            byte[] data = DfsFactory.getInstance().doDownload(gundamFileRel.getDfsId(),strDefaultKey);
            byte[] data = DfsFactory.getInstance().doDownload(gundamFileRel.getDfsId());
            HttpServletResponse response = inv.getResponse();
            OutputStream outputStream = response.getOutputStream();
            try{
                outputStream.write(data);
                outputStream.flush();
            }catch (Exception e){
                log.error("吊毛，下载文件失败了！",e);
            }finally {
                outputStream.close();
            }
        }

//        json.setSuccess(true);
        return null;
    }
}
