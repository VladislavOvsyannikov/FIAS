package system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import system.domain.Stead;
import system.dto.SteadDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SteadMapper extends AbstractDtoMapper<Stead, SteadDto> {
}
