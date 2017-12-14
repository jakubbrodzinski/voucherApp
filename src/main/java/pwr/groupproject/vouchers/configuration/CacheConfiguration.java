package pwr.groupproject.vouchers.configuration;

import com.github.benmanes.caffeine.cache.CaffeineSpec;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager(){
        CaffeineCacheManager caffeineCacheManager=new CaffeineCacheManager();
        caffeineCacheManager.setCaffeineSpec(caffeineSpec());
        return  caffeineCacheManager;
    }

    @Bean
    public CaffeineSpec caffeineSpec(){
        CaffeineSpec caffeineSpec=CaffeineSpec.parse("expireAfterAccess=5m");
        return caffeineSpec;
    }
}
