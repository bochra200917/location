package com.pfe.location.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.location.Models.Conversation;
import com.pfe.location.Models.Message;
import com.pfe.location.Services.ConversationService;
import com.pfe.location.Utils.MyResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path="/api/v1/conv")
@RequiredArgsConstructor
public class ConversationController {
    final ConversationService convService ;
    
    @PostMapping(path="/message/{id_sender}/{id_receiver}")
    public ResponseEntity<MyResponse> sendMessage(@RequestBody Message message, 
    @PathVariable("id_sender") long  id_sender , @PathVariable("id_receiver") long id_receiver){
        try{
            return convService.sendMessage(id_sender , id_receiver , message);
        }catch(Exception e){
            return ResponseEntity.status(500).body(new MyResponse(500,e.getMessage()));
        }
    }

    @GetMapping(path="/{id_conv}/sender/{sender_username}")
    public ResponseEntity<Conversation> getConvById(@PathVariable("id_conv") long id,@PathVariable("sender_username") String username){
        return ResponseEntity.ok(convService.getConversationById(id,username));
    }

    @GetMapping("/conversationsbyidperson/{id_person}")
    public ResponseEntity<?> selectConvsByPersonId(@PathVariable("id_person") long id_person){
        List<Conversation> conversations = convService.selectConvsByPersonId(id_person); 
        if(conversations.size() == 0){
            return ResponseEntity.status(404).body(new MyResponse(404,"Empty list!"));
        }
        return ResponseEntity.ok(new MyResponse(200,conversations));
    }

}
