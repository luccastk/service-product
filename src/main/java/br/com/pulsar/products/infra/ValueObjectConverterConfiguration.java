package br.com.pulsar.products.infra;

import br.com.pulsar.products.common.utils.*;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StockId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ValueObjectConverterConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToStoreIdConverter());
        registry.addConverter(new StringToBatchIdConverter());
        registry.addConverter(new StringToProductsIdConverter());
    }
}
