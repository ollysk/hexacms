package net.ollysk.pr.service;

import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.RequestLog;
import net.ollysk.pr.port.in.RequestLogService;
import net.ollysk.pr.port.out.RequestLogRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestLogUseCase implements RequestLogService {

  private final RequestLogRepo requestLogRepo;

  @Override public RequestLog save(RequestLog requestLog) {
    if (requestLog.getRequest().contains(".")) {
      return requestLog; // <-- this is a business logic, not validation, should be kept in use case
    }
    return requestLogRepo.save(requestLog);
  }
}
