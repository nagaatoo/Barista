package ru.numbDev.barista.conf;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer",
        type = SecuritySchemeType.HTTP,
        scheme = "Bearer"
)
@OpenAPIDefinition(
        info = @Info(title = "Бариста", version = "v1"),
        security = @SecurityRequirement(name = "Bearer")
)
public class OpenAPIConfiguration {
}
