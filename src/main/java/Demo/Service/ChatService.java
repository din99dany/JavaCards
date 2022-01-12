package Demo.Service;

import Demo.Model.Chat;
import Demo.Model.User;
import Demo.Repository.ChatRepository;
import Demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ChatService extends GeneralService<Chat>{

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean verifyChat( Chat chat ) {

        if ( chat == null || chat.getSender() == null || chat.getReceiver() == null ) {
            return false;
        }

        return userRepository.findById(chat.getSender().getId()).isPresent() &&
                userRepository.findById( chat.getReceiver().getId() ).isPresent();
    }

    public Optional<Chat> createChat(long senderId, long receiverId, String message ) {
        Chat chat = new Chat();

        Optional<User> sender = userRepository.findById( senderId );
        Optional<User> receiver = userRepository.findById( receiverId );

        if ( sender.isPresent() && receiver.isPresent() && message != null ) {
            chat.setSender(sender.get());
            chat.setReceiver(receiver.get());
            chat.setMessage(message);
            chat.setSendTime(new Date());

            if ( verifyChat(chat) ) {
                return Optional.of( chat );
            }
        }
        return Optional.empty();
    }

    @Override
    public JpaRepository<Chat, Long> getRepo() {
        return chatRepository;
    }

}
