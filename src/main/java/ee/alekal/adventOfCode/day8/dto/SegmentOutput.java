package ee.alekal.adventOfCode.day8.dto;

import lombok.Getter;

@Getter
public class SegmentOutput {

    String value;
    int represents;

    private SegmentOutput(Builder builder) {
        this.value = builder.value;
        this.represents = builder.represents;
    }

    public static class Builder {

        String value;
        int represents;

        public static Builder builder() {
            return new Builder();
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setRepresents(int valueLength) {
            switch (valueLength) {
                case 2:
                    represents = 1;
                    return this;
                case 4:
                    represents = 4;
                    return this;
                case 3:
                    represents = 7;
                    return this;
                case 7:
                    represents = 8;
                    return this;
                default:
                    represents = 99;
                    return this;
            }
        }

        public SegmentOutput build() {
            return new SegmentOutput(this);
        }
    }
}
