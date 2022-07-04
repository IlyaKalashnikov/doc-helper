package service;

import lombok.AllArgsConstructor;
import lombok.Builder;

import model.entity.ClinicalRecommendationEntity;
import model.entity.MkbEntity;
import org.hibernate.Session;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import util.SessionUtil;


import java.util.List;


@AllArgsConstructor
@Builder
public class DocHelperService {
    public void getMkbForDisease(String disease) {
        try (Session session = SessionUtil.getSession()) {
            Query<MkbEntity> query = session.createQuery(
                    "from MkbEntity entity where entity.disease like :name",
                    MkbEntity.class
            );
            query.setParameter("name", "%" + disease.substring(1).toLowerCase() + "%");
            List<MkbEntity> resultList = query.getResultList();
            if (resultList.isEmpty()) {
                System.out.println("По запросу " + "\"" + disease + "\" в кодификаторе ничего не найдено." +
                        "\nПопробуйте еще раз");
                return;
            }
            resultList.forEach(System.out::println);
        }
    }

    public void showClinicalRecommendationForMkb(String mkb) {
        try (Session session = SessionUtil.getSession()) {
            NativeQuery<ClinicalRecommendationEntity> query = session.createNativeQuery(
                    "SELECT * FROM recommendations WHERE mkb @> '[\"" + mkb + "\"]'",
                    ClinicalRecommendationEntity.class
            );
            List<ClinicalRecommendationEntity> resultList = query.getResultList();
            if (resultList.isEmpty()){
                System.out.println("По коду " + "\"" + mkb + "\" клинических рекоммендаций не найдено." +
                        "\nПопробуйте еще раз");
                return;
            }
            resultList.forEach(System.out::println);
        }
    }
}

