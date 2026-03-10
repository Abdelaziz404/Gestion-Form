package Security;

import Entity.Person;
import Repository.PersonRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomPersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public CustomPersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<Person> personOpt = personRepository.findByEmail(email);

        return personOpt
                .map(PersonDetails::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + email));
    }
}
