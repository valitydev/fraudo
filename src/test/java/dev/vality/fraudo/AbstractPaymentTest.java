package dev.vality.fraudo;

import dev.vality.fraudo.FraudoPaymentParser.ParseContext;
import dev.vality.fraudo.aggregator.UniqueValueAggregator;
import dev.vality.fraudo.bundle.AggregatorBundle;
import dev.vality.fraudo.bundle.FinderBundle;
import dev.vality.fraudo.bundle.ResolverBundle;
import dev.vality.fraudo.bundle.VisitorBundle;
import dev.vality.fraudo.finder.InListFinder;
import dev.vality.fraudo.model.ResultModel;
import dev.vality.fraudo.payment.aggregator.CountPaymentAggregator;
import dev.vality.fraudo.payment.aggregator.SumPaymentAggregator;
import dev.vality.fraudo.payment.factory.FraudVisitorFactoryImpl;
import dev.vality.fraudo.payment.factory.FullVisitorFactoryImpl;
import dev.vality.fraudo.payment.resolver.CustomerTypeResolver;
import dev.vality.fraudo.payment.resolver.PaymentGroupResolver;
import dev.vality.fraudo.payment.resolver.PaymentTimeWindowResolver;
import dev.vality.fraudo.payment.resolver.PaymentTypeResolver;
import dev.vality.fraudo.resolver.CountryResolver;
import dev.vality.fraudo.resolver.FieldResolver;
import dev.vality.fraudo.test.constant.PaymentCheckedField;
import dev.vality.fraudo.test.model.PaymentModel;
import dev.vality.fraudo.test.payment.PaymentModelFieldResolver;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.mockito.Mock;

import java.io.IOException;
import java.io.InputStream;

public class AbstractPaymentTest {

    @Mock
    CountPaymentAggregator<PaymentModel, PaymentCheckedField> countPaymentAggregator;
    @Mock
    SumPaymentAggregator<PaymentModel, PaymentCheckedField> sumPaymentAggregator;
    @Mock
    UniqueValueAggregator<PaymentModel, PaymentCheckedField> uniqueValueAggregator;
    @Mock
    CountryResolver<PaymentCheckedField> countryResolver;
    @Mock
    InListFinder<PaymentModel, PaymentCheckedField> inListFinder;
    @Mock
    PaymentTimeWindowResolver timeWindowResolver;
    @Mock
    PaymentTypeResolver<PaymentModel> paymentModelPaymentTypeResolver;
    @Mock
    CustomerTypeResolver<PaymentModel> customerTypeResolver;

    private final FieldResolver<PaymentModel, PaymentCheckedField> fieldResolver = new PaymentModelFieldResolver();
    private final PaymentGroupResolver<PaymentModel, PaymentCheckedField> paymentGroupResolver =
            new PaymentGroupResolver<>(fieldResolver);

    ResultModel parseAndVisit(InputStream resourceAsStream) throws IOException {
        ParseContext parse = getParseContext(resourceAsStream);
        return invokeParse(parse);
    }

    ResultModel invokeParse(ParseContext parse) {
        PaymentModel model = new PaymentModel();
        return invoke(parse, model);
    }

    ResultModel invoke(ParseContext parse, PaymentModel model) {
        VisitorBundle<PaymentModel, PaymentCheckedField> visitorBundle = buildVisitorDto();
        return new FraudVisitorFactoryImpl()
                .createVisitor(visitorBundle)
                .visit(parse, model);
    }

    ResultModel invokeFullVisitor(ParseContext parse, PaymentModel model) {
        VisitorBundle<PaymentModel, PaymentCheckedField> visitorBundle = buildVisitorDto();
        return new FullVisitorFactoryImpl()
                .createVisitor(visitorBundle)
                .visit(parse, model);
    }

    private VisitorBundle<PaymentModel, PaymentCheckedField> buildVisitorDto() {
        var aggregatorBundle = AggregatorBundle.<PaymentModel, PaymentCheckedField>
                        builder()
                .sumPaymentAggregator(sumPaymentAggregator)
                .countPaymentAggregator(countPaymentAggregator)
                .uniqueValueAggregator(uniqueValueAggregator)
                .build();
        var resolverBundle = ResolverBundle.<PaymentModel, PaymentCheckedField>
                        builder()
                .countryResolver(countryResolver)
                .customerTypeResolver(customerTypeResolver)
                .paymentGroupResolver(paymentGroupResolver)
                .fieldPairResolver(fieldResolver)
                .timeWindowResolver(timeWindowResolver)
                .paymentTypeResolver(paymentModelPaymentTypeResolver)
                .build();
        var finderBundle = FinderBundle.<PaymentModel, PaymentCheckedField>
                        builder()
                .listFinder(inListFinder)
                .build();
        return VisitorBundle.<PaymentModel, PaymentCheckedField>
                        builder()
                .aggregatorBundle(aggregatorBundle)
                .resolverBundle(resolverBundle)
                .finderBundle(finderBundle)
                .build();
    }

    ParseContext getParseContext(InputStream resourceAsStream) throws IOException {
        FraudoPaymentLexer lexer =
                new FraudoPaymentLexer(new ANTLRInputStream(resourceAsStream));
        FraudoPaymentParser parser =
                new FraudoPaymentParser(new CommonTokenStream(lexer));
        return parser.parse();
    }

}
