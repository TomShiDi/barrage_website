package barrage.demo.repository;

import barrage.demo.entity.QuotesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author TomShiDi
 * @Since 2020/7/29
 * @Version 1.0
 */
public interface QuotesInfoRepository extends JpaRepository<QuotesInfo, Integer> {
    public QuotesInfo findByQuotesId(Integer quotesId);

    @Query(value = "select * from quotes_info order by quotes_id asc limit ?1,1",nativeQuery = true)
    public QuotesInfo getOne(Integer index);

    public QuotesInfo deleteByQuotesId(Integer quotesId);
}
