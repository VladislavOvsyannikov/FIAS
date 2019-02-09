package system.mapper;

import java.util.Collection;
import java.util.List;

public interface AbstractDtoMapper<Domain, Dto> {

    Dto toDto(Domain domainModel);

    List<Dto> toDto(Collection<Domain> domainModelList);
}