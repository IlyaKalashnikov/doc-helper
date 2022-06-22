package service;

import client.DocHelperClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import mapper.DocHelperMapper;
import model.dto.clinical_recommendations_passport_dto.ClinicalRecommendationPassportDto;
import model.dto.mkb_dto.MkbDto;
import model.entity.ClinicalRecommendationPassportEntity;
import model.entity.MkbEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class DocHelperService {
    DocHelperClient docHelperClient;
    DocHelperMapper docHelperMapper;

    private static void printCodes(List<MkbEntity> mkbEntityList) {
        for (MkbEntity mkbEntity : mkbEntityList) {
            System.out.printf("%s: %s%n", mkbEntity.getDisease(), mkbEntity.getCode());
        }
    }

    public void getMkbCodeByDisease(String disease) {
        MkbDto mkbDto;
        try {
            mkbDto = docHelperClient.getMkbDtoByDisease(disease);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<MkbEntity> mkbEntityList = docHelperMapper.mkbDtoToEntity(mkbDto);
        printCodes(mkbEntityList);
        //СОХРАНЕНИЕ ЗАБОЛЕВАНИЯ И КОДА В БД С ДЕФОЛТНЫМ ЗНАЧЕНИЕМ ДОСТУПНОСТИ КЛИНРЕКОВ
    }

    //ПРОПИСАТЬ РЕАЛИЗАЦИЮ НА СЛУЧАЙ ДВУХ КОДОВ (ВЗРОСЛЫЕ И ДЕТИ)
    public void checkClinRecAvailability(String mkb) {
        List<ClinicalRecommendationPassportDto> availableClinRecs;
        try {
            availableClinRecs = docHelperClient.getAvailableClinRecs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Optional<ClinicalRecommendationPassportDto> result = availableClinRecs.stream()
                .filter(clinicalRecommendationPassportDto -> clinicalRecommendationPassportDto.getMkb().contains(mkb))
                .findFirst();
        if (result.isEmpty()) {
            System.out.println("К сожалению, для этого заболевания нет клинических рекомендаций.");
            return;
        }
        ClinicalRecommendationPassportEntity entity = docHelperMapper.passportDtoToEntity(result.get());
        //ПРОВЕРКА СТАТУСА ДОСТУПНОСТИ КЛИНРЕКОВ В БД И ОБНОВЛЕНИЕ РЕЗУЛЬТАТА ПРИ НЕОБХОДИМОСТИ
        System.out.println("Для этого заболевания доступна клиническая рекоммендация:");
        System.out.println(entity.toString());
    }
}
