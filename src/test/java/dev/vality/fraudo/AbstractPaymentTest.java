package dev.vality.fraudo;

import dev.vality.fraudo.FraudoPaymentParser.ParseContext;
import dev.vality.fraudo.aggregator.UniqueValueAggregator;
import dev.vality.fraudo.dto.AggregatorDto;
import dev.vality.fraudo.dto.FinderDto;
import dev.vality.fraudo.dto.ResolverDto;
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
import org.junit.Before;
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
    private AggregatorDto<PaymentModel, PaymentCheckedField> aggregatorDto;
    private ResolverDto<PaymentModel, PaymentCheckedField> resolverDto;
    private FinderDto<PaymentModel, PaymentCheckedField> finderDto;

    @Before
    public void setUp() {
        aggregatorDto = new AggregatorDto<>();
        aggregatorDto.setSumPaymentAggregator(sumPaymentAggregator);
        aggregatorDto.setUniqueValueAggregator(uniqueValueAggregator);
        aggregatorDto.setCountPaymentAggregator(countPaymentAggregator);
        resolverDto = new ResolverDto<>();
        resolverDto.setCountryResolver(countryResolver);
        resolverDto.setCustomerTypeResolver(customerTypeResolver);
        resolverDto.setPaymentGroupResolver(paymentGroupResolver);
        resolverDto.setFieldPairResolver(fieldResolver);
        resolverDto.setTimeWindowResolver(timeWindowResolver);
        resolverDto.setPaymentTypeResolver(paymentModelPaymentTypeResolver);
        finderDto = new FinderDto<>();
        finderDto.setListFinder(inListFinder);
    }

    ResultModel parseAndVisit(InputStream resourceAsStream) throws IOException {
        ParseContext parse = getParseContext(resourceAsStream);
        return invokeParse(parse);
    }

    ResultModel invokeParse(ParseContext parse) {
        PaymentModel model = new PaymentModel();
        return invoke(parse, model);
    }

    ResultModel invoke(ParseContext parse, PaymentModel model) {
        return new FraudVisitorFactoryImpl()
                .createVisitor(aggregatorDto, resolverDto, finderDto)
                .visit(parse, model);
    }

    ResultModel invokeFullVisitor(ParseContext parse, PaymentModel model) {
        return new FullVisitorFactoryImpl()
                .createVisitor(aggregatorDto, resolverDto, finderDto)
                .visit(parse, model);
    }

    ParseContext getParseContext(InputStream resourceAsStream) throws IOException {
        FraudoPaymentLexer lexer =
                new FraudoPaymentLexer(new ANTLRInputStream(resourceAsStream));
        FraudoPaymentParser parser =
                new FraudoPaymentParser(new CommonTokenStream(lexer));
        return parser.parse();
    }

}
