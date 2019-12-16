package com.cn.rabbitmq.cp1.exchange.topic;

import com.cn.rabbitmq.cp1.exchange.BaseQueue;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 队列、交换器、路由和 consummer  consummer1相同   经过测试，一条消息会在 consumer、consumer0、consumer1上都消费 死信和不确认都不影响别的消费者
 * 客户端自动确认
 *
 */
public class TopicConsumer1 extends BaseQueue {
    public static void main(String[] args) throws Exception{
        Connection connection = getConnection(); //创建连接
        Channel channel =connection.createChannel(); //创建信道
        String queueName ="queue1";
        System.out.println(queueName);
        channel.queueBind(queueName,TopicProduct.TOPIC_EXCHANGE,"topic.test");//队列绑定到交换器并指定路由
        //创建队列消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String str = new String(body, "utf-8");
                System.out.println(str);
            }
        };
        channel.basicConsume(queueName,true,consumer); //队列名称，是否自动确认，消费者
    }
}
