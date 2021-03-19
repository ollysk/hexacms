package net.ollysk.pr.persistance.mapper;

import net.ollysk.pr.model.VerificationToken;
import net.ollysk.pr.persistance.model.VerificationTokenJpa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VerificationTokenMapper {

  VerificationToken toVerificationToken(VerificationTokenJpa tokenJpa);

  VerificationTokenJpa toVerificationTokenJpa(VerificationToken token);
}
