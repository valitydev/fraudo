package dev.vality.fraudo.p2p.visitor;

import dev.vality.fraudo.FraudoP2PParser;

public interface ListP2PVisitor<T> {

    Boolean visitInWhiteList(FraudoP2PParser.In_white_listContext ctx, T model);

    Boolean visitInBlackList(FraudoP2PParser.In_black_listContext ctx, T model);

    Boolean visitInGreyList(FraudoP2PParser.In_grey_listContext ctx, T model);

    Boolean visitInList(FraudoP2PParser.In_listContext ctx, T model);

}
