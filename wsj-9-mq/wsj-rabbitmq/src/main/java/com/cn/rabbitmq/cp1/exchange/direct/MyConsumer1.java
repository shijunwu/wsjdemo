package com.cn.rabbitmq.cp1.exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MyConsumer1 {
    public static void main(String[] args) throws Exception {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("127.0.0.1");
        cf.setUsername("guest");
        cf.setPassword("guest");
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(DirectProducter.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        String queueName="test_queue1";
        channel.queueDeclare(queueName,false,false,
                false,null);

    }
}
