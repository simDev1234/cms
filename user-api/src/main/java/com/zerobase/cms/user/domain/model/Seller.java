package com.zerobase.cms.user.domain.model;

import com.zerobase.cms.user.domain.SignUpForm;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Seller extends BaseEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private String phone;
    private LocalDate birth;

    private LocalDateTime verifyExpiredAt;
    private String verificationCode;
    private boolean verify;

    public static Seller from(SignUpForm form){
        return Seller.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .name(form.getName())
                .password(form.getPassword())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .verify(false)
                .build();
    }

}
