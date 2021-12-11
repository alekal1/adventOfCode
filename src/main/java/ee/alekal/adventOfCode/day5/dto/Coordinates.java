package ee.alekal.adventOfCode.day5.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class Coordinates {

    private final Point startPoint;
    private final Point endPoint;

    private Coordinates(Builder builder) {
        this.startPoint = builder.startPoint;
        this.endPoint = builder.endPoint;
    }

    @Override
    public String toString() {
        return "Coordinates{"
                + startPoint.getX() + "," + startPoint.getY()
                + " -> "
                + endPoint.getX() + "," + endPoint.getY()
                + "}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private Point startPoint;
        private Point endPoint;

        public static Builder builder() {
            return new Builder();
        }

        public Builder setStartPoint(int x, int y) {
            this.startPoint = new Point(x, y);
            return this;
        }

        public Builder setEndPoint(int x, int y) {
            this.endPoint = new Point(x, y);
            return this;
        }

        public Coordinates build() {
            return new Coordinates(this);
        }
    }

}
