package system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import system.domain.AddrObject;
import system.dto.AddrObjectDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AddrObjectMapper extends AbstractDtoMapper<AddrObject, AddrObjectDto> {
}
