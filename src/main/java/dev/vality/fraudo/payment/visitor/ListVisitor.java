package dev.vality.fraudo.payment.visitor;

import static dev.vality.fraudo.FraudoPaymentParser.*;

public interface ListVisitor<T> {

    Boolean visitInWhiteList(In_white_listContext ctx, T model);

    Boolean visitInBlackList(In_black_listContext ctx, T model);

    Boolean visitInGreyList(In_grey_listContext ctx, T model);

    Boolean visitInList(In_listContext ctx, T model);

}
