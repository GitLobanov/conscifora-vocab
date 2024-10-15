package com.conscifora.vocab.dto;

import com.conscifora.vocab.domain.TranslationType;
import lombok.Value;

import java.util.Set;

/**
 * DTO for {@link com.conscifora.vocab.domain.VocabTranslation}
 */
public record VocabTranslationDto(
        Set<VocabDTO> sourceVocab,
        Set<VocabDTO> vocabTarget,
        TranslationType translationType) {
}