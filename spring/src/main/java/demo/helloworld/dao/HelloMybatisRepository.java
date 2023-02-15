package demo.helloworld.dao;

import demo.helloworld.model.HelloMybatisEntity;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Mapper
@Resource(name="h2Factory")
public interface HelloMybatisRepository {
    List<HelloMybatisEntity> list();
}
