package bitcoin.infrastructure.repository.impl;

import bitcoin.infrastructure.mapper.KlineMapper;
import bitcoin.infrastructure.model.Kline;
import bitcoin.infrastructure.repository.KlineRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@MapperScan("bitcoin.infrastructure.mapper")
public class KlineRepositoryImpl implements KlineRepository{

    @Autowired
    public KlineMapper klineMapper;

    @Override
    public boolean insertKline(Kline kline) {
        int insertNum = klineMapper.insert(kline);
        if(insertNum > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<Kline> queryKlineByBeginTimeAndSource(String dateTime, String source) {

        return null;
    }
}
