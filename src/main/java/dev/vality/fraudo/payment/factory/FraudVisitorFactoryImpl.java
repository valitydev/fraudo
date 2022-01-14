package dev.vality.fraudo.payment.factory;

import dev.vality.fraudo.aggregator.UniqueValueAggregator;
import dev.vality.fraudo.converter.TrustConditionConverter;
import dev.vality.fraudo.finder.InListFinder;
import dev.vality.fraudo.model.BaseModel;
import dev.vality.fraudo.payment.aggregator.CountPaymentAggregator;
import dev.vality.fraudo.payment.aggregator.SumPaymentAggregator;
import dev.vality.fraudo.payment.resolver.CustomerTypeResolver;
import dev.vality.fraudo.payment.resolver.PaymentGroupResolver;
import dev.vality.fraudo.payment.resolver.PaymentTimeWindowResolver;
import dev.vality.fraudo.payment.resolver.PaymentTypeResolver;
import dev.vality.fraudo.payment.visitor.*;
import dev.vality.fraudo.payment.visitor.impl.*;
import dev.vality.fraudo.resolver.CountryResolver;
import dev.vality.fraudo.resolver.FieldResolver;

public class FraudVisitorFactoryImpl implements FraudVisitorFactory {

    @Override
    public <T extends BaseModel, U> FirstFindVisitorImpl<T, U> createVisitor(
            CountPaymentAggregator<T, U> countPaymentAggregator,
            SumPaymentAggregator<T, U> sumPaymentAggregator,
            UniqueValueAggregator<T, U> uniqueValueAggregator,
            CountryResolver<U> countryResolver,
            InListFinder<T, U> listFinder,
            FieldResolver<T, U> fieldResolver,
            PaymentGroupResolver<T, U> paymentGroupResolver,
            PaymentTimeWindowResolver timeWindowResolver,
            PaymentTypeResolver<T> paymentTypeResolver,
            CustomerTypeResolver<T> customerTypeResolver) {
        CountVisitor<T> countVisitor =
                new CountVisitorImpl<>(countPaymentAggregator, fieldResolver, paymentGroupResolver, timeWindowResolver);
        SumVisitor<T> sumVisitor =
                new SumVisitorImpl<>(sumPaymentAggregator, fieldResolver, paymentGroupResolver, timeWindowResolver);
        ListVisitor<T> listVisitor = new ListVisitorImpl<>(listFinder, fieldResolver);
        CustomFuncVisitor<T> customFuncVisitor = new CustomFuncVisitorImpl<>(
                uniqueValueAggregator,
                countryResolver,
                fieldResolver,
                paymentGroupResolver,
                timeWindowResolver,
                paymentTypeResolver
        );
        IsTrustedFuncVisitor<T> isTrustedFuncVisitor = new IsTrustedFuncVisitorImpl<>(customerTypeResolver);
        TrustConditionConverter trustConditionConverter = new TrustConditionConverter();

        return new FirstFindVisitorImpl<>(
                countVisitor,
                sumVisitor,
                listVisitor,
                customFuncVisitor,
                isTrustedFuncVisitor,
                fieldResolver,
                trustConditionConverter
        );
    }

}
