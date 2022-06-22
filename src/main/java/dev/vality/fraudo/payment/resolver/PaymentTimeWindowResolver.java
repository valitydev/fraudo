package dev.vality.fraudo.payment.resolver;

import dev.vality.fraudo.FraudoPaymentParser;
import dev.vality.fraudo.model.TimeWindow;
import dev.vality.fraudo.resolver.TimeWindowResolver;
import dev.vality.fraudo.utils.TextUtil;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static dev.vality.fraudo.constant.TimeUnit.*;


public class PaymentTimeWindowResolver implements TimeWindowResolver<FraudoPaymentParser.Time_windowContext> {

    @Override
    public TimeWindow resolve(FraudoPaymentParser.Time_windowContext ctx) {
        TimeWindow timeWindow = new TimeWindow();
        List<TerminalNode> times = ctx.INTEGER();
        String start = TextUtil.safeGetText(times.get(0));
        timeWindow.setStart(Integer.parseInt(start));
        timeWindow.setTimeUnit(ChronoUnit.HOURS);
        if (times.size() == 2) {
            String end = TextUtil.safeGetText(times.get(1));
            timeWindow.setEnd(Integer.parseInt(end));
        }
        if (Objects.nonNull(ctx.time_unit())) {
            String timeUnit = ctx.time_unit().getText();
            timeWindow.setTimeUnit(resolveTimeUnit(timeUnit));
            if (CAL_MONTHS.equals(timeUnit)) {
                calculateTimeWindow(timeWindow);
            }
        }
        return timeWindow;
    }

    private ChronoUnit resolveTimeUnit(String timeUnit) {
        return switch (timeUnit) {
            case MINUTES -> ChronoUnit.MINUTES;
            case DAYS, CAL_MONTHS -> ChronoUnit.DAYS;
            default -> ChronoUnit.HOURS;
        };
    }

    private void calculateTimeWindow(TimeWindow timeWindow) {
        int start = calculateStart(timeWindow.getStart());
        timeWindow.setStart(start);
        int end = calculateEnd(timeWindow.getEnd());
        timeWindow.setEnd(end);
    }

    private int calculateStart(int start) {
        LocalDate now = LocalDate.now();
        int startInDays = now.getDayOfMonth();
        for (int i = 1; i <= start - 1; i++) {
            startInDays += now.minusMonths(i).lengthOfMonth();
        }
        return startInDays;
    }

    private int calculateEnd(int end) {
        LocalDate now = LocalDate.now();
        int endInDays = 0;
        for (int i = 1; i <= end; i++) {
            endInDays += now.minusMonths(i).lengthOfMonth();
        }
        return endInDays;
    }

}
