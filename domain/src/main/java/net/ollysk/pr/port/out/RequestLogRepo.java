package net.ollysk.pr.port.out;

import java.util.Optional;
import net.ollysk.pr.model.RequestLog;

public interface RequestLogRepo {

  long findTop10ByIdLessThanEqual(long id);

  Optional<Long> findUserAliasId(long trackingId);

  RequestLog save(RequestLog requestLog);
}
