package gundam.dao;

import gundam.pojo.FileInfoBean;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileInfoDaoImpl implements IFileInfoDao{
    private SqlSessionFactory factory;
    public FileInfoDaoImpl(SqlSessionFactory factory){
        this.factory=factory;
    }

    @Override
    public void saveFileInfo(FileInfoBean fileInfoBean) throws Exception{
        SqlSession session = factory.openSession();
        session.insert("fileInfo.save",fileInfoBean);
        session.close();
    }

    @Override
    public FileInfoBean getFileInfo(FileInfoBean fileInfoBean) throws Exception {
        SqlSession session = factory.openSession();
        List<FileInfoBean> fileInfo=session.selectList("fileInfo.getBean",fileInfoBean);
        session.close();
        return fileInfo.get(0);
    }
}
