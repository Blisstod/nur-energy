package kz.nur.energy.enums;

public enum UserTypeEnum {
    ADMIN("ADMIN", "Админ"),
    TAXI_DRIVER("TAXI_DRIVER","Водитель такси"),
    PHYSICAL_PERSON("PHYSICAL_PERSON", "Физ.Лицо");

    private String id;
    private String name;

    UserTypeEnum(String id, String name) {
        this.name= name;
        this.id = id;
    }

    public String getId() {
        return this.name();
    }

    public String getName() {
        return this.name;
    }
}