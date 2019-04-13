package fias.mapper;

import fias.domain.NormativeDocument;
import fias.dto.NormativeDocumentDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface NormativeDocumentMapper extends AbstractDtoMapper<NormativeDocument, NormativeDocumentDto> {
}
