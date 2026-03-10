package Security;

import Entity.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonDetails implements UserDetails {

    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return authorities based on the person's role
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + person.getRole().name()));

        if (person instanceof Entity.Admin) {
            authorities.add(new SimpleGrantedAuthority("PERMISSION_" + ((Entity.Admin) person).getPermissions()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return person.getMotDePasse();
    }

    @Override
    public String getUsername() {
        return person.getEmail();
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
        return true;
    }

    public Long getId() {
        return person.getId();
    }

    public String getFirstName() {
        return person.getPrenom();
    }

    public String getLastName() {
        return person.getNom();
    }

    public String getRole() {
        return person.getRole().name();
    }
}