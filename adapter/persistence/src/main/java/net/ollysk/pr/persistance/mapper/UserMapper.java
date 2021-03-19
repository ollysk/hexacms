package net.ollysk.pr.persistance.mapper;

import net.ollysk.pr.model.User;
import net.ollysk.pr.persistance.model.UserJpa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  User toUser(UserJpa userJpa);

  UserJpa toUserJpa(User user);
}
