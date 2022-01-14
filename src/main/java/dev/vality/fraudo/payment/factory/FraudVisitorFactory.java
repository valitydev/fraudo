package dev.vality.fraudo.payment.factory;

import dev.vality.fraudo.aggregator.UniqueValueAggregator;
import dev.vality.fraudo.finder.InListFinder;
import dev.vality.fraudo.model.BaseModel;
import dev.vality.fraudo.payment.aggregator.CountPaymentAggregator;
import dev.vality.fraudo.payment.aggregator.SumPaymentAggregator;
import dev.vality.fraudo.payment.resolver.CustomerTypeResolver;
import dev.vality.fraudo.payment.resolver.PaymentGroupResolver;
import dev.vality.fraudo.payment.resolver.PaymentTimeWindowResolver;
import dev.vality.fraudo.payment.resolver.PaymentTypeResolver;
import dev.vality.fraudo.payment.visitor.impl.FirstFindVisitorImpl;
import dev.vality.fraudo.resolver.CountryResolver;
import dev.vality.fraudo.resolver.FieldResolver;

public interface FraudVisitorFactory {

    <T extends BaseModel, U> FirstFindVisitorImpl<T, U> createVisitor(
            CountPaymentAggregator<T, U> countPaymentAggregator,
            SumPaymentAggregator<T, U> sumPaymentAggregator,
            UniqueValueAggregator<T, U> uniqueValueAggregator,
            CountryResolver<U> countryResolver,
            InListFinder<T, U> listFinder,
            FieldResolver<T, U> fieldPairResolver,
            PaymentGroupResolver<T, U> paymentGroupResolver,
            PaymentTimeWindowResolver timeWindowResolver,
            PaymentTypeResolver<T> paymentTypeResolver,
            CustomerTypeResolver<T> customerTypeResolver);

}
