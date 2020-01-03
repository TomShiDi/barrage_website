package barrage.demo.serviceImpl;

import barrage.demo.entity.BarrageInfo;
import barrage.demo.enums.BarrageExceptionEnum;
import barrage.demo.exception.BarrageException;
import barrage.demo.repository.BarrageInfoRepository;
import barrage.demo.service.BarrageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
//@MethodLog
public class BarrageInfoServiceImpl implements BarrageInfoService {

    private BarrageInfoRepository repository;

    public BarrageInfoServiceImpl() {
    }

    @Autowired
    public BarrageInfoServiceImpl(BarrageInfoRepository repository) {
        this.repository = repository;
    }


    @Override
    public BarrageInfo findByBarrageId(Integer id) {
        BarrageInfo barrageInfo = repository.findByBarrageId(id);
        if (barrageInfo == null) {
            throw new BarrageException(BarrageExceptionEnum.BARRAGE_INFO_QUERY_ERROR);
        }

        return barrageInfo;
    }

    @Override
    public List<BarrageInfo> findBySenderId(Integer senderId) {

        return repository.findByBarrageSenderId(senderId);
    }


    @Override
    public Page<BarrageInfo> getBarragePageByIndex(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public BarrageInfo saveBarrageInfo(BarrageInfo barrageInfo) {
        BarrageInfo result = repository.save(barrageInfo);

        if (result == null) {
            throw new BarrageException(BarrageExceptionEnum.BARRAGE_INFO_SAVE_ERROR);
        }

        return barrageInfo;
    }

    @Override
    public void deleteBarrageInfo(Integer barrageId) {
        repository.deleteByBarrageId(barrageId);

    }
}
