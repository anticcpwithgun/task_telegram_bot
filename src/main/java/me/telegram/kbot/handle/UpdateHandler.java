package me.telegram.kbot.handle;

import me.telegram.kbot.KBot;
import me.telegram.kbot.constant.BotConstant;
import me.telegram.kbot.model.Hero;
import me.telegram.kbot.model.Task;
import me.telegram.kbot.service.HeroService;
import me.telegram.kbot.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author freealways
 * @date 2020/7/31 9:53
 */
@Service
public class UpdateHandler {
    @Autowired
    private KBot kBot;
    @Autowired
    private HeroService heroService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskHandler taskHandler;
    //info - 个人信息
    //task - 我的任务
    //task_list - 任务列表
    public void distribute(Update update){
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            if (data.contains("taskList/page")) {
                String[] pageInfo = data.split("/");
                taskHandler.taskList(Integer.valueOf(pageInfo[2]),update);
            }
            if (data.contains("taskList/detail")) {
                String[] detailInfo = data.split("/");
                taskHandler.taskDetail(detailInfo[2],update);
            }
            if (data.contains("taskDetail/receive")) {
                String[] actionInfo = data.split("/");
                taskHandler.taskReceive(actionInfo[2],update);
            }

            return;
        }
        String text = update.getMessage().getText();
        switch (text){
            case "/start":{
                this.start(update);
                break;
            }
            case "/info":{
                this.integral(update);
                break;
            }
            case "/task":{
                this.task(update);
                break;
            }
            case "/task_list":{
                this.taskList(update);
                break;
            }
            default:{
                this.general(update);
            }
        }
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

    public void start(Update update){
        User user = update.getMessage().getFrom();
        Hero hero = heroService.findById(String.valueOf(user.getId()));

        if (hero == null) {
            hero = new Hero();
            hero.setHeroId(String.valueOf(user.getId()));
            hero.setIntegral(0);
            hero.setLevel(0);
            hero.setTaskReceiveTimes(0);
            hero.setTaskActionTimes(0);
            hero.setActionSuccessTimes(0);
            hero.setActionFailTimes(0);
            hero.setCheckSuccessTimes(0);
            hero.setCheckFailTimes(0);
        }
        if(user.getUserName() != null){
            hero.setUserName(user.getUserName());
        }
        if(user.getLastName()!=null){
            hero.setName(user.getFirstName()+" "+user.getLastName());
        }else {
            hero.setName(user.getFirstName());
        }
        heroService.save(hero);

        sendMsg(update.getMessage().getChatId().toString(), BotConstant.start);
    }
    public void integral(Update update){
        String heroId = String.valueOf(update.getMessage().getFrom().getId());
        Hero hero = heroService.findById(heroId);
        String text = String.format(BotConstant.integralInfo,
                hero.getIntegral(),hero.getLevel(),
                hero.getTaskReceiveTimes(),
                hero.getTaskActionTimes(),
                hero.getActionSuccessTimes(),
                hero.getActionFailTimes());
        sendMsg(update.getMessage().getChatId().toString(), text);
    }
    public void task(Update update){
        String text;
        String heroId = String.valueOf(update.getMessage().getFrom().getId());
        Hero hero = heroService.findById(heroId);
        List<Task> taskList = taskService.listByHeroIdAndStatus(heroId,1);
        if (taskList.isEmpty()) {
            text = "未领取任务";
        }else {
            Task task = taskList.get(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            text =  String.format(BotConstant.taskInfo,
                    task.getTitle(),
                    task.getContent(),
                    sdf.format(task.getCreateDate()),
                    task.getActionIntegral(),
                    task.getCheckIntegral(),
                    task.getReward() == null ?"无":task.getReward(),
                    task.getRemark());
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(text);
        try {
            kBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void taskList(Update update){
        String text = "任务列表";
        String heroId = String.valueOf(update.getMessage().getFrom().getId());
        Hero hero = heroService.findById(heroId);
        Integer page = 0;
        Integer size = 10;
        Page<Task> taskPage = taskService.listByLevelAndPage(page,size,hero.getLevel());

        SendMessage sendMessage = new SendMessage();
        if (taskPage.getTotalElements() == 0) {
            text = "暂时未发布任务";
            sendMessage.setText(text);
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            try {
                kBot.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return;
        }
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        List<Task> taskList = taskPage.getContent();
        for (Task task : taskList) {
            rowInline.add(new InlineKeyboardButton().setText(task.getTitle()).setCallbackData("taskList/detail/"+task.getTaskId()));
        }
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
    public void general(Update update){
        String text = "if you are not for yourself,who will?";
        sendMsg(update.getMessage().getChatId().toString(),text);
    }
}
