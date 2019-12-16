package com.cn.rabbitmq.cp1.exchange.topic;

import com.cn.rabbitmq.cp1.exchange.BaseQueue;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 不同的路由，相同队列名称，和交换器，是不会被消费
 */
public class TopicConsumer2 extends BaseQueue {
    public static void main(String[] args) throws Exception{

        Connection connection = getConnection();
        Channel channel =connection.createChannel();
        String queueName =channel.queueDeclare().getQueue();
        channel.queueBind(queueName,TopicProduct.TOPIC_EXCHANGE,"topic.mymsg");

        channel.basicConsume(queueName,true,new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String str = new String(body,"utf-8");
                System.out.println(str);
            }
        });
    }
}
