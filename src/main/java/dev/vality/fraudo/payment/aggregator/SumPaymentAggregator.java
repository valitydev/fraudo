package dev.vality.fraudo.payment.aggregator;

import dev.vality.fraudo.aggregator.SumAggregator;
import dev.vality.fraudo.model.TimeWindow;

import java.util.List;

public interface SumPaymentAggregator<T, U> extends SumAggregator<T, U> {

    Double sumSuccess(U checkedField, T model, TimeWindow timeWindow, List<U> fields);

    Double sumError(U checkedField, T model, TimeWindow timeWindow, String errorCode, List<U> fields);

    Double sumError(U checkedField, T model, TimeWindow timeWindow, List<U> fields);

    Double sumChargeback(U checkedField, T model, TimeWindow timeWindow, List<U> fields);

    Double sumRefund(U checkedField, T model, TimeWindow timeWindow, List<U> fields);

}
