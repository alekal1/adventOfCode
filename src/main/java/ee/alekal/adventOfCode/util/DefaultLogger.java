package ee.alekal.adventOfCode.util;

import ee.alekal.adventOfCode.day2.dto.MovementType;
import ee.alekal.adventOfCode.day2.dto.Movement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

public class DefaultLogger {

    public final static Logger LOG = LoggerFactory.getLogger(DefaultLogger.class);

    public static void logMovementTypeCount(List<Movement> movementList,
                                            MovementType movementType) {
        var predicateFilter = createMovementPredicateFilter(movementType);
        LOG.debug("{} type count {}",
                movementType,
                movementList.stream()
                    .filter(predicateFilter)
                    .count());
    }

    private static Predicate<Movement> createMovementPredicateFilter(MovementType type) {
        return movement -> type.equals(movement.getMovementType());
    }
}
