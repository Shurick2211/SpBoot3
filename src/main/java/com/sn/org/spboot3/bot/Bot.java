package com.sn.org.spboot3.bot;

import com.sn.org.spboot3.model.Person;
import com.sn.org.spboot3.model.Post;
import com.sn.org.spboot3.model.Role;
import com.sn.org.spboot3.repo.PersonRepo;
import com.sn.org.spboot3.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.time.LocalDateTime;

@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String name;
    @Autowired
    PersonRepo personRepo;
    @Autowired
    ApplicationContext applicationContext;
    private  Person BOT=null;
    private void saveBotByDB() {
        if(personRepo.getByName(name)==null)
        try {

            personRepo.save(new Person(
                    applicationContext.getBean(Bot.class).getMe().getId(),
                    applicationContext.getBean(Bot.class).getBotUsername(),
                    applicationContext.getBean(Bot.class).getMe().getId().toString(),
                    applicationContext.getBean(Bot.class).getBotToken(),
                    Role.ADMIN,
                    true
            ));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        else BOT=personRepo.getByName(name);
    }

    @Autowired
    PostRepo postRepo;

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        saveBotByDB();
        if(update.getMessage().getText().startsWith("/")) {
            if (update.getMessage().getText().startsWith("/start")) {
                personRepo.save(new Person(
                        update.getMessage().getChatId(),
                        update.getMessage().getFrom().getFirstName(),
                        update.getMessage().getChatId().toString(),
                        "1",
                        Role.ADMIN,
                        true
                ));
                sendMessage(update.getMessage().getChatId().toString(),
                        "You login: "+ update.getMessage().getChatId().toString()+
                        "\n You password: 1"
                        );
            }
        }else{
                postRepo.save(new Post(
                        update.getMessage().getMessageId(),
                        personRepo.getByLogin(update.getMessage().getChatId().toString()),
                        update.getMessage().getText(),
                        LocalDateTime.now()
                ));
            sendMessage(update.getMessage().getChatId().toString(),
                    "OK!"
            );
            }





    }

    public void sendMessage(String chatId,String text){
        SendMessage sendMessage= new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        try {
           Integer messId= execute(sendMessage).getMessageId();

            postRepo.save(new Post(
                    messId,
                    BOT,
                    text,
                    LocalDateTime.now()
            ));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
