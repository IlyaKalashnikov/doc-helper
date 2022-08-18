package com.example.dochelper.telegram;

import com.example.dochelper.model.entity.ClinicalRecommendationEntity;
import com.example.dochelper.model.entity.MkbEntity;
import com.example.dochelper.model.entity.clinrec_content.ClinRecContent;
import com.example.dochelper.repository.mkb_repository.MkbRepository;
import com.example.dochelper.service.ClinRecContentService;
import com.example.dochelper.service.ClinRecService;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocHelperBotService {
    private final MkbRepository mkbRepository;
    private final ClinRecService clinRecService;
    private final ClinRecContentService clinRecContentService;

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
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            InlineKeyboardButton button = new InlineKeyboardButton();

            button.setText("Проверить наличие клинических рекоммендаций");
            button.setCallbackData("/checkClinRec " + entity.getCode());
            rowInline.add(button);
            rowsInline.add(rowInline);
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
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            InlineKeyboardButton button = new InlineKeyboardButton();

            button.setText("Содержание");
            button.setCallbackData("/getClinRecContent " + entity.getId());
            rowInline.add(button);
            rowsInline.add(rowInline);

            inlineKeyboardMarkup.setKeyboard(rowsInline);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            resultList.add(sendMessage);
        }
        return resultList;
    }

    public List<SendMessage> getClinRecContent(Long chatId, String clinRecId) {
        Iterable<ClinRecContent> clinRecContents = clinRecContentService.getClinRecContentsById(clinRecId)
                .toIterable();
        List<SendMessage> messageList = new ArrayList<>();

        for (ClinRecContent content : clinRecContents) {
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            InlineKeyboardButton htmlButton = new InlineKeyboardButton();

            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("http")
                    .host("127.0.0.1")
                    .port(8080)
                    .addPathSegment("api")
                    .addPathSegment("clinrec")
                    .addQueryParameter("title", content.getTitle())
                    .addQueryParameter("id", clinRecId)
                    .build();
            String url = httpUrl.toString();
            htmlButton.setText("Читать");
            htmlButton.setUrl(url);
            rowInline.add(htmlButton);
            rowsInline.add(rowInline);
            inlineKeyboardMarkup.setKeyboard(rowsInline);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(content.getTitle());
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            messageList.add(sendMessage);
        }
        return messageList;
    }
}