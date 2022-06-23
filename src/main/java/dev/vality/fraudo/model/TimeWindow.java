package dev.vality.fraudo.model;

import lombok.Builder;
import lombok.Data;

import java.time.temporal.ChronoUnit;

@Data
@Builder
public class TimeWindow {

    private int start;
    private int end;
    private ChronoUnit timeUnit;

}
