package com.conscifora.vocab.dto;

import java.util.Set;

/**
 * DTO for {@link com.conscifora.vocab.domain.VocabExamples}
 */
public record VocabExamplesDto(
        String text,
        Set<VocabDTO> vocabs) {
}