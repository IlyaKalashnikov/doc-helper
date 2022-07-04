package mapper;

import model.dto.clinical_recommendation.ClinicalRecommendationDto;
import model.dto.mkb.MkbDiseaseCodePair;
import model.dto.mkb.MkbDto;
import model.entity.ClinicalRecommendationEntity;
import model.entity.MkbEntity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DocHelperMapper {
    public List<MkbEntity> mkbDtoToEntity(MkbDto mkbDto) {
        return mkbDto.getList().stream()
                .map(mkbDiseaseCodePairs ->
                        MkbEntity.builder()
                                .code(getMkbCode(mkbDiseaseCodePairs))
                                .disease(getMkbName(mkbDiseaseCodePairs))
                                .build())
                .toList();
    }

    public ClinicalRecommendationEntity clinRecDtoToEntity(ClinicalRecommendationDto dto) {
        return ClinicalRecommendationEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .mkb(dto.getMkb())
                .publishDate(new Date(Long.parseLong(dto.getPublishDate().substring(6, 19))))
                .author(dto.getAss())
                .build();
    }

    private static String getMkbName(List<MkbDiseaseCodePair> pairList) {
        String name = null;
        for (MkbDiseaseCodePair pair : pairList) {
            if (pair.getColumn().equalsIgnoreCase("MKB_NAME")) {
                name = pair.getValue();
            }
        }
        return name;
    }

    private static String getMkbCode(List<MkbDiseaseCodePair> pairList) {
        String code = null;
        for (MkbDiseaseCodePair pair : pairList) {
            if (pair.getColumn().equalsIgnoreCase("MKB_CODE")) {
                code = pair.getValue();
            }
        }
        return code;
    }
}