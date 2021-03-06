package dev.vality.fraudo.utils.key.generator;

import dev.vality.fraudo.utils.TextUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.function.Function;

import static dev.vality.fraudo.FraudoPaymentParser.Group_byContext;
import static dev.vality.fraudo.FraudoPaymentParser.Time_windowContext;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonKeyGenerator {

    static <T> String generateKeyGroupedFunction(TerminalNode string,
                                                 ParseTree parseTree,
                                                 Time_windowContext timeWindowContext,
                                                 Group_byContext groupByContext,
                                                 Function<String, T> resolve) {
        String countTarget = TextUtil.safeGetText(string);
        return new StringBuilder()
                .append(parseTree)
                .append(countTarget)
                .append(resolve.apply(countTarget))
                .append(timeWindowContext != null ? timeWindowContext.children : "")
                .append(groupByContext != null ? groupByContext.string_list().children : "")
                .toString();
    }

    static <T> String generateKeyGroupedTwoFieldFunction(TerminalNode firstField,
                                                         TerminalNode secondField,
                                                         ParseTree parseTree,
                                                         Time_windowContext timeWindowContext,
                                                         Group_byContext groupByContext,
                                                         Function<String, T> resolve) {
        String target = TextUtil.safeGetText(firstField);
        String errorCode = TextUtil.safeGetText(secondField);
        return new StringBuilder()
                .append(parseTree)
                .append(target)
                .append(errorCode)
                .append(resolve.apply(target))
                .append(timeWindowContext != null ? timeWindowContext.children : "")
                .append(groupByContext != null ? groupByContext.string_list().children : "")
                .toString();
    }

}
