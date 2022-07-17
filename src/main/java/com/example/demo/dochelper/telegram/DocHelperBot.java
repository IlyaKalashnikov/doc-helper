package com.example.demo.dochelper.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class DocHelperBot extends TelegramLongPollingBot {
    private final String token;
    private final String userName;
    private final DocHelperBotService docHelperBotService;

    DocHelperBot(@Value("${bot.token}") String token,
                 @Value("${bot.username}") String userName,
                 DocHelperBotService docHelperBotService) {
        this.token = token;
        this.userName = userName;
        this.docHelperBotService = docHelperBotService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().contains("/getClinRecContent")) {
                String clinRecId = update.getCallbackQuery().getData().replace("/getClinRecContent ", "");
                Long chatId = update.getCallbackQuery().getMessage().getChatId();
                List<SendMessage> messageList = docHelperBotService.getClinRecContents(chatId, clinRecId);
                try {
                    for (SendMessage message : messageList) {
                        execute(message);
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getCallbackQuery().getData().contains("/checkClinRec")) {
                String mkb = update.getCallbackQuery().getData().replace("/checkClinRec ", "");
                Long chatId = update.getCallbackQuery().getMessage().getChatId();
                List<SendMessage> messageList = docHelperBotService.findClinRec(chatId, mkb);
                try {
                    for (SendMessage message : messageList) {
                        execute(message);
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getCallbackQuery().getData().contains("/getHtml")){
                String callbackData = update.getCallbackQuery().getData().replace("/getHtml", "");
                Long chatId = update.getCallbackQuery().getMessage().getChatId();
                SendDocument sendDocument = docHelperBotService.getHtmlContentByTitle(chatId, callbackData);
                try{
                    execute(sendDocument);
                } catch (TelegramApiException e){
                    e.printStackTrace();
                }
//            } else if (update.getCallbackQuery().getData().contains("/getTxt")){
//                String callbackData = update.getCallbackQuery().getData().replace("/getTxt", "");
//                Long chatId = update.getCallbackQuery().getMessage().getChatId();
//                SendDocument sendDocument = docHelperBotService.getTextContentByTitle(chatId, callbackData);
//                try{
//                    execute(sendDocument);
//                } catch (TelegramApiException e){
//                    e.printStackTrace();
//                }
            }
        } else {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            List<SendMessage> messageList = docHelperBotService.findMkbByDisease(chatId, messageText);
            try {
                for (SendMessage message : messageList) {
                    execute(message);
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
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
