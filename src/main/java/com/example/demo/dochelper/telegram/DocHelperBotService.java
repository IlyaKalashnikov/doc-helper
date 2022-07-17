package com.example.demo.dochelper.telegram;

import com.example.demo.dochelper.model.entity.ClinicalRecommendationEntity;
import com.example.demo.dochelper.model.entity.MkbEntity;
import com.example.demo.dochelper.model.entity.clinrec_content.ClinRecContent;
import com.example.demo.dochelper.repository.mkb_repository.MkbRepository;
import com.example.demo.dochelper.service.ClinRecContentService;
import com.example.demo.dochelper.service.ClinRecService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocHelperBotService {
    private final MkbRepository mkbRepository;
    private final ClinRecService clinRecService;
    private final ClinRecContentService clinRecContentService;

    public DocHelperBotService(MkbRepository repository,
                               ClinRecService clinRecService,
                               ClinRecContentService clinRecContentService) {
        this.mkbRepository = repository;
        this.clinRecService = clinRecService;
        this.clinRecContentService = clinRecContentService;
    }

    public List<SendMessage> findMkbByDisease(Long chatId,
                                              String messageText) {
        List<MkbEntity> mkbEntityList = mkbRepository.findMkbByDisease(messageText.toLowerCase());
        List<SendMessage> messageList = new ArrayList<>();

        if (mkbEntityList.isEmpty()) {
            SendMessage message = new SendMessage();
            message.setText("По запросу \"" + messageText + "\" ничего не найдено." +
                    "Попробуйте другой запрос.");
            message.setChatId(chatId);
            messageList.add(message);
            return messageList;
        } else if (mkbEntityList.size() > 20) {
            SendMessage message = new SendMessage();
            message.setText("По запросу \"" + messageText + "\" найдено слишком много результатов." +
                    "\nПожалуйста, попробуйте уточнить запрос.");
            message.setChatId(chatId);
            messageList.add(message);
            return messageList;
        }

        for (MkbEntity entity : mkbEntityList) {
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Проверить наличие клинических рекоммендаций");
            button.setCallbackData("/checkClinRec " + entity.getCode());
            rowInline.add(button);
            rowsInline.add(rowInline);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(rowsInline);

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(entity.getCode() + " " + entity.getDisease());
            message.setReplyMarkup(inlineKeyboardMarkup);
            messageList.add(message);
        }
        return messageList;
    }

    public List<SendMessage> findClinRec(Long chatId, String mkb) {
        List<ClinicalRecommendationEntity> clinRecEntityList = clinRecService.findClinRecByMkb(mkb);
        List<SendMessage> resultList = new ArrayList<>();

        if (clinRecEntityList.isEmpty()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Для кода \"" + mkb + "\" клинических рекоммендаций не найдено.");
            resultList.add(sendMessage);
            return resultList;
        }


        for (ClinicalRecommendationEntity entity : clinRecEntityList) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Название: " + entity.getName() +
                    "\nID: " + entity.getId() +
                    "\nКоды МКБ-10: " + entity.getMkb().toString() +
                    "\nРазработчик: " + entity.getAuthor() +
                    "\nПринята: " + entity.getPublishDate());

            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Содержание");
            button.setCallbackData("/getClinRecContent " + entity.getId());
            rowInline.add(button);
            rowsInline.add(rowInline);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(rowsInline);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            resultList.add(sendMessage);
        }
        return resultList;
    }

    public List<SendMessage> getClinRecContents(Long chatId, String clinRecId) {
        List<ClinRecContent> clinRecContents = clinRecContentService.getClinRecContentsById(clinRecId);
        List<SendMessage> messageList = new ArrayList<>();

        for (ClinRecContent content : clinRecContents) {
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            InlineKeyboardButton htmlButton = new InlineKeyboardButton();
            htmlButton.setText("Читать");
//            HttpUrl httpUrl = new HttpUrl.Builder()
//                    .scheme("http")
//                    .host("localhost")
//                    .port(8080)                           //TODO Получить валидную урл
//                    .addPathSegment("api")
//                    .addPathSegment("clinrec")
//                    .addQueryParameter("title", content.getTitle())
//                    .addQueryParameter("id", clinRecId)
//                    .build();
//            String url = httpUrl.toString();
//            htmlButton.setUrl(url);
            if (content.getTitle().length() > 15) {
                String callbackData = content.getTitle().substring(0, 15);
                htmlButton.setCallbackData("/getHtml" + callbackData + ":" + clinRecId);
            } else {
                htmlButton.setCallbackData("/getHtml" + content.getTitle() + ":" + clinRecId);
            }
            rowInline.add(htmlButton);

//            InlineKeyboardButton textButton = new InlineKeyboardButton();
//            textButton.setText("Читать TXT");
//            if (content.getTitle().length() > 15) {
//                String callbackData = content.getTitle().substring(0, 15);
//                textButton.setCallbackData("/getTxt" + callbackData + ":" + clinRecId);
//            } else {
//                textButton.setCallbackData("/getTxt" + content.getTitle() + ":" + clinRecId);
//            }
//            rowInline.add(textButton);
            rowsInline.add(rowInline);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(rowsInline);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(content.getTitle());
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            messageList.add(sendMessage);
        }
        return messageList;
    }

    public SendDocument getHtmlContentByTitle(Long chatId, String callbackData) {
        String[] info = callbackData.split(":");
        String title = info[0];
        String clinRecId = info[1];
        List<ClinRecContent> clinRecContents = clinRecContentService.getClinRecContentsById(clinRecId);
        ClinRecContent clinRecContent = null;
        for (ClinRecContent content : clinRecContents) {
            if (content.getTitle().contains(title)) {
                clinRecContent = content;
            }
        }
        File tempFile;
        try {
            tempFile = java.io.File.createTempFile(clinRecId, ".html");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            writer.write(clinRecContent.getContent());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputFile inputFile = new InputFile(tempFile);
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(inputFile);
        tempFile.deleteOnExit();
        return sendDocument;
    }

//    public SendDocument getTextContentByTitle(Long chatId, String callbackData) {
//        String[] info = callbackData.split(":");
//        String title = info[0];
//        String clinRecId = info[1];
//        List<ClinRecContent> clinRecContents = clinRecContentService.getClinRecContentsById(clinRecId);
//        ClinRecContent clinRecContent = null;
//        for (ClinRecContent content : clinRecContents) {
//            if (content.getTitle().contains(title)) {
//                clinRecContent = content;
//            }
//        }
//        File tempFile;
//        try {
//
//            tempFile = java.io.File.createTempFile(clinRecContent.getTitle(), ".txt");
//            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
//            Document document = Jsoup.parse(clinRecContent.getContent());
//            writer.write(document.body().text());
//            writer.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        InputFile inputFile = new InputFile(tempFile);
//        SendDocument sendDocument = new SendDocument();
//        sendDocument.setChatId(chatId);
//        sendDocument.setDocument(inputFile);
//        tempFile.deleteOnExit();
//        return sendDocument;
//    }


}