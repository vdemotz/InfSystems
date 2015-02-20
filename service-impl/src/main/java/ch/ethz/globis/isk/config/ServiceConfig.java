package ch.ethz.globis.isk.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"ch.ethz.globis.isk.service"})
public class ServiceConfig {
}
