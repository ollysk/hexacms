package net.ollysk.pr.service;

import lombok.RequiredArgsConstructor;
import net.ollysk.pr.port.in.UserAliasService;
import net.ollysk.pr.port.out.RequestLogRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserAlias implements UserAliasService {

  private final RequestLogRepo requestLogRepo;

  @Override public long findUserAliasId(long trackingId) {
    return requestLogRepo.findUserAliasId(trackingId).orElse(0L);
  }
}
