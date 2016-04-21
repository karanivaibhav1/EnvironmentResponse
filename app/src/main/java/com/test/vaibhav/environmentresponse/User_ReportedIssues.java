package com.test.vaibhav.environmentresponse;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Date",
        "Description",
        "Reporter",
        "Type_Air",
        "Type_Other",
        "Type_Plant",
        "Type_Soil",
        "Type_Trash",
        "Type_Water"
})

public class User_ReportedIssues {
        @JsonProperty("Date")
        private String Date;
        @JsonProperty("Description")
        private String Description;
        @JsonProperty("Reporter")
        private String Reporter;
        @JsonProperty("Type_Air")
        private Integer TypeAir;
        @JsonProperty("Type_Other")
        private Integer TypeOther;
        @JsonProperty("Type_Plant")
        private Integer TypePlant;
        @JsonProperty("Type_Soil")
        private Integer TypeSoil;
        @JsonProperty("Type_Trash")
        private Integer TypeTrash;
        @JsonProperty("Type_Water")
        private Integer TypeWater;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/*        public User_ReportedIssues(String date, String desc, String reporter, Integer air, Integer other, Integer plant, Integer water, Integer soil,
                                   Integer trash){
                Date = date;
                Reporter = reporter;
                TypeAir=air;
                TypeOther=other;
                Description=desc;
                TypeWater=water;
                TypeSoil=soil;
                TypePlant=plant;
                TypeTrash=trash;
        }
*/
        /**
         *
         * @return
         * The Date
         */
        @JsonProperty("Date")
        public String getDate() {
            return Date;
        }

        /**
         *
         * @param Date
         * The Date
         */
        @JsonProperty("Date")
        public void setDate(String Date) {
            this.Date = Date;
        }

        /**
         *
         * @return
         * The Description
         */
        @JsonProperty("Description")
        public String getDescription() {
            return Description;
        }

        /**
         *
         * @param Description
         * The Description
         */
        @JsonProperty("Description")
        public void setDescription(String Description) {
            this.Description = Description;
        }

        /**
         *
         * @return
         * The Reporter
         */
        @JsonProperty("Reporter")
        public String getReporter() {
            return Reporter;
        }

        /**
         *
         * @param Reporter
         * The Reporter
         */
        @JsonProperty("Reporter")
        public void setReporter(String Reporter) {
            this.Reporter = Reporter;
        }

        /**
         *
         * @return
         * The TypeAir
         */
        @JsonProperty("Type_Air")
        public Integer getTypeAir() {
            return TypeAir;
        }

        /**
         *
         * @param TypeAir
         * The Type_Air
         */
        @JsonProperty("Type_Air")
        public void setTypeAir(Integer TypeAir) {
            this.TypeAir = TypeAir;
        }

        /**
         *
         * @return
         * The TypeOther
         */
        @JsonProperty("Type_Other")
        public Integer getTypeOther() {
            return TypeOther;
        }

        /**
         *
         * @param TypeOther
         * The Type_Other
         */
        @JsonProperty("Type_Other")
        public void setTypeOther(Integer TypeOther) {
            this.TypeOther = TypeOther;
        }

        /**
         *
         * @return
         * The TypePlant
         */
        @JsonProperty("Type_Plant")
        public Integer getTypePlant() {
            return TypePlant;
        }

        /**
         *
         * @param TypePlant
         * The Type_Plant
         */
        @JsonProperty("Type_Plant")
        public void setTypePlant(Integer TypePlant) {
            this.TypePlant = TypePlant;
        }

        /**
         *
         * @return
         * The TypeSoil
         */
        @JsonProperty("Type_Soil")
        public Integer getTypeSoil() {
            return TypeSoil;
        }

        /**
         *
         * @param TypeSoil
         * The Type_Soil
         */
        @JsonProperty("Type_Soil")
        public void setTypeSoil(Integer TypeSoil) {
            this.TypeSoil = TypeSoil;
        }

        /**
         *
         * @return
         * The TypeTrash
         */
        @JsonProperty("Type_Trash")
        public Integer getTypeTrash() {
            return TypeTrash;
        }

        /**
         *
         * @param TypeTrash
         * The Type_Trash
         */
        @JsonProperty("Type_Trash")
        public void setTypeTrash(Integer TypeTrash) {
            this.TypeTrash = TypeTrash;
        }

        /**
         *
         * @return
         * The TypeWater
         */
        @JsonProperty("Type_Water")
        public Integer getTypeWater() {
            return TypeWater;
        }

        /**
         *
         * @param TypeWater
         * The Type_Water
         */
        @JsonProperty("Type_Water")
        public void setTypeWater(Integer TypeWater) {
            this.TypeWater = TypeWater;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }


}