package dev.vality.fraudo.bundle;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitorBundle<T, U> {

    private AggregatorBundle<T, U> aggregatorBundle;
    private ResolverBundle<T, U> resolverBundle;
    private FinderBundle<T, U> finderBundle;

}
