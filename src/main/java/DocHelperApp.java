import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import mapper.DocHelperMapper;
import model.dto.mkb_dto.MkbDto;
import model.entity.MkbEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DocHelperApp {
    @SneakyThrows
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        DocHelperMapper docHelperMapper = new DocHelperMapper();
        Path path = Paths.get("MKB_codes");
        File file = new File("src/main/resources/MKB_codes");

        MkbDto mkbDto = mapper.readValue(file, MkbDto.class);
        List<MkbEntity> mkbEntityList = docHelperMapper.mkbDtoToEntity(mkbDto);

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();



        try (SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
                Session session = sessionFactory.openSession()
        ) {
            Transaction transaction = session.beginTransaction();

            for (int i = 0; i < mkbEntityList.size(); i++) {
                MkbEntity entityToSave = new MkbEntity();
                entityToSave.setCode(mkbEntityList.get(i).getCode());
                entityToSave.setDisease(mkbEntityList.get(i).getDisease());
                session.persist(entityToSave);
            }
            transaction.commit();
        }
    }
}

