package dev.vality.fraudo.payment.generator;

import dev.vality.fraudo.FraudoPaymentParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RuleKeyGenerator {

    public static String generateRuleKey(FraudoPaymentParser.Fraud_ruleContext fraudRuleContext, int index) {
        if (fraudRuleContext.IDENTIFIER() != null && !fraudRuleContext.IDENTIFIER().getText().isEmpty()) {
            return fraudRuleContext.IDENTIFIER().getText();
        }
        return String.valueOf(index);
    }

}
