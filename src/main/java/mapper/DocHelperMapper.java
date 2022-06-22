package mapper;

import model.dto.clinical_recommendations_passport_dto.ClinicalRecommendationPassportDto;
import model.dto.mkb_dto.MkbDiseaseCodePair;
import model.dto.mkb_dto.MkbDto;
import model.entity.ClinicalRecommendationPassportEntity;
import model.entity.MkbEntity;

import java.util.Date;
import java.util.List;

public class DocHelperMapper {
    public List<MkbEntity> mkbDtoToEntity(MkbDto mkbDto) {
        return mkbDto.getList().stream()
                .map(mkbDiseaseCodePairs -> {
                    MkbEntity mkbEntity = new MkbEntity();
                    for (MkbDiseaseCodePair pair : mkbDiseaseCodePairs) {
                        if (pair.getColumn().equalsIgnoreCase("MKB_CODE")) {
                            mkbEntity.setCode(pair.getValue());
                        } else if (pair.getColumn().equalsIgnoreCase("MKB_NAME")) {
                            mkbEntity.setDisease(pair.getValue());
                        }
                    }
                    return mkbEntity;
                })
                .toList();
    }

    public ClinicalRecommendationPassportEntity passportDtoToEntity(ClinicalRecommendationPassportDto dto) {
        return ClinicalRecommendationPassportEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .mkb(dto.getMkb())
                .publishDate(new Date(Long.parseLong(dto.getPublishDate().substring(6, 19))))
                .author(dto.getAss())
                .build();
    }

}