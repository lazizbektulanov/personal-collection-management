package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.Collection;

@Getter
@Setter
@ToString
//@AllArgsConstructor
@NoArgsConstructor
@Indexed
@Entity(name = "users")
public class User extends AbsEntity implements UserDetails {

    @Field(store = Store.YES)
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(columnDefinition = "text")
    private String bio;

    private String profileImgUrl;

    @Column(nullable = false)
    private Timestamp lastLoginTime = Timestamp.from(Instant.now());

    @ManyToOne
    private Role role;

    public User(String fullName, String email,
                String password, boolean isActive,
                Role role, String profileImgUrl) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.role = role;
        this.profileImgUrl = profileImgUrl;
    }

    public User(String fullName, String email,
                String password, boolean isActive,
                String profileImgUrl, Timestamp lastLoginTime,
                Role role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.profileImgUrl = profileImgUrl;
        this.lastLoginTime = lastLoginTime;
        this.role = role;
    }

    public User(String fullName, String email, String password, boolean isActive, String bio, String profileImgUrl, Timestamp lastLoginTime, Role role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.bio = bio;
        this.profileImgUrl = profileImgUrl;
        this.lastLoginTime = lastLoginTime;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }


}
