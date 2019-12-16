package com.cn.rabbitmq.cp1.exchange.fanout;

import com.cn.rabbitmq.cp1.exchange.BaseQueue;
import com.rabbitmq.client.*;

import java.io.IOException;

public class FanoutConsumer extends BaseQueue {

    public static String FANOUT_EXCHANGE ="fanout_exchange";
    public static String FANOUT_QUEUE = "fanout_queue";
    public static void main(String[] args) throws  Exception{
        Connection connection = getConnection();
        final Channel channel =connection.createChannel();
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(FANOUT_QUEUE,false,false,false,null);
        channel.queueBind(FANOUT_QUEUE,FANOUT_EXCHANGE,FANOUT_QUEUE);
        channel.basicConsume(FANOUT_QUEUE, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println(new String(body,"utf-8"));
            }
        });
    }
}
