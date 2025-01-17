package com.org.mfm.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Dnyaneshwar Pote", email = "dnyaneshwarpote7@gmail.com", url = "https://linkdin.in/dnyaneshwarpote7@"), description = "Envisions a comprehensive view of one's financial situation by encompassing all aspects of personal finance and enables you wiser in managing the wealth", title = "WealthWise - Api specification", version = "1.0", license = @License(name = "Licence name", url = "https://some-url.com"), termsOfService = "Terms of service"), servers = {
		@Server(description = "Development Environment", url = "http://localhost:8080"),
		@Server(description = "Production Environment", url = "https://mfm-ayejgsgubpfaatak.canadacentral-01.azurewebsites.net") }, security = {
				@SecurityRequirement(name = "bearerAuth") })
@SecurityScheme(name = "bearerAuth", description = "JWT auth description", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class AppOpenApiConfig {

}