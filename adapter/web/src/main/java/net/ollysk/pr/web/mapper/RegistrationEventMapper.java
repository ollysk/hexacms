package net.ollysk.pr.web.mapper;

import net.ollysk.pr.model.MailData;
import net.ollysk.pr.web.model.RegistrationEvent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistrationEventMapper {

  MailData toMailData(RegistrationEvent registrationEvent);
}
