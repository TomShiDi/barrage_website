package barrage.demo.repository;

import barrage.demo.entity.BarrageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarrageInfoRepository extends JpaRepository<BarrageInfo, Integer> {

    BarrageInfo findByBarrageId(Integer barrageId);

    List<BarrageInfo> findByBarrageSenderId(Integer senderId);

    void deleteByBarrageId(Integer barrageId);
}
