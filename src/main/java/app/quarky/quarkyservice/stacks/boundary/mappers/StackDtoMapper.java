package app.quarky.quarkyservice.stacks.boundary.mappers;

import app.quarky.quarkyservice.stacks.boundary.dtos.StackViewResponseDto;
import app.quarky.quarkyservice.stacks.control.objects.QuarkyStack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StackDtoMapper {

    default StackViewResponseDto map(QuarkyStack quarkyStack) {
        var openCards = quarkyStack.getOpenCards();
        var finishedCards = quarkyStack.getFinishedCards();
        return map(quarkyStack, openCards == null ? 0 : openCards.size(),
                finishedCards == null ? 0 : finishedCards.size());
    }

    StackViewResponseDto map(QuarkyStack quarkyStack,
                             Integer openCardCount,
                             Integer finishedCardCount);
}
