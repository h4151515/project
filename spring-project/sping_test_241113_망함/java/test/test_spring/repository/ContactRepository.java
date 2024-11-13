package test.test_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.test_spring.dao.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
