package com.conscifora.vocab.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vocab_translation")
public class VocabTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vocab_translation_seq")
    @SequenceGenerator(name = "vocab_translation_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TranslationType translationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocab_source_id")
    private Vocab vocabSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocab_target_id")
    private Vocab vocabTarget;

}