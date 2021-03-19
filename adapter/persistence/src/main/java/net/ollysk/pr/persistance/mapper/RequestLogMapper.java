package net.ollysk.pr.persistance.mapper;

import net.ollysk.pr.model.RequestLog;
import net.ollysk.pr.persistance.model.RequestLogJpa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestLogMapper {

  RequestLog toRequestLog(RequestLogJpa requestLogJpa);

  RequestLogJpa toRequestLogJpa(RequestLog requestLog);
}
