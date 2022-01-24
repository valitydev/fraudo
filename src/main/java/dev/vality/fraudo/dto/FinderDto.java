package dev.vality.fraudo.dto;

import dev.vality.fraudo.finder.InListFinder;
import lombok.Data;

@Data
public class FinderDto<T, U> {

    private InListFinder<T, U> listFinder;

}
