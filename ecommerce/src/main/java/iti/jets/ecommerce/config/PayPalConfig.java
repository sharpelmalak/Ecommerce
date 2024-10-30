package iti.jets.ecommerce.config;


import com.paypal.sdk.Environment;
import com.paypal.sdk.PaypalServerSDKClient;
import com.paypal.sdk.authentication.ClientCredentialsAuthModel;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class PayPalConfig {

    @Value("${paypal.client.id}")
    private String PAYPAL_CLIENT_ID;

    @Value("${paypal.client.secret}")
    private String PAYPAL_CLIENT_SECRET;


    @Bean
    public PaypalServerSDKClient paypalClient() {
        return new PaypalServerSDKClient.Builder()
                .loggingConfig(builder -> builder
                        .level(Level.DEBUG)
                        .requestConfig(logConfigBuilder -> logConfigBuilder.body(true))
                        .responseConfig(logConfigBuilder -> logConfigBuilder.headers(true)))
                .httpClientConfig(configBuilder -> configBuilder
                        .timeout(0))
                .environment(Environment.SANDBOX)
                .clientCredentialsAuth(new ClientCredentialsAuthModel.Builder(
                        PAYPAL_CLIENT_ID,
                        PAYPAL_CLIENT_SECRET)
                        .build())
                .build();
    }


}

