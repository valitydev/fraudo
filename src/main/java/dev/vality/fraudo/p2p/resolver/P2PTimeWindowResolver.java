package dev.vality.fraudo.p2p.resolver;

import dev.vality.fraudo.model.TimeWindow;
import dev.vality.fraudo.resolver.TimeWindowResolver;
import dev.vality.fraudo.utils.TextUtil;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

import static dev.vality.fraudo.FraudoP2PParser.Time_windowContext;

public class P2PTimeWindowResolver implements TimeWindowResolver<Time_windowContext> {

    @Override
    public TimeWindow resolve(Time_windowContext ctx) {
        TimeWindow.TimeWindowBuilder builder = TimeWindow.builder();
        List<TerminalNode> times = ctx.INTEGER();
        String startWindow = TextUtil.safeGetText(times.get(0));
        if (times.size() == 2) {
            String endWindow = TextUtil.safeGetText(times.get(1));
            builder.endWindowTime(Long.valueOf(endWindow));
        }
        return builder
                .startWindowTime(Long.valueOf(startWindow))
                .build();
    }

}
