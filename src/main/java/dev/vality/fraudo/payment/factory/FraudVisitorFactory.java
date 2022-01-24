package dev.vality.fraudo.payment.factory;

import dev.vality.fraudo.dto.VisitorDto;
import dev.vality.fraudo.model.BaseModel;
import dev.vality.fraudo.payment.visitor.impl.FirstFindVisitorImpl;

public interface FraudVisitorFactory {

    <T extends BaseModel, U> FirstFindVisitorImpl<T, U> createVisitor(VisitorDto<T, U> dto);

}