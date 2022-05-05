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
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;


public class PaymentTimeWindowResolverTest {

    private TimeWindowResolver<FraudoPaymentParser.Time_windowContext> timeWindowResolver =
            new PaymentTimeWindowResolver();

    @Test
    void withoutEndTimeAndWithoutTimeUnit() throws Exception {
        FraudoPaymentParser.Time_windowContext timeWindowContext =
                getTimeWindowContext("/rules/time_window/withoutEndTimeAndWithoutTimeUnit.frd");

        TimeWindow timeWindow = timeWindowResolver.resolve(timeWindowContext);

        assertNotNull(timeWindow);
        assertEquals(24L, timeWindow.getStartWindowTime());
        assertNull(timeWindow.getEndWindowTime());
        assertNull(timeWindow.getTimeUnit());
    }

    @Test
    void withEndTimeAndWithoutTimeUnit() throws Exception {
        FraudoPaymentParser.Time_windowContext timeWindowContext =
                getTimeWindowContext("/rules/time_window/withEndTimeAndWithoutTimeUnit.frd");

        TimeWindow timeWindow = timeWindowResolver.resolve(timeWindowContext);

        assertNotNull(timeWindow);
        assertEquals(24L, timeWindow.getStartWindowTime());
        assertEquals(2L, timeWindow.getEndWindowTime());
        assertNull(timeWindow.getTimeUnit());
    }

    @Test
    void withoutEndTimeAndWithDayTimeUnitTest() throws Exception {
        FraudoPaymentParser.Time_windowContext timeWindowContext =
                getTimeWindowContext("/rules/time_window/withoutEndTimeAndWithDayTimeUnit.frd");

        TimeWindow timeWindow = timeWindowResolver.resolve(timeWindowContext);

        assertNotNull(timeWindow);
        assertEquals(24L, timeWindow.getStartWindowTime());
        assertNull(timeWindow.getEndWindowTime());
        assertEquals(ChronoUnit.DAYS, timeWindow.getTimeUnit());
    }

    @Test
    void withEndTimeAndHourTimeUnitTest() throws Exception {
        FraudoPaymentParser.Time_windowContext timeWindowContext =
                getTimeWindowContext("/rules/time_window/withEndTimeAndHourTimeUnit.frd");

        TimeWindow timeWindow = timeWindowResolver.resolve(timeWindowContext);

        assertNotNull(timeWindow);
        assertEquals(24L, timeWindow.getStartWindowTime());
        assertEquals(1L, timeWindow.getEndWindowTime());
        assertEquals(ChronoUnit.HOURS, timeWindow.getTimeUnit());
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