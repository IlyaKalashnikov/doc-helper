package com.example.demo.dochelper.telegram;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class DocHelperBot extends TelegramLongPollingBot {
    private final String token;
    private final String userName;
    private final DocHelperBotService docHelperBotService;

    public DocHelperBot(@Value("${bot.token}") String token,
                 @Value("${bot.username}") String userName, //TODO Check if the value could be operated via
                 DocHelperBotService docHelperBotService) { //TODO the annotation above the field
        this.token = token;
        this.userName = userName;
        this.docHelperBotService = docHelperBotService;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().contains("/getClinRecContent")) {
                String clinRecId = update.getCallbackQuery().getData().replace("/getClinRecContent ", "");
                Long chatId = update.getCallbackQuery().getMessage().getChatId();

                List<SendMessage> messageList = docHelperBotService.getClinRecContent(chatId, clinRecId);
                for (SendMessage message : messageList) {
                    execute(message);
                }
            } else if (update.getCallbackQuery().getData().contains("/checkClinRec")) {
                String mkb = update.getCallbackQuery().getData().replace("/checkClinRec ", "");
                Long chatId = update.getCallbackQuery().getMessage().getChatId();

                List<SendMessage> messageList = docHelperBotService.findClinRec(chatId, mkb);
                for (SendMessage message : messageList) {
                    execute(message);
                }
            }
        } else {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            List<SendMessage> messageList = docHelperBotService.findMkbByDisease(chatId, messageText);
            for (SendMessage message : messageList) {
                execute(message);
            }
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return userName;
    }
}
