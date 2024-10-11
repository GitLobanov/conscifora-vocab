package com.conscifora.vocab.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vocab_examples")
public class VocabExamples {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vocab_examples_seq")
    @SequenceGenerator(name = "vocab_examples_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 500)
    private String text;

    @ManyToMany
    @JoinTable(name = "vocab_examples_vocabs",
            joinColumns = @JoinColumn(name = "vocabExamples_id"),
            inverseJoinColumns = @JoinColumn(name = "vocabs_id"))
    private Set<Vocab> vocabs = new LinkedHashSet<>();

}