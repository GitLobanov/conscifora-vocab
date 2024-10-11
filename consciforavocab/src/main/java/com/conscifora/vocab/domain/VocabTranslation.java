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

    @ManyToMany
    @JoinTable(name = "vocab_translation_sourceVocab",
            joinColumns = @JoinColumn(name = "vocabTranslation_id"),
            inverseJoinColumns = @JoinColumn(name = "sourceVocab_id"))
    private Set<Vocab> sourceVocab = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "vocab_translation_vocabTarget",
            joinColumns = @JoinColumn(name = "vocabTranslation_id"),
            inverseJoinColumns = @JoinColumn(name = "vocabTarget_id"))
    private Set<Vocab> vocabTarget = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    private TranslationType translationType;

}