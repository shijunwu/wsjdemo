package com.cn.rabbitmq.cp1.exchange.topic;

import com.cn.rabbitmq.cp1.exchange.BaseQueue;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 队列、交换器、路由和 consummer  consummer1相同
 * 测试topic下不确认情况会这样
 *
 */
public class TopicConsumer0 extends BaseQueue {
    public static void main(String[] args) throws Exception{
        Connection connection = getConnection(); //创建连接
        Channel channel =connection.createChannel(); //创建信道
//        channel.exchangeDeclare(TopicProduct.TOPIC_EXCHANGE,  BuiltinExchangeType.TOPIC,false,false,null); //创建交换器

        String queueName ="queue1";
        channel.queueDeclare(queueName,false,false,false,null);
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
        channel.basicConsume(queueName,false,consumer); //队列名称，是否自动确认，消费者
    }
}
