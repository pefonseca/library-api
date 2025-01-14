package com.pefonseca.library.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author", schema = "public")
@ToString(exclude = {"books"})
@EntityListeners(AuditingEntityListener.class)
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "date_birth", nullable = false)
    private LocalDate birthDate;

    @Column(name = "nationality", length = 50, nullable = false)
    private String nationality;

    @OneToMany(
            mappedBy = "author", fetch = FetchType.LAZY
//            cascade = CascadeType.ALL
    )
    private List<Book> books;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedAt;

    @Column(name = "user_id")
    private UUID userId;
}
