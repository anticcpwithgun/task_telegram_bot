package me.telegram.kbot.handle;

import lombok.extern.slf4j.Slf4j;
import me.telegram.kbot.KBot;
import me.telegram.kbot.constant.BotConstant;
import me.telegram.kbot.model.Hero;
import me.telegram.kbot.model.Task;
import me.telegram.kbot.service.HeroService;
import me.telegram.kbot.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author freealways
 * @date 2020/7/31 15:51
 */
@Service
@Slf4j
public class TaskHandler {
    @Autowired
    private KBot kBot;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HeroService heroService;

    public void taskList(Integer page, Update update) {
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
    }

    public void taskDetail(String taskId, Update update) {
        log.debug(taskId);
        Task task = taskService.findByTaskId(taskId);
        String strDateFormat = "yyyy年MM月dd日 HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String text = String.format(BotConstant.taskInfo,
                task.getTitle(),
                task.getContent(),
                sdf.format(task.getCreateDate()),
                task.getActionIntegral(),
                task.getCheckIntegral(),
                task.getReward(),
                task.getRemark());
        CallbackQuery callbackQuery = update.getCallbackQuery();
        callbackQuery.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("领取").setCallbackData("taskDetail/receive/" + task.getTaskId()));
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);
        try {
            kBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void taskReceive(String taskId, Update update) {
        log.debug(taskId);
        log.debug("heroId:{}",update.getCallbackQuery().getFrom().getId());
        Hero hero = heroService.findById(String.valueOf(update.getCallbackQuery().getFrom().getId()));
        //todo check hero status
        String text = taskService.receiveTask(taskId,hero.getHeroId());
        this.sendMsg(update.getCallbackQuery().getMessage().getChatId().toString(),text);
    }
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            kBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
