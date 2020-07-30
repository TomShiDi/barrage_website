package barrage.demo.serviceImpl;

import barrage.demo.entity.QuotesInfo;
import barrage.demo.enums.BarrageExceptionEnum;
import barrage.demo.exception.BarrageException;
import barrage.demo.repository.QuotesInfoRepository;
import barrage.demo.service.QuotesInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author TomShiDi
 * @Since 2020/7/29
 * @Version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QuotesInfoServiceImpl implements QuotesInfoService {

    @Resource
    private QuotesInfoRepository repository;

    @Override
    public QuotesInfo findByQuotesId(Integer quotesId) {
        QuotesInfo quotesInfo = repository.findByQuotesId(quotesId);
        if (quotesInfo == null) {
            throw new BarrageException(BarrageExceptionEnum.QUOTES_NOT_FOUND);
        }
        return quotesInfo;
    }

    @Override
    public QuotesInfo getOne(Integer index) {
        QuotesInfo quotesInfo = repository.getOne(index);
        if (quotesInfo == null) {
            throw new BarrageException(BarrageExceptionEnum.INDEX_OUT_OF_BOUND);
        }
        return quotesInfo;
    }

    @Override
    public QuotesInfo save(QuotesInfo quotesInfo) {
        return repository.save(quotesInfo);
    }

    @Override
    public void deleteByQuotesId(Integer quotesId) {
        repository.deleteByQuotesId(quotesId);
    }

}
