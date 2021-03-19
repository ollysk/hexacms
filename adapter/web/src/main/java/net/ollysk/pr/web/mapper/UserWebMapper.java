package net.ollysk.pr.web.mapper;

import net.ollysk.pr.model.User;
import net.ollysk.pr.web.model.UserWeb;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserWebMapper {

  User toUser(UserWeb userWeb);
}
