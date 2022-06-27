package uz.itransition.personalcollectionmanagement.entity.enums;

public enum CustomFieldType {

    TEXT("String field"),
    TEXTAREA("Multi line text field"),
    CHECKBOX("Boolean checkbox field"),
    NUMBER("Integer field"),
    DATE("Date field");

    private final String dataTypeName;

    CustomFieldType(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public String getDataTypeName(){
        return dataTypeName;
    }
}
