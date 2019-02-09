package system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import system.domain.Room;
import system.dto.RoomDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RoomMapper extends AbstractDtoMapper<Room, RoomDto> {
}
