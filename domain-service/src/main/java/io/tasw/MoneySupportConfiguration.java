package io.tasw;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class MoneySupportConfiguration {

    public static final String AMOUNT_FIELD_NAME = "value";

    public static final String CURRENCY_FIELD_NAME = "currency";

    @Bean
    @ConditionalOnMissingBean
    public MoneyModule moneyModule() {
        return new MoneyModule()
            .withAmountFieldName(AMOUNT_FIELD_NAME)
            .withCurrencyFieldName(CURRENCY_FIELD_NAME)
            .withFastMoney();
    }

}