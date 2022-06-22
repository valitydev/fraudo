package dev.vality.fraudo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
public class TimeWindow {

    private int start;
    private int end;
    private ChronoUnit timeUnit;

}
