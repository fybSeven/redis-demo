package springboot.redis.redisdemo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: fengyibo
 * @Date: 2019/1/14 10:56
 * @Description: 配合jedisPool使用  redis配置类
 */
@Configuration
public class RedisConfig {


/*    @Value("${redis.hostName}")
    private String hostName;

    @Value("${redis.port}")
    private Integer port;*/

    @Value("${redis.cluster}")
    private String cluster;

    @Value("${redis.timeout}")
    private Integer timeout;

    @Value("${redis.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${redis.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;

    @Value("${redis.testOnBorrow}")
    private Boolean testOnBorrow;

    @Value("${redis.testWhileIdle}")
    private Boolean testWhileIdle;

    /**
     * jedis连接池配置设置
     * @return
     */
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //设置最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        //设置最大连接数
        jedisPoolConfig.setMaxTotal(maxTotal);
        //最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        //逐出连接的最小空闲时间
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(true);
        //在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;
    }

    /**
     * 配置连接工厂 hostname port等
     * @param jedisPoolConfig
     * @return
     */
    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(@Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig){
/*        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setPort(port);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);*/



        List<String> nodeList = Arrays.asList(cluster.split(","));
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(nodeList);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
        return jedisConnectionFactory;
    }

    /**
     * 配置redisTemplate 设置序列化方式
     * @param redisConnectionFactory
     * @return
     */
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> jedisTemplate(@Qualifier("jedisConnectionFactory") RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> jedisTemplate = new RedisTemplate<>();
        jedisTemplate.setConnectionFactory(redisConnectionFactory);
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //设置key的序列化方式为stringRedisSerializer
        jedisTemplate.setKeySerializer(stringRedisSerializer);
        jedisTemplate.setHashKeySerializer(stringRedisSerializer);
        //设置value的序列化方式为json
        jedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        jedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return jedisTemplate;
    }

}
