package com.payu.ratel.config.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.Environment;

import com.payu.ratel.client.RemoteAutowireCandidateResolver;
import com.payu.ratel.register.ServiceRegisterPostProcessor;

public class RatelContextApplier implements BeanFactoryPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatelContextApplier.class);

    public static final String SERVICE_DISCOVERY_ENABLED = "serviceDiscovery.enabled";

    private RegistryBeanProviderFactory registryBeanProviderFactory;
    private ServiceRegisterPostProcessorFactory serviceRegisterPostProcessorFactory;

    public RatelContextApplier(RegistryBeanProviderFactory registryBeanProviderFactory, ServiceRegisterPostProcessorFactory serviceRegisterPostProcessorFactory) {
        this.registryBeanProviderFactory = registryBeanProviderFactory;
        this.serviceRegisterPostProcessorFactory = serviceRegisterPostProcessorFactory;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        final Environment environment = beanFactory.getBean(Environment.class);

        if ("false".equals(environment.getProperty(SERVICE_DISCOVERY_ENABLED))) {
            LOGGER.info("Ratel is disabled");
            return;
        }

        LOGGER.info("Ratel is enabled");

        final RegistryBeanProvider registryBeanProvider = registryBeanProviderFactory.create(beanFactory);
        final String registryBeanName = registryBeanProvider.getClass().getName();
        beanFactory.registerSingleton(registryBeanName, registryBeanProvider);
        beanFactory.initializeBean(registryBeanProvider, registryBeanName);


        final ServiceRegisterPostProcessor serviceRegisterPostProcessor = serviceRegisterPostProcessorFactory.create(beanFactory,
                registryBeanProvider.getRegisterStrategy());
        beanFactory.registerSingleton(serviceRegisterPostProcessor.getClass().getName(), serviceRegisterPostProcessor);


        final RemoteAutowireCandidateResolver autowireCandidateResolver = new RemoteAutowireCandidateResolver(
                registryBeanProvider.getFetchStrategy(), registryBeanProvider.getClientProxyGenerator());
        ((DefaultListableBeanFactory) beanFactory).setAutowireCandidateResolver(autowireCandidateResolver);

    }

}
