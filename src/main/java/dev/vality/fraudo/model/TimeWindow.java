package dev.vality.fraudo.model;

import lombok.Builder;
import lombok.Data;

import java.time.temporal.ChronoUnit;

@Data
@Builder
public class TimeWindow {

    private Long startWindowTime;
    private Long endWindowTime;
    private ChronoUnit timeUnit;

}
