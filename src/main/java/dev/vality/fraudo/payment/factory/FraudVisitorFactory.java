package dev.vality.fraudo.payment.factory;

import dev.vality.fraudo.dto.AggregatorDto;
import dev.vality.fraudo.dto.FinderDto;
import dev.vality.fraudo.dto.ResolverDto;
import dev.vality.fraudo.model.BaseModel;
import dev.vality.fraudo.payment.visitor.impl.FirstFindVisitorImpl;

public interface FraudVisitorFactory {

    <T extends BaseModel, U> FirstFindVisitorImpl<T, U> createVisitor(AggregatorDto<T, U> aggregatorDto,
                                                                      ResolverDto<T, U> resolverDto,
                                                                      FinderDto<T, U> finderDto);

}
