package kz.nur.energy.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import kz.nur.energy.entity.AutoModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@Setter
public class ModelItem {
    @Schema(description = "UUID", name = "id", example = "264b77d2-80a6-4afb-2a5c-69285dbda04b")
    private UUID id;

    @Schema(description = "Название", name = "name", example = "a8")
    private String name;

    @Schema(description = "Бренд", name = "brand", example = "Audi")
    private BrandItem brand;

    public static List<ModelItem> listOf(List<AutoModel> autoModelList) {
        if (autoModelList == null)
            return null;

        return autoModelList.stream()
                .map(ModelItem::of)
                .collect(Collectors.toList());
    }

    public static ModelItem of(AutoModel autoModel) {
        if (autoModel == null)
            return null;

        return ModelItem.builder()
                .id(autoModel.getId())
                .name(autoModel.getName())
                .brand(BrandItem.of(autoModel.getBrand()))
                .build();
    }
}
