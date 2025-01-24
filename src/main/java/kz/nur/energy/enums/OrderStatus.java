package kz.nur.energy.enums;

/**
 * Тип заказа такси.
 */
public enum OrderStatus {

    NEW("Создан"),
    ACCEPTED("Принят"),
    IN_PROGRESS("В процессе"),
    FINISHED("Завершен"),
    REJECTED("Отменен пользователем");

    private String description;

    OrderStatus(String value) {
        this.description = value;
    }

    public String getId() {
        return this.name();
    }

    public String getDescription(){
        return description;
    }

    public static OrderStatus fromId(String id) {
        for (OrderStatus at : OrderStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
