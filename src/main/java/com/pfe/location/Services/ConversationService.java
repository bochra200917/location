package com.pfe.location.Services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pfe.location.Models.Conversation;
import com.pfe.location.Models.Message;
import com.pfe.location.Models.Person;
import com.pfe.location.Repositories.ConversationRepo;
import com.pfe.location.Repositories.MessageRepo;
import com.pfe.location.Repositories.PersonRepo;
import com.pfe.location.Utils.MyResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConversationService {
    final ConversationRepo conversationRepo;
    final MessageRepo messageRepo;
    final PersonRepo personRepo;

    @Transactional
    public ResponseEntity<MyResponse> sendMessage(long id_sender, long id_receiver, Message message) {
        Optional<Conversation> conversation = conversationRepo.selectConvByPersonsIds(id_sender, id_receiver);
        Person sender = personRepo.findById(id_sender).get();
        Person receiver = personRepo.findById(id_receiver).get();
        if (conversation.isPresent()) {
            Collection<Message> messages = conversation.get().getMessages();
            message.setPerson(sender);
            message.setSend_date(LocalDate.now());
            message.setConversation(conversation.get());
            messages.add(message);
            messageRepo.save(message);
            return ResponseEntity.ok(new MyResponse(200, "Conversation updated successfully!"));
        } else {
            message.setPerson(sender);
            Conversation newConversation = new Conversation(LocalDate.now(), List.of(sender, receiver),
                    List.of(message));
            message.setConversation(newConversation);
            conversationRepo.save(newConversation);
            return ResponseEntity.ok(new MyResponse(200, "New conversation created!"));
        }
    }

    public Conversation getConversationById(long id_conv, String sender_username) {
        Conversation conv = this.conversationRepo.findById(id_conv).get();
        conv.getPersons().removeIf(elem -> elem.getUser().getUsername().equals(sender_username));
        return conv;
    }

    public List<Conversation> selectConvsByPersonId(long id_person) {
        List<Conversation> convs = conversationRepo.selectConvsByPersonId(id_person);
        for (Conversation elem : convs) {
            elem.getPersons().removeIf(p -> p.getId_person() == id_person);
        }
        return convs;
    }

}
