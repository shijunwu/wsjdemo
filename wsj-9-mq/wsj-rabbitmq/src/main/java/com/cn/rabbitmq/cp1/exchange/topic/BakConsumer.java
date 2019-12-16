package com.cn.rabbitmq.cp1.exchange.topic;

import com.cn.rabbitmq.cp1.exchange.BaseQueue;
import com.rabbitmq.client.*;

import java.io.IOException;

public class BakConsumer extends BaseQueue {

    public static void main(String[] args) throws Exception {
        Connection connection = getConnection();
        Channel channel =connection.createChannel();
        channel.exchangeDeclare(TopicProduct.BAK_EXCHANGE_NAME,  BuiltinExchangeType.FANOUT);
        channel.queueDeclare("bak_queue",false,false,false,null);
        channel.queueBind("bak_queue",TopicProduct.BAK_EXCHANGE_NAME,"#");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println(new String(body,"utf-8"));
            }
        };
        channel.basicConsume("bak_queue",consumer);
    }
}
