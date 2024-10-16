package dev.vality.fraudo.payment.visitor;

import static dev.vality.fraudo.FraudoPaymentParser.*;

public interface CustomFuncVisitor<T> {

    String visitCountryBy(Country_byContext ctx, T model);

    boolean visitLike(LikeContext ctx, T model);

    Integer visitUnique(UniqueContext ctx, T model);

    Integer visitRand(RandContext ctx, T model);

    boolean visitCheckMobile(Is_mobileContext ctx, T model);

    boolean visitCheckRecurrent(Is_recurrentContext ctx, T model);

}
