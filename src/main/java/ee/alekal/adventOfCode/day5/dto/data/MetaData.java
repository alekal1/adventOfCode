package ee.alekal.adventOfCode.day5.dto.data;

import ee.alekal.adventOfCode.day5.dto.Point;
import lombok.Getter;

import static ee.alekal.adventOfCode.day5.dto.data.DecreaseType.X_DECREASE;
import static ee.alekal.adventOfCode.day5.dto.data.DecreaseType.Y_DECREASE;

@Getter
public class MetaData {

    int counter;
    int loopStatement;
    DecreaseType decreaseType;

    private MetaData(int counter, int loopStatement, DecreaseType decreaseType) {
        this.counter = counter;
        this.loopStatement = loopStatement;
        this.decreaseType = decreaseType;
    }

    public static MetaData getHorizontalVerticalDataInstance(Point startPoint, Point endPoint) {
        if (startPoint.getX() > endPoint.getX()) {
            return new MetaData(startPoint.getX(), endPoint.getX(), X_DECREASE);
        }
        if (startPoint.getX() < endPoint.getX()) {
            return new MetaData(endPoint.getX(), startPoint.getX(), X_DECREASE);
        }
        if (startPoint.getY() > endPoint.getY()) {
            return new MetaData(startPoint.getY(), endPoint.getY(), Y_DECREASE);
        }
        if (startPoint.getY() < endPoint.getY()) {
            return new MetaData(endPoint.getY(), startPoint.getY(), Y_DECREASE);
        }
        throw new IllegalArgumentException("Cannot create meta data for horizontal or vertical condition.");
    }
}
