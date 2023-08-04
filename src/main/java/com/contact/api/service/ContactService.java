package com.contact.api.service;

import com.contact.api.dto.response.ContactResponseDto;
import com.contact.api.model.Contact;
import com.contact.api.repository.ContactRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact save(Contact contact) {
        return (Contact) contactRepository.save(contact);
    }

    public Object findByUuid(UUID uuid) {
        @NotNull Optional<Contact> contactExist = contactRepository.findById(uuid);
        System.out.println(contactExist);
        if (contactExist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ContactResponseDto("not found"));
        }
        return contactExist;
    }

    public Page<Contact> findAll(PageRequest page) {
        return contactRepository.findAll(page);
    }

    public ResponseEntity<Object> deleteByUuid(UUID uuid) {
        @NotNull Optional<Contact> contactExist = contactRepository.findById(uuid);
        if (contactExist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ContactResponseDto("not found"));
        }
        contactRepository.deleteById(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(new ContactResponseDto("Deleted"));
    }

    public ResponseEntity<Object> patchByUuid(UUID uuid, Contact contact) {
        @NotNull Optional<Contact> contactExist = contactRepository.findById(uuid);
        if (contactExist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ContactResponseDto("not found"));
        }
        return (ResponseEntity<Object>) contactRepository.save(contact);
    }
}
