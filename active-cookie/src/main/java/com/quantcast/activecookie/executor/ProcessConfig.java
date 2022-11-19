package com.quantcast.activecookie.executor;

import com.quantcast.activecookie.filter.CookieFilter;
import com.quantcast.activecookie.filter.CookieFilterImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfig {

  @Bean
  public CommandLineRunner commandLineRunner(
      ApplicationContext applicationContext, ProcessExecutor processExecutor) {
    return new ProcessRunner(applicationContext, processExecutor);
  }

  @Bean
  public CookieFilter cookieFilter() {
    return new CookieFilterImpl();
  }

  @Bean
  public ProcessExecutor processExecutor(CookieFilter cookieFilter) {
    return new ProcessExecutorImpl(cookieFilter);
  }
}
