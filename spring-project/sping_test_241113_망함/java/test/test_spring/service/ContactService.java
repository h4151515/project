package test.test_spring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import test.test_spring.dao.Contact;
import test.test_spring.repository.ContactRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactRepository contactRepository;
    // 문의사항 저장
    public Long createContact(Contact contact) {
        log.info("createContact() process now");
        this.contactRepository.save(contact);
        log.info("createContact() 저장을 위한 Contact id : ", contact.getId());
        return contact.getId();
    }
    // 전체 Contacts list 조회
    public List<Contact> findByAllList() {
        log.info("findByAllList() process now");
        return this.contactRepository.findAll();
    }
    // id로 Contact 가져오기
    public Optional<Contact> findByContactId(Long id) {
        log.info("findByContactId() process now");
        return this.contactRepository.findById(id);
    }
    // id로 Contact 삭제하기
    public void deleteContact(Contact deleteContact) {
        log.info("deleteContact() process now");
        contactRepository.delete(deleteContact);
    }
}
