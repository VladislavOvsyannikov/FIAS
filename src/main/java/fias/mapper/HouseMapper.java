package fias.mapper;

import fias.domain.House;
import fias.dto.HouseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HouseMapper extends AbstractDtoMapper<House, HouseDto> {
}
