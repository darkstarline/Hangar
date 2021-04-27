package gundam.dao;

import gundam.pojo.FileInvokeBean;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FileInvokeDaoImpl implements IFileInvokeDao {
    private SqlSessionFactory factory;
    public FileInvokeDaoImpl(SqlSessionFactory factory){
        this.factory=factory;
    }

    @Override
    public void saveFileInvoke(FileInvokeBean fileInvokeBean) throws Exception {
        SqlSession session = factory.openSession();
        session.insert("fileInvoke.save",fileInvokeBean);
        session.close();
    }
}
