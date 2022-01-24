package dev.vality.fraudo.p2p.visitor;

import dev.vality.fraudo.FraudoP2PParser;

public interface CountP2PVisitor<T> {

    Integer visitCount(FraudoP2PParser.CountContext ctx, T model);

}