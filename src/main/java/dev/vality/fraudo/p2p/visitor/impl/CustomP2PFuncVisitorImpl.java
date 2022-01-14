package dev.vality.fraudo.p2p.visitor.impl;

import dev.vality.fraudo.aggregator.UniqueValueAggregator;
import dev.vality.fraudo.model.Pair;
import dev.vality.fraudo.p2p.resolver.P2PGroupResolver;
import dev.vality.fraudo.p2p.resolver.P2PTimeWindowResolver;
import dev.vality.fraudo.p2p.visitor.CustomP2PFuncVisitor;
import dev.vality.fraudo.resolver.CountryResolver;
import dev.vality.fraudo.resolver.FieldResolver;
import dev.vality.fraudo.utils.TextUtil;
import lombok.RequiredArgsConstructor;

import static com.rbkmoney.fraudo.FraudoP2PParser.*;

@RequiredArgsConstructor
public class CustomP2PFuncVisitorImpl<T, U> implements CustomP2PFuncVisitor<T> {

    private final UniqueValueAggregator<T, U> uniqueValueAggregator;
    private final CountryResolver<U> countryResolver;
    private final FieldResolver<T, U> fieldResolver;
    private final P2PGroupResolver<T, U> groupResolver;
    private final P2PTimeWindowResolver timeWindowResolver;

    @Override
    public String visitCountryBy(Country_byContext ctx, T model) {
        String fieldName = TextUtil.safeGetText(ctx.STRING());
        Pair<U, String> resolve = fieldResolver.resolve(fieldName, model);
        return countryResolver.resolveCountry(resolve.getFirst(), resolve.getSecond());
    }

    @Override
    public boolean visitLike(LikeContext ctx, T model) {
        String fieldName = TextUtil.safeGetText(ctx.STRING(0));
        String fieldValue = fieldResolver.resolve(fieldName, model).getSecond();
        String pattern = TextUtil.safeGetText(ctx.STRING(1));
        return fieldValue.matches(pattern);
    }

    @Override
    public Integer visitUnique(UniqueContext ctx, T model) {
        String field = TextUtil.safeGetText(ctx.STRING(0));
        String fieldBy = TextUtil.safeGetText(ctx.STRING(1));
        return uniqueValueAggregator.countUniqueValue(
                fieldResolver.resolveName(field),
                model,
                fieldResolver.resolveName(fieldBy),
                timeWindowResolver.resolve(ctx.time_window()),
                groupResolver.resolve(ctx.group_by())
        );
    }

}
