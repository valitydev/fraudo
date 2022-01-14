package dev.vality.fraudo.p2p.factory;

import dev.vality.fraudo.aggregator.CountAggregator;
import dev.vality.fraudo.aggregator.SumAggregator;
import dev.vality.fraudo.aggregator.UniqueValueAggregator;
import dev.vality.fraudo.finder.InListFinder;
import dev.vality.fraudo.model.BaseModel;
import dev.vality.fraudo.p2p.resolver.P2PGroupResolver;
import dev.vality.fraudo.p2p.resolver.P2PTimeWindowResolver;
import dev.vality.fraudo.p2p.visitor.impl.FirstFindP2PVisitorImpl;
import dev.vality.fraudo.resolver.CountryResolver;
import dev.vality.fraudo.resolver.FieldResolver;

public interface FraudP2PVisitorFactory {

    <T extends BaseModel, U> FirstFindP2PVisitorImpl<T, U> createVisitor(
            CountAggregator<T, U> countAggregator,
            SumAggregator<T, U> sumAggregator,
            UniqueValueAggregator<T, U> uniqueValueAggregator,
            CountryResolver<U> countryResolver,
            InListFinder<T, U> listFinder,
            FieldResolver<T, U> fieldPairResolver,
            P2PGroupResolver<T, U> paymentGroupResolver,
            P2PTimeWindowResolver timeWindowResolver);

}
