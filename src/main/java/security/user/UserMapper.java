package security.user;

import fias.mapper.AbstractDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper extends AbstractDtoMapper<User, UserDto> {
}
