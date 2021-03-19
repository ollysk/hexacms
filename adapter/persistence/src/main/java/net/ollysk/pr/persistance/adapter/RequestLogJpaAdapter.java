package net.ollysk.pr.persistance.adapter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.RequestLog;
import net.ollysk.pr.persistance.dao.RequestLogJpaRepo;
import net.ollysk.pr.persistance.mapper.RequestLogMapper;
import net.ollysk.pr.persistance.model.RequestLogJpa;
import net.ollysk.pr.port.out.RequestLogRepo;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RequestLogJpaAdapter implements RequestLogRepo {

  private final RequestLogJpaRepo requestLogJpaRepo;
  private final RequestLogMapper requestLogMapper;

  @Override public long findTop10ByIdLessThanEqual(long id) {
    return requestLogJpaRepo.findTop10ByIdLessThanEqualOrderByIdDesc(id);
  }

  @Override public Optional<Long> findUserAliasId(long trackingId) {
    return requestLogJpaRepo.findUserAliasId(trackingId);
  }

  @Override public RequestLog save(RequestLog requestLog) {
    RequestLogJpa requestLogJpa = requestLogJpaRepo.save(requestLogMapper.toRequestLogJpa(
        requestLog));
    return requestLogMapper.toRequestLog(requestLogJpa);
  }
}
