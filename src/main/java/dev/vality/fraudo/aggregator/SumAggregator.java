package dev.vality.fraudo.aggregator;

import dev.vality.fraudo.model.TimeWindow;

import java.util.List;

public interface SumAggregator<T, U> {

    Double sum(U checkedField, T model, TimeWindow timeWindow, List<U> fields);

}
