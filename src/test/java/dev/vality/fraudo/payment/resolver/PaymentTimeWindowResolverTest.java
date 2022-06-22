package dev.vality.fraudo.payment.resolver;

import dev.vality.fraudo.FraudoPaymentLexer;
import dev.vality.fraudo.FraudoPaymentParser;
import dev.vality.fraudo.model.TimeWindow;
import dev.vality.fraudo.resolver.TimeWindowResolver;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class PaymentTimeWindowResolverTest {

    private final TimeWindowResolver<FraudoPaymentParser.Time_windowContext> timeWindowResolver =
            new PaymentTimeWindowResolver();

    @Test
    void withoutEndTimeAndWithoutTimeUnit() throws Exception {
        FraudoPaymentParser.Time_windowContext timeWindowContext =
                getTimeWindowContext("/rules/time_window/withoutEndTimeAndWithoutTimeUnit.frd");

        TimeWindow timeWindow = timeWindowResolver.resolve(timeWindowContext);

        assertNotNull(timeWindow);
        assertEquals(24, timeWindow.getStart());
        assertEquals(0, timeWindow.getEnd());
        assertEquals(ChronoUnit.HOURS, timeWindow.getTimeUnit());
    }

    @Test
    void withEndTimeAndWithoutTimeUnit() throws Exception {
        FraudoPaymentParser.Time_windowContext timeWindowContext =
                getTimeWindowContext("/rules/time_window/withEndTimeAndWithoutTimeUnit.frd");

        TimeWindow timeWindow = timeWindowResolver.resolve(timeWindowContext);

        assertNotNull(timeWindow);
        assertEquals(24, timeWindow.getStart());
        assertEquals(2, timeWindow.getEnd());
        assertEquals(ChronoUnit.HOURS, timeWindow.getTimeUnit());
    }

    @Test
    void withoutEndTimeAndWithDayTimeUnitTest() throws Exception {
        FraudoPaymentParser.Time_windowContext timeWindowContext =
                getTimeWindowContext("/rules/time_window/withoutEndTimeAndWithDayTimeUnit.frd");

        TimeWindow timeWindow = timeWindowResolver.resolve(timeWindowContext);

        assertNotNull(timeWindow);
        assertEquals(24, timeWindow.getStart());
        assertEquals(0, timeWindow.getEnd());
        assertEquals(ChronoUnit.DAYS, timeWindow.getTimeUnit());
    }

    @Test
    void withEndTimeAndHourTimeUnitTest() throws Exception {
        FraudoPaymentParser.Time_windowContext timeWindowContext =
                getTimeWindowContext("/rules/time_window/withEndTimeAndHourTimeUnit.frd");

        TimeWindow timeWindow = timeWindowResolver.resolve(timeWindowContext);

        assertNotNull(timeWindow);
        assertEquals(24, timeWindow.getStart());
        assertEquals(1, timeWindow.getEnd());
        assertEquals(ChronoUnit.HOURS, timeWindow.getTimeUnit());
    }

    @Test
    void withOneCalMonthsTimeUnitTest() throws Exception {
        FraudoPaymentParser.Time_windowContext timeWindowContext =
                getTimeWindowContext("/rules/time_window/withOneCalMonthsTimeUnit.frd");

        TimeWindow timeWindow = timeWindowResolver.resolve(timeWindowContext);

        LocalDate now = LocalDate.now();
        assertNotNull(timeWindow);
        assertEquals(now.getDayOfMonth(), timeWindow.getStart());
        assertEquals(ChronoUnit.DAYS, timeWindow.getTimeUnit());
    }

    @Test
    void withThreeCalMonthsTimeUnitTest() throws Exception {
        FraudoPaymentParser.Time_windowContext timeWindowContext =
                getTimeWindowContext("/rules/time_window/withThreeCalMonthsTimeUnit.frd");

        TimeWindow timeWindow = timeWindowResolver.resolve(timeWindowContext);

        LocalDate now = LocalDate.now();
        int startForThreeCalMonths = now.getDayOfMonth() + now.minusMonths(1).lengthOfMonth() +
                now.minusMonths(2).lengthOfMonth();
        assertNotNull(timeWindow);
        assertEquals(startForThreeCalMonths, timeWindow.getStart());
        assertEquals(ChronoUnit.DAYS, timeWindow.getTimeUnit());
    }

    @Test
    void withCalMonthsTimeUnitAndWithEndTimeTest() throws Exception {
        FraudoPaymentParser.Time_windowContext timeWindowContext =
                getTimeWindowContext("/rules/time_window/withCalMonthsTimeUnitAndWithEndTime.frd");

        TimeWindow timeWindow = timeWindowResolver.resolve(timeWindowContext);

        LocalDate now = LocalDate.now();
        int startForFourCalMonths = now.getDayOfMonth() + now.minusMonths(1).lengthOfMonth() +
                now.minusMonths(2).lengthOfMonth() + now.minusMonths(3).lengthOfMonth();
        int endForTwoCalMonths = now.minusMonths(1).lengthOfMonth() + now.minusMonths(2).lengthOfMonth();
        assertNotNull(timeWindow);
        assertEquals(startForFourCalMonths, timeWindow.getStart());
        assertEquals(endForTwoCalMonths, timeWindow.getEnd());
        assertEquals(ChronoUnit.DAYS, timeWindow.getTimeUnit());
    }

    private FraudoPaymentParser.Time_windowContext getTimeWindowContext(String path) throws IOException {
        InputStream resourceAsStream = PaymentTimeWindowResolverTest.class.getResourceAsStream(path);
        FraudoPaymentLexer lexer = new FraudoPaymentLexer(CharStreams.fromStream(resourceAsStream));
        FraudoPaymentParser parser =
                new FraudoPaymentParser(new CommonTokenStream(lexer));
        FraudoPaymentParser.Time_windowContext timeWindowContext = parser.time_window();
        return timeWindowContext;
    }
}