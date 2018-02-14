package edu.ensim.biblio.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(edu.ensim.biblio.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Acte.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Article.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Chapitre.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Communication.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Conference.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Conference.class.getName() + ".notations", jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Contribution.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Memoire.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Memoire.class.getName() + ".notations", jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Notation.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.NumeroRevue.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Ouvrage.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Ouvrage.class.getName() + ".notations", jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Ouvrage.class.getName() + ".chapitres", jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Publication.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.PublicationGouvernementale.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.PublicationGouvernementale.class.getName() + ".notations", jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Rapport.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Rapport.class.getName() + ".notations", jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Revue.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ensim.biblio.domain.Revue.class.getName() + ".notations", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
