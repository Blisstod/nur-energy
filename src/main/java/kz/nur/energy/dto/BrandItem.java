package kz.nur.energy.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import kz.nur.energy.entity.AutoBrand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@Setter
public class BrandItem {
    @Schema(description = "UUID", name = "id", example = "264b77d2-80a6-4afb-2a5c-69285dbda04b")
    private UUID id;

    @Schema(description = "Название", name = "name", example = "audi")
    private String name;

    public static List<BrandItem> listOf(List<AutoBrand> autoBrandList) {
        if (autoBrandList == null)
            return null;

        return autoBrandList.stream()
                .map(BrandItem::of)
                .collect(Collectors.toList());
    }

    public static BrandItem of(AutoBrand autoBrand) {
        if (autoBrand == null)
            return null;

        return BrandItem.builder()
                .id(autoBrand.getId())
                .name(autoBrand.getName())
                .build();
    }

}