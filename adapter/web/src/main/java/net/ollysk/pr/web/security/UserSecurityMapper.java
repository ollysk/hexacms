package net.ollysk.pr.web.security;

import net.ollysk.pr.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSecurityMapper {

  User toUser(UserSecurity userSecurity);

  UserSecurity toUserSecurity(User user);
}
