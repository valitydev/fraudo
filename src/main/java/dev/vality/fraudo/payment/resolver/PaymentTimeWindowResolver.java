package dev.vality.fraudo.payment.resolver;

import dev.vality.fraudo.FraudoPaymentParser;
import dev.vality.fraudo.model.TimeWindow;
import dev.vality.fraudo.resolver.TimeWindowResolver;
import dev.vality.fraudo.utils.TextUtil;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static dev.vality.fraudo.constant.TimeUnit.*;


public class PaymentTimeWindowResolver implements TimeWindowResolver<FraudoPaymentParser.Time_windowContext> {

    @Override
    public TimeWindow resolve(FraudoPaymentParser.Time_windowContext ctx) {
        TimeWindow.TimeWindowBuilder builder = TimeWindow.builder();
        List<TerminalNode> times = ctx.INTEGER();
        String start = TextUtil.safeGetText(times.get(0));
        builder
                .start(Integer.parseInt(start))
                .timeUnit(ChronoUnit.HOURS);
        if (times.size() == 2) {
            String end = TextUtil.safeGetText(times.get(1));
            builder
                    .end(Integer.parseInt(end));
        }
        if (Objects.nonNull(ctx.time_unit())) {
            String timeUnit = ctx.time_unit().getText();
            builder
                    .timeUnit(resolveTimeUnit(timeUnit));
        }
        return builder.build();
    }

    private ChronoUnit resolveTimeUnit(String timeUnit) {
        return switch (timeUnit) {
            case MINUTES -> ChronoUnit.MINUTES;
            case DAYS, CALENDAR_MONTHS -> ChronoUnit.DAYS;
            default -> ChronoUnit.HOURS;
        };
    }

}
