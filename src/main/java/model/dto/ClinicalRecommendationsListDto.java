package model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class ClinicalRecommendationsListDto {
        public String id;
        public int status;
        public int db_id;
        public int code;
        public int version;
        public int ver;
        public int age;
        public String name;
        @JsonProperty("Mkb")
        public ArrayList<String> mkb;
        public String ass;
        public Date publish_date;
        @JsonProperty("NPC_approved")
        public boolean nPC_approved;
}
