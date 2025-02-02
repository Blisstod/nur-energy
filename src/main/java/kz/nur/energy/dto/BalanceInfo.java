package kz.nur.energy.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
@Schema(description = "Получение инфо о баланса юзера")
public class BalanceInfo {
    @Schema(description = "Номер телефона пользователя", name = "phoneNum", example = "+77714597364")
    private String phoneNum;

    @Schema(description = "Id счета/баланса", name = "balanceId", example = "2a2e248d-f2cd-3e2d-4179-c171767c3068")
    private UUID balanceId;

    @Schema(description = "Текущий баланс", name = "balance", example = "2223")
    private Long balance;
}
