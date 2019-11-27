package io.tasw;

import static io.tasw.MoneySupportConfiguration.AMOUNT_FIELD_NAME;
import static io.tasw.MoneySupportConfiguration.CURRENCY_FIELD_NAME;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.RoundedMoney;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.tasw.domain.account.AccountId;
import io.tasw.domain.business.BusinessId;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean
    Docket api() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .forCodeGeneration(true)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex("/api/.*"))
            .build();

        return registerModelSubstitutes(docket)
            .apiInfo(apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Domain REST API :: Borrower")
            .description("Domain REST Interface for Borrower Service.")
            .build();
    }
    
    Docket registerModelSubstitutes(Docket docket) {

        docket.directModelSubstitute(AccountId.class, String.class);
        docket.directModelSubstitute(BusinessId.class, String.class);

        docket.directModelSubstitute(Money.class, MonetaryAmountApiRepresentation.class);
        docket.directModelSubstitute(FastMoney.class, MonetaryAmountApiRepresentation.class);
        docket.directModelSubstitute(RoundedMoney.class, MonetaryAmountApiRepresentation.class);
        docket.directModelSubstitute(MonetaryAmount.class, MonetaryAmountApiRepresentation.class);
        
        return docket;
    }

    @ApiModel("MonetaryAmount")
    interface MonetaryAmountApiRepresentation {

        @JsonProperty
        @ApiModelProperty(name = AMOUNT_FIELD_NAME, required = true)
        BigDecimal value();

        @JsonProperty
        @ApiModelProperty(name = CURRENCY_FIELD_NAME, required = true, allowableValues = "BRL")
        String currency();

    }
}
