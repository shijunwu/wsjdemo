package  com.wsj.springboot.rabbit.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

@Configuration
public class RabbitMqConfig {

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cf =  new CachingConnectionFactory();
        cf.setHost("");
        cf.setPort(5672);
        cf.setUsername("");
        cf.setPassword("");
        cf.addConnectionListener(new ConnectionListener() {
            public void onCreate(Connection connection) {

            }
        });
        return cf;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //初始化消费者数量
        factory.setConcurrentConsumers(5);
        //最大消费者数量
        factory.setMaxConcurrentConsumers(5);
        //手动确认消息
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setErrorHandler(new ErrorHandler() {
            public void handleError(Throwable t) {

            }
        });

        return factory;
    }

    @Bean
    public DirectRabbitListenerContainerFactory directRabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //每个队列的消费者数量
        factory.setConsumersPerQueue(2);
        //手动确认消息
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setErrorHandler(new ErrorHandler() {
            public void handleError(Throwable t) {

            }
        });

        return factory;
    }




}
