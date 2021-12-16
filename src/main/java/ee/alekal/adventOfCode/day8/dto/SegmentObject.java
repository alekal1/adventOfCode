package ee.alekal.adventOfCode.day8.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class SegmentObject {
    List<String> segmentsInput; // left part of input line
    List<SegmentOutput> segmentsOutput; // right part of input line

    private SegmentObject(Builder builder) {
        this.segmentsInput = builder.segmentsInput;
        this.segmentsOutput = builder.segmentsOutput;
    }


    public static class Builder {
        List<String> segmentsInput = new ArrayList<>(); // left part of input line
        List<SegmentOutput> segmentsOutput = new ArrayList<>(); // right part of input line

        public static Builder builder() {
            return new Builder();
        }

        public Builder addSegmentInput(String input) {
            segmentsInput.add(input);
            return this;
        }

        public Builder addSegmentOutput(SegmentOutput output) {
            segmentsOutput.add(output);
            return this;
        }

        public SegmentObject build() {
            return new SegmentObject(this);
        }
    }
}
