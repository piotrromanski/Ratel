/*
 * Copyright 2015 PayU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.payu.ratel.tests.service.timeout;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.web.WebAppConfiguration;

import com.payu.ratel.config.ServiceDiscoveryConfig;

@Configuration
@EnableAutoConfiguration
@Import(ServiceDiscoveryConfig.class)
@WebAppConfiguration
public class TimeoutServiceConfiguration {

    @Bean
    public TimeoutService testService() {
        return new TimeoutServiceImpl();
    }

    @Bean
    AnnotatedTimeoutService annotatedTestService() {
        return new AnnotatedTimeoutServiceImpl();
    }
}
