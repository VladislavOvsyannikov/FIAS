package fias.mapper;

import fias.domain.Stead;
import fias.dto.SteadDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SteadMapper extends AbstractDtoMapper<Stead, SteadDto> {
}
