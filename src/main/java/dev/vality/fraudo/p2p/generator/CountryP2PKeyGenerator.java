package dev.vality.fraudo.p2p.generator;

import dev.vality.fraudo.FraudoP2PParser;
import dev.vality.fraudo.utils.TextUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CountryP2PKeyGenerator {

    public static String generate(FraudoP2PParser.Country_byContext ctx) {
        String fieldName = TextUtil.safeGetText(ctx.STRING());
        return new StringBuilder()
                .append(ctx.children.get(0))
                .append(fieldName)
                .toString();
    }

}
