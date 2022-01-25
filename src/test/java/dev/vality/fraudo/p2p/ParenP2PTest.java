package dev.vality.fraudo.p2p;

import dev.vality.fraudo.FraudoP2PParser;
import dev.vality.fraudo.constant.ResultStatus;
import dev.vality.fraudo.model.ResultModel;
import dev.vality.fraudo.utils.ResultUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class ParenP2PTest extends AbstractP2PTest {

    public static final String FIRST_RULE_INDEX = "0";

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void countCardTokenTest() throws Exception {
        InputStream resourceAsStream = ParenP2PTest.class.getResourceAsStream("/rules/p2p/paren.frd");
        when(countAggregator.count(anyObject(), any(), any(), any())).thenReturn(10);
        FraudoP2PParser.ParseContext parseContext = getParseContext(resourceAsStream);
        ResultModel result = invokeParse(parseContext);

        assertEquals(ResultStatus.DECLINE, ResultUtils.findFirstNotNotifyStatus(result).get().getResultStatus());
        verify(countAggregator, times(2)).count(anyObject(), any(), any(), any());
    }

}
