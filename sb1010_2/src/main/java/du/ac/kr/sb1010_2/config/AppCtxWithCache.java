package du.ac.kr.sb1010_2.config;

import du.ac.kr.sb1010_2.aspect.CacheAspect;
import du.ac.kr.sb1010_2.aspect.ExeTimeAspect;
import du.ac.kr.sb1010_2.chap07.Calculator;
import du.ac.kr.sb1010_2.chap07.RecCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppCtxWithCache {
    @Bean
    public CacheAspect cacheAspect() {
        return new CacheAspect();
    }

    @Bean
    public ExeTimeAspect exeTimeAspect() {
        return new ExeTimeAspect();
    }

    @Bean
    public Calculator calculator() {
        return new RecCalculator();
    }
}
