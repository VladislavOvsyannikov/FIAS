package fias.mapper;

import fias.domain.AddrObject;
import fias.dto.AddrObjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AddrObjectMapper extends AbstractDtoMapper<AddrObject, AddrObjectDto> {
}
