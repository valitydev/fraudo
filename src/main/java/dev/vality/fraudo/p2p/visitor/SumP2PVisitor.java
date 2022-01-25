package dev.vality.fraudo.p2p.visitor;

import dev.vality.fraudo.FraudoP2PParser;

public interface SumP2PVisitor<T> {

    Double visitSum(FraudoP2PParser.SumContext ctx, T model);

}
