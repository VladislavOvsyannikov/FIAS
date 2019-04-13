package fias.mapper;

import fias.domain.Room;
import fias.dto.RoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RoomMapper extends AbstractDtoMapper<Room, RoomDto> {
}
