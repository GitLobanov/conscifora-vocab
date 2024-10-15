package com.conscifora.vocab.mapper;

import com.conscifora.vocab.dto.VocabDTO;
import com.conscifora.vocab.domain.Vocab;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VocabMapper {
    Vocab toEntity(VocabDTO vocabDTO);

    VocabDTO toDto(Vocab vocab);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Vocab partialUpdate(VocabDTO vocabDTO, @MappingTarget Vocab vocab);
}