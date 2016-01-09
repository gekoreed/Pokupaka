package com.selfach;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * By gekoreed on 9/12/15.
 */
@EnableCaching
@Import(DbConfig.class)
@ComponentScan("com.selfach")
@Configuration
@EnableScheduling
@PropertySource("file:conf/app.properties")
public class Config {
    @Value("${server.port}")
    int port;

    @Value("${server.workers}")
    private int workers;

    @Value("${server.bosses}")
    private int bosses;

    @Bean
    @Qualifier("port8083")
    public ServerBootstrap getAPIBootstrap(MsgResolver channelHandler){
         return getBootstrap(channelHandler);
    }

    @Bean
    @Qualifier("port80")
    public ServerBootstrap getHttpBootstrap(MsgResolver channelHandler){
        return getBootstrap(channelHandler);
    }

    public ServerBootstrap getBootstrap(SimpleChannelInboundHandler channelHandler) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(getBossCount(), getWorkersCount())
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder", new HttpServerCodec());
                        pipeline.addLast("aggregator", new HttpObjectAggregator(2097152));
                        pipeline.addLast("handler", channelHandler);
                    }
                });

        return serverBootstrap;

    }

    private NioEventLoopGroup getBossCount() {
        return new NioEventLoopGroup(bosses);
    }

    private NioEventLoopGroup getWorkersCount() {
        return new NioEventLoopGroup(workers);
    }


    @Bean(name = "tcpAppAddress")
    public InetSocketAddress tcpAppPort() {
        return new InetSocketAddress(port);
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    CacheManager getCacheManager(){
        return new ConcurrentMapCacheManager();
    }

    @Component
    public static class HttpAppServerBean {
        @Autowired
        @Qualifier("port8083")
        private ServerBootstrap port8083;

        @Autowired
        @Qualifier("port80")
        private ServerBootstrap port80;

        @Autowired
        @Qualifier("tcpAppAddress")
        private InetSocketAddress tcpPort;

        private Channel serverChannel;


        public void start() throws Exception {
            System.out.println("Starting app server at " + tcpPort);
            port8083.bind(tcpPort).sync();
            port80.bind(8080).sync();
        }


        @SuppressWarnings("unused")
        public void stop() {
            serverChannel.close();
        }
    }
}
