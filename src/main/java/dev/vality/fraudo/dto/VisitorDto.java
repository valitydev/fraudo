package dev.vality.fraudo.dto;

import lombok.Data;

@Data
public class VisitorDto<T, U> {

    private AggregatorDto<T, U> aggregatorDto;
    private ResolverDto<T, U> resolverDto;
    private FinderDto<T, U> finderDto;

}
