package com.conscifora.vocab.dto;

import lombok.Value;

/**
 * DTO for {@link com.conscifora.vocab.domain.VocabDefinitions}
 */
public record VocabDefinitionsDto(
        String definition
) {
}