package system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import system.domain.House;
import system.dto.HouseDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HouseMapper extends AbstractDtoMapper<House, HouseDto> {
}
