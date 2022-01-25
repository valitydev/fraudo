package dev.vality.fraudo.bundle;

import dev.vality.fraudo.finder.InListFinder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinderBundle<T, U> {

    private InListFinder<T, U> listFinder;

}
