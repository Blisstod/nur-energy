package kz.nur.energy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.nur.energy.entity.Auto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Инфо для автомобилей")
public class AutoItem {
    @Schema(description = "UUID")
    private UUID id;

    @Schema(description = "Номер авто", name = "num", example = "wqe12342")
    private String num;

    @Schema(description = "Модель авто", name = "model;", example = "a8")
    private ModelItem model;

    @Schema(description = "Активен?", name = "isActive;", example = "true")
    private Boolean isActive;

    public static List<AutoItem> listOf(List<Auto> autoList) {
        if (autoList == null)
            return null;

        return autoList.stream()
                .map(AutoItem::of)
                .collect(Collectors.toList());
    }

    public static AutoItem of(Auto auto) {
        if (auto == null)
            return null;

        return AutoItem.builder()
                .id(auto.getId())
                .num(auto.getNum())
                .model(ModelItem.of(auto.getModel()))
                .isActive(auto.getIsActive())
                .build();
    }
}
