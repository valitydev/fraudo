package dev.vality.fraudo.bundle;

import dev.vality.fraudo.aggregator.UniqueValueAggregator;
import dev.vality.fraudo.payment.aggregator.CountPaymentAggregator;
import dev.vality.fraudo.payment.aggregator.SumPaymentAggregator;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AggregatorBundle<T, U> {

    private CountPaymentAggregator<T, U> countPaymentAggregator;
    private SumPaymentAggregator<T, U> sumPaymentAggregator;
    private UniqueValueAggregator<T, U> uniqueValueAggregator;

}
