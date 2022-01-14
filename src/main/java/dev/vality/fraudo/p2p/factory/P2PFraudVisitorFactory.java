package dev.vality.fraudo.p2p.factory;

import dev.vality.fraudo.aggregator.CountAggregator;
import dev.vality.fraudo.aggregator.SumAggregator;
import dev.vality.fraudo.aggregator.UniqueValueAggregator;
import dev.vality.fraudo.finder.InListFinder;
import dev.vality.fraudo.model.BaseModel;
import dev.vality.fraudo.p2p.resolver.P2PGroupResolver;
import dev.vality.fraudo.p2p.resolver.P2PTimeWindowResolver;
import dev.vality.fraudo.p2p.visitor.CountP2PVisitor;
import dev.vality.fraudo.p2p.visitor.CustomP2PFuncVisitor;
import dev.vality.fraudo.p2p.visitor.ListP2PVisitor;
import dev.vality.fraudo.p2p.visitor.SumP2PVisitor;
import dev.vality.fraudo.p2p.visitor.impl.*;
import dev.vality.fraudo.resolver.CountryResolver;
import dev.vality.fraudo.resolver.FieldResolver;

public class P2PFraudVisitorFactory implements FraudP2PVisitorFactory {

    @Override
    public <T extends BaseModel, U> FirstFindP2PVisitorImpl<T, U> createVisitor(
            CountAggregator<T, U> countAggregator,
            SumAggregator<T, U> sumAggregator,
            UniqueValueAggregator<T, U> uniqueValueAggregator,
            CountryResolver<U> countryResolver,
            InListFinder<T, U> listFinder,
            FieldResolver<T, U> fieldResolver,
            P2PGroupResolver<T, U> groupResolver,
            P2PTimeWindowResolver timeWindowResolver) {
        CountP2PVisitor<T> countVisitor = new CountP2PVisitorImpl<>(countAggregator, fieldResolver, groupResolver, timeWindowResolver);
        SumP2PVisitor<T> sumVisitor = new SumP2PVisitorImpl<>(sumAggregator, fieldResolver, groupResolver, timeWindowResolver);
        ListP2PVisitor<T> listVisitor = new ListP2PVisitorImpl<>(listFinder, fieldResolver);
        CustomP2PFuncVisitor<T> customFuncVisitor = new CustomP2PFuncVisitorImpl<>(uniqueValueAggregator, countryResolver,
                fieldResolver, groupResolver, timeWindowResolver);
        return new FirstFindP2PVisitorImpl<>(countVisitor, sumVisitor, listVisitor, customFuncVisitor, fieldResolver);
    }

}
