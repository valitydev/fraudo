package dev.vality.fraudo.payment.factory;

import dev.vality.fraudo.converter.TrustConditionConverter;
import dev.vality.fraudo.dto.AggregatorDto;
import dev.vality.fraudo.dto.FinderDto;
import dev.vality.fraudo.dto.ResolverDto;
import dev.vality.fraudo.dto.VisitorDto;
import dev.vality.fraudo.model.BaseModel;
import dev.vality.fraudo.payment.visitor.*;
import dev.vality.fraudo.payment.visitor.impl.*;

public class FraudVisitorFactoryImpl implements FraudVisitorFactory {

    @Override
    public <T extends BaseModel, U> FirstFindVisitorImpl<T, U> createVisitor(VisitorDto<T, U> visitorDto) {
        AggregatorDto<T, U> aggregatorDto = visitorDto.getAggregatorDto();
        ResolverDto<T, U> resolverDto = visitorDto.getResolverDto();
        FinderDto<T, U> finderDto = visitorDto.getFinderDto();
        CountVisitor<T> countVisitor = new CountVisitorImpl<>(aggregatorDto.getCountPaymentAggregator(),
                resolverDto.getFieldPairResolver(),
                resolverDto.getPaymentGroupResolver(),
                resolverDto.getTimeWindowResolver());
        SumVisitor<T> sumVisitor = new SumVisitorImpl<>(aggregatorDto.getSumPaymentAggregator(),
                resolverDto.getFieldPairResolver(),
                resolverDto.getPaymentGroupResolver(),
                resolverDto.getTimeWindowResolver());
        ListVisitor<T> listVisitor = new ListVisitorImpl<>(finderDto.getListFinder(),
                resolverDto.getFieldPairResolver());
        CustomFuncVisitor<T> customFuncVisitor = new CustomFuncVisitorImpl<>(
                aggregatorDto.getUniqueValueAggregator(),
                resolverDto.getCountryResolver(),
                resolverDto.getFieldPairResolver(),
                resolverDto.getPaymentGroupResolver(),
                resolverDto.getTimeWindowResolver(),
                resolverDto.getPaymentTypeResolver());
        IsTrustedFuncVisitor<T> isTrustedFuncVisitor =
                new IsTrustedFuncVisitorImpl<>(resolverDto.getCustomerTypeResolver());
        TrustConditionConverter trustConditionConverter = new TrustConditionConverter();

        return new FirstFindVisitorImpl<>(
                countVisitor,
                sumVisitor,
                listVisitor,
                customFuncVisitor,
                isTrustedFuncVisitor,
                resolverDto.getFieldPairResolver(),
                trustConditionConverter
        );
    }

}
