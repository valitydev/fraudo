package dev.vality.fraudo.payment.resolver;

import dev.vality.fraudo.FraudoPaymentParser;
import dev.vality.fraudo.model.TimeWindow;
import dev.vality.fraudo.resolver.TimeWindowResolver;
import dev.vality.fraudo.utils.TextUtil;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class PaymentTimeWindowResolver implements TimeWindowResolver<FraudoPaymentParser.Time_windowContext> {

    @Override
    public TimeWindow resolve(FraudoPaymentParser.Time_windowContext ctx) {
        TimeWindow.TimeWindowBuilder builder = TimeWindow.builder();
        List<TerminalNode> times = ctx.INTEGER();
        String startWindow = TextUtil.safeGetText(times.get(0));
        builder
                .startWindowTime(Long.valueOf(startWindow))
                .timeUnit(ChronoUnit.HOURS);
        if (times.size() == 2) {
            String endWindow = TextUtil.safeGetText(times.get(1));
            builder.endWindowTime(Long.valueOf(endWindow));
        }
        if (Objects.nonNull(ctx.time_unit())) {
            String timeUnit = ctx.time_unit().getText().toUpperCase();
            builder.timeUnit(ChronoUnit.valueOf(timeUnit));
        }
        return builder.build();
    }
}
