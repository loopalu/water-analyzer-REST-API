package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Experiment.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Experiment.class.getName() + ".experimentAnalyses", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Experiment.class.getName() + ".experimentResults", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Analyte.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Analyte.class.getName() + ".experimentPeaks", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Analyte.class.getName() + ".analytesOfInterests", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ExperimentAnalysis.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ExperimentAnalysis.class.getName() + ".experimentPeaks", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Method.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Method.class.getName() + ".experiments", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Method.class.getName() + ".bGECompositions", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Method.class.getName() + ".analytesOfInterests", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.BGEComposition.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.CapillaryType.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.CapillaryType.class.getName() + ".methods", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ExperimentPeaks.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.MatrixList.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.MatrixList.class.getName() + ".methods", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ExperimentResults.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.AnalytesOfInterest.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
