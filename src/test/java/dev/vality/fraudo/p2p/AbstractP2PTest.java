package dev.vality.fraudo.p2p;

import dev.vality.fraudo.aggregator.CountAggregator;
import dev.vality.fraudo.aggregator.SumAggregator;
import dev.vality.fraudo.aggregator.UniqueValueAggregator;
import dev.vality.fraudo.finder.InListFinder;
import dev.vality.fraudo.model.ResultModel;
import dev.vality.fraudo.p2p.factory.P2PFraudVisitorFactory;
import dev.vality.fraudo.p2p.resolver.P2PGroupResolver;
import dev.vality.fraudo.p2p.resolver.P2PTimeWindowResolver;
import dev.vality.fraudo.resolver.CountryResolver;
import dev.vality.fraudo.test.constant.P2PCheckedField;
import dev.vality.fraudo.test.model.P2PModel;
import dev.vality.fraudo.test.p2p.P2PModelFieldResolver;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.mockito.Mock;

import java.io.IOException;
import java.io.InputStream;

public class AbstractP2PTest {

    @Mock
    CountAggregator<P2PModel, P2PCheckedField> countAggregator;
    @Mock
    SumAggregator<P2PModel, P2PCheckedField> sumAggregator;
    @Mock
    UniqueValueAggregator<P2PModel, P2PCheckedField> uniqueValueAggregator;
    @Mock
    CountryResolver<P2PCheckedField> countryResolver;
    @Mock
    InListFinder<P2PModel, P2PCheckedField> listFinder;
    @Mock
    P2PTimeWindowResolver timeWindowResolver;

    private P2PModelFieldResolver fieldResolver = new P2PModelFieldResolver();
    private P2PGroupResolver<P2PModel, P2PCheckedField> paymentGroupResolver = new P2PGroupResolver<>(fieldResolver);


    ResultModel parseAndVisit(InputStream resourceAsStream) throws IOException {
        dev.vality.fraudo.FraudoP2PParser.ParseContext parse = getParseContext(resourceAsStream);
        return invokeParse(parse);
    }

    ResultModel invokeParse(dev.vality.fraudo.FraudoP2PParser.ParseContext parse) {
        P2PModel model = new P2PModel();
        return invoke(parse, model);
    }

    ResultModel invoke(dev.vality.fraudo.FraudoP2PParser.ParseContext parse, P2PModel model) {
        return new P2PFraudVisitorFactory()
                .createVisitor(
                        countAggregator,
                        sumAggregator,
                        uniqueValueAggregator,
                        countryResolver,
                        listFinder,
                        fieldResolver,
                        paymentGroupResolver,
                        timeWindowResolver)
                .visit(parse, model);
    }

    dev.vality.fraudo.FraudoP2PParser.ParseContext getParseContext(InputStream resourceAsStream) throws IOException {
        dev.vality.fraudo.FraudoP2PLexer lexer =
                new dev.vality.fraudo.FraudoP2PLexer(new ANTLRInputStream(resourceAsStream));
        dev.vality.fraudo.FraudoP2PParser parser =
                new dev.vality.fraudo.FraudoP2PParser(new CommonTokenStream(lexer));

        return parser.parse();
    }

}
