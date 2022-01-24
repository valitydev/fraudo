package dev.vality.fraudo.dto;

import dev.vality.fraudo.aggregator.UniqueValueAggregator;
import dev.vality.fraudo.payment.aggregator.CountPaymentAggregator;
import dev.vality.fraudo.payment.aggregator.SumPaymentAggregator;
import lombok.Data;

@Data
public class AggregatorDto<T, U> {

    private CountPaymentAggregator<T, U> countPaymentAggregator;
    private SumPaymentAggregator<T, U> sumPaymentAggregator;
    private UniqueValueAggregator<T, U> uniqueValueAggregator;

}
