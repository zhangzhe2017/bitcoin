package bitcoin.infrastructure.mapper;

import bitcoin.infrastructure.model.Kline;
import bitcoin.infrastructure.model.KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KlineMapper {
    int countByExample(KlineExample example);

    int deleteByExample(KlineExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Kline record);

    int insertSelective(Kline record);

    List<Kline> selectByExample(KlineExample example);

    Kline selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Kline record, @Param("example") KlineExample example);

    int updateByExample(@Param("record") Kline record, @Param("example") KlineExample example);

    int updateByPrimaryKeySelective(Kline record);

    int updateByPrimaryKey(Kline record);
}