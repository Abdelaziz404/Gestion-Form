package Service.Person;

import Dto.Person.PersonRequest;
import Dto.Person.PersonResponse;

import java.util.List;

public interface PersonService {

    // ================= CREATE =================
    PersonResponse createPerson(PersonRequest request);

    // ================= UPDATE =================
    PersonResponse updatePerson(Long id, PersonRequest request);

    PersonResponse findById(Long id);

    // ================= DELETE =================
    void deletePerson(Long id);

    // ================= GET =================
    PersonResponse getById(Long id);

    PersonResponse getByEmail(String email);

    PersonResponse getByPhone(String phone);

    List<PersonResponse> findAllResponse();
}