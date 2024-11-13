package test.test_spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import test.test_spring.dao.Contact;
import test.test_spring.dto.ContactForm;
import test.test_spring.service.ContactService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/") // home 화면 호출
    public String getHome(Model model) {
        log.info("getHome() callback, 홈화면 요청");
        model.addAttribute("contactForm", new ContactForm());
        log.info("getHome() process now, contactForm model 객체 담아서 리턴 ");
        return "home";
    }

    @PostMapping("/") // contact 내용 저장 , @Valid BindingResult bindingResult
    public String postCreateContact(@Valid @ModelAttribute("contactForm") ContactForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "home";
        }
        log.info("postCreateContact() 처리 요청, DTO의 데이터 Contact 객체 주입");
        Contact contact = new Contact();
        contact.setName(form.getName());
        contact.setTel(form.getTel());
        contact.setEmail(form.getEmail());
        contact.setInquire(form.getInquire());
        contact.setNewsletter(form.isNewsletter());
        contactService.createContact(contact);
        log.info("postCreateContact() create complete, dataInfo { id : "+ contact.getId() + ",\n  name : " + contact.getName() + "}");
        return "redirect:/";
    }

    @GetMapping("/contactList") // contact 리스트 화면 호출
    public String getContactList(Model model) {
        log.info("getContactList() callback, contact 리스트 화면 요청");
        List<Contact> contacts = contactService.findByAllList();
        model.addAttribute("contactList", contacts);
        log.info("getContactList() object return now, List Total Count : ", contacts.size());
        return "page/contactList";
    }

    @GetMapping("/modify/{id}") // contact 수정 페이지 출력 , @Valid BindingResult bindingResult
    public String getContactModify(@PathVariable("id") Long id, Model model){
        Optional<Contact> getContacts = contactService.findByContactId(id);
        Contact editContact = getContacts.get();
        ContactForm contactForm = new ContactForm();
        log.info("getContactModify() process and get data from ContactService()");

        contactForm.setId(editContact.getId());
        contactForm.setName(editContact.getName());
        contactForm.setTel(editContact.getTel());
        contactForm.setEmail(editContact.getEmail());
        contactForm.setInquire(editContact.getInquire());
        contactForm.setNewsletter(editContact.isNewsletter());

        model.addAttribute("modifyForm", contactForm);
        log.info("getContactModify() process and setdata, ");
        return "page/contactModify";
    }

    @PostMapping("/modify/{id}") // contact 수정 로직 시작
    public String postContactModify(@PathVariable("id") Long id, @Valid @ModelAttribute("modifyForm") ContactForm modifyForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "page/contactModify";
        }

        Contact contact = new Contact();
        log.info("postContactModify() process now, /pape/contactMofidy 데이터 contact에 주입");
        contact.setId(id);
        contact.setName(modifyForm.getName());
        contact.setTel(modifyForm.getTel());
        contact.setEmail(modifyForm.getEmail());
        contact.setInquire(modifyForm.getInquire());
        contact.setNewsletter(modifyForm.isNewsletter());
        contactService.createContact(contact);
        log.info("postContactModify() update complete, dataInfo={id : "+contact.getId()+", name : "+contact.getName()+", tel : "+contact.getTel()+", inquire : "+contact.getInquire()+", newsletter : "+contact.isNewsletter()+"}");
        return String.format("redirect:/contactList");
    }

    @GetMapping("/delete/{id}")
    public String getContactDelete(@PathVariable("id") Long id) {
        Optional<Contact> findContact = contactService.findByContactId(id);
        if(findContact.isPresent()) {
            log.info("getContactDelete() for delete Object found, delete name : " + findContact.get().getName());
            contactService.deleteContact(findContact.get());
        }
        log.info("getContactDelete() process complete");
        return String.format("redirect:/contactList");
    }
}
