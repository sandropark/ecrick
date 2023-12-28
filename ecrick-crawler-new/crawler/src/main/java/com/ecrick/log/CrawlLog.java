package com.ecrick.log;

import com.ecrick.entity.AuditField;
import com.ecrick.entity.Library;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_crawl_log_transaction_id_success", columnList = "transaction_id, success"),
})
@Entity
public class CrawlLog extends AuditField {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BIGINT comment '사용자 ID'")
    private Long id;
    @Column(columnDefinition = "VARCHAR(255) not null comment 'URL'")
    private String url;
    @Column(name = "transaction_id", columnDefinition = "BINARY(16) not null comment 'Transaction id'")
    private UUID transactionId;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "BIT(2) not null comment '로그 타입'")
    private LogType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id", foreignKey = @ForeignKey(name = "fk_crawl_log_library_id"))
    private Library library;
    @Column(columnDefinition = "LONGTEXT not null comment '바디'")
    private String body;
    @Column(columnDefinition = "VARCHAR(255) not null comment '에러 메시지'")
    private String error;
    @Column(columnDefinition = "BIT(1) not null comment '성공 여부'")
    private boolean success;
}
