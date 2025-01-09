package test.quartz.quartz.model;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import test.quartz.quartz.service.QuartzEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "quartz_entity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(QuartzEntityListener.class)
@Log4j2
@ToString
public class QuartzEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false, unique = true)
    private Long id;
    @Column(name = "url", nullable = false)
    private String url;
    @Column(name = "method", nullable = false)
    private String method;
    @Column(name = "body")
    private String body;
    @Column(name = "headers")
    private String headers;
    @Column(name = "cron",nullable = false)
    private String cron;
    @Column(name = "isActive",columnDefinition = "boolean DEFAULT true")
    private Boolean isActive;
}
