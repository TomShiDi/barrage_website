package barrage.demo.service;

import barrage.demo.entity.QuotesInfo;

/**
 * @Author TomShiDi
 * @Since 2020/7/29
 * @Version 1.0
 */
public interface QuotesInfoService {
    QuotesInfo findByQuotesId(Integer quotesId);

    QuotesInfo getOne(Integer index);

    QuotesInfo save(QuotesInfo quotesInfo);

    QuotesInfo deleteByQuotesId(Integer quotesId);
}
