package bitcoin.infrastructure.repository;

import bitcoin.infrastructure.model.Kline;
import java.util.List;

public interface KlineRepository {

    public boolean insertKline(Kline kline);

    public List<Kline> queryKlineByBeginTimeAndSource(String dateTime, String source);
}
