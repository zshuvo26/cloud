package cloud.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
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
            cm.createCache(cloud.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(cloud.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(cloud.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(cloud.domain.Region.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.Country.class.getName() + ".cities", jcacheConfiguration);
            cm.createCache(cloud.domain.City.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.Division.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.Division.class.getName() + ".districts", jcacheConfiguration);
            cm.createCache(cloud.domain.District.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.District.class.getName() + ".upazilas", jcacheConfiguration);
            cm.createCache(cloud.domain.Upazila.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.Upazila.class.getName() + ".students", jcacheConfiguration);
            cm.createCache(cloud.domain.Upazila.class.getName() + ".institutes", jcacheConfiguration);
            cm.createCache(cloud.domain.Institute.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.Institute.class.getName() + ".departments", jcacheConfiguration);
            cm.createCache(cloud.domain.Department.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.Department.class.getName() + ".employees", jcacheConfiguration);
            cm.createCache(cloud.domain.Department.class.getName() + ".students", jcacheConfiguration);
            cm.createCache(cloud.domain.Department.class.getName() + ".sessions", jcacheConfiguration);
            cm.createCache(cloud.domain.Session.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.Session.class.getName() + ".students", jcacheConfiguration);
            cm.createCache(cloud.domain.Designation.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.Designation.class.getName() + ".employees", jcacheConfiguration);
            cm.createCache(cloud.domain.Employee.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.domain.Student.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
