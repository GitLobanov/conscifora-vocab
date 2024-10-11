package com.conscifora.vocab.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vocab")
public class Vocab {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vocab_seq")
    @SequenceGenerator(name = "vocab_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 10)
    private String languageCode;

    private String word;
    private String definition;

    @ManyToMany(mappedBy = "vocabs")
    private Set<VocabExamples> vocabExamples = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "sourceVocab")
    private Set<VocabTranslation> vocabTranslationsSource = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "sourceVocab")
    private Set<VocabTranslation> vocabTranslationsTarget = new LinkedHashSet<>();


    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Vocab vocab = (Vocab) object;
        return getId() != null && Objects.equals(getId(), vocab.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}