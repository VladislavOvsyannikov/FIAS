package system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import system.domain.User;
import system.dto.UserDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper extends AbstractDtoMapper<User, UserDto> {
}
