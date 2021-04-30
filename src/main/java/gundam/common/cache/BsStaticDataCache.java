package gundam.common.cache;

import gundam.constans.ResourceConstans;
import gundam.dao.IBsStaticData;
import gundam.pojo.BsStaticData;
import gundam.services.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BsStaticDataCache implements ApplicationListener<ContextRefreshedEvent> {
    private  final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    IBsStaticData bsStaticData;
    @Autowired
    IRedisService redisService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {//保证只执行一次
            if(logger.isErrorEnabled()){
                logger.error("开始加载bs_static_data缓存");
            }
            try {
                Map<String,Object> bsStaticDataMap = this.getBsStaticDataMap();
                redisService.hmset(ResourceConstans.CACHE.HANGERBASE,bsStaticDataMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(logger.isErrorEnabled()){
                logger.error("bs_static_data缓存加载完毕");
            }
        }
    }

    private Map<String, Object> getBsStaticDataMap() throws Exception{
        Map<String,Object> map = new HashMap<>();
        List<BsStaticData> data = bsStaticData.getAllBsStaticData();
        for(BsStaticData bsd : data){
            map.put(bsd.getCodeName(),bsd.getCodeValue());
        }
        return map;
    }
}
