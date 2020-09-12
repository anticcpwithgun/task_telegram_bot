package me.telegram.kbot;

import lombok.extern.slf4j.Slf4j;
import me.telegram.kbot.handle.UpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * @author freealways
 * @date 2020/7/28 16:13
 */
@Component
@Slf4j
public class KBot extends TelegramLongPollingBot {
    @Autowired
    private UpdateHandler updateHandler;
    @Value("${bot.username}")
    private String username;
    @Value("${bot.token}")
    private String token;
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().toString());
        log.debug("text:{}",update.getMessage().getText());
        log.debug("message:{}",update.getMessage().toString());
    }


    public void onUpdatesReceived(List<Update> updates) {
        System.out.println("updates");
        updates.forEach(update->{
            updateHandler.distribute(update);
//                log.debug("text:{}",update.getMessage().getText());
//                log.debug("message:{}",update.getMessage().toString());
        });
    }
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}
