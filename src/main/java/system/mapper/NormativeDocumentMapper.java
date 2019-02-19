package system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import system.domain.NormativeDocument;
import system.dto.NormativeDocumentDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface NormativeDocumentMapper extends AbstractDtoMapper<NormativeDocument, NormativeDocumentDto> {
}
