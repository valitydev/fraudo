package dev.vality.fraudo.p2p.visitor.impl;

import dev.vality.fraudo.aggregator.CountAggregator;
import dev.vality.fraudo.p2p.resolver.P2PGroupResolver;
import dev.vality.fraudo.p2p.resolver.P2PTimeWindowResolver;
import dev.vality.fraudo.p2p.visitor.CountP2PVisitor;
import dev.vality.fraudo.resolver.FieldResolver;
import dev.vality.fraudo.utils.TextUtil;
import lombok.RequiredArgsConstructor;

import static com.rbkmoney.fraudo.FraudoP2PParser.CountContext;

@RequiredArgsConstructor
public class CountP2PVisitorImpl<T, U> implements CountP2PVisitor<T> {

    private final CountAggregator<T, U> countAggregator;
    private final FieldResolver<T, U> fieldResolver;
    private final P2PGroupResolver<T, U> groupResolver;
    private final P2PTimeWindowResolver timeWindowResolver;

    @Override
    public Integer visitCount(CountContext ctx, T model) {
        String countTarget = TextUtil.safeGetText(ctx.STRING());
        return countAggregator.count(
                fieldResolver.resolveName(countTarget),
                model,
                timeWindowResolver.resolve(ctx.time_window()),
                groupResolver.resolve(ctx.group_by())
        );
    }

}
