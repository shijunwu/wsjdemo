package com.cn.rabbitmq.cp1.exchange.topic;

import com.cn.rabbitmq.cp1.exchange.BaseQueue;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 死信队列客户端
 */
public class DlxProcessConsumer  extends BaseQueue {

    public static String  DLX_QUEUE_NAME="dlx-exchange";
   public static String  DLX_EXCHANGE_NAME="dlx-exchange";

    public static String  DLX_ROUTE_KEY="dlx_route_key";


    public static void main(String[] args) throws  Exception{
        Connection connection = getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(DLX_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(DLX_QUEUE_NAME,false,false,false,null);
        channel.queueBind(DLX_QUEUE_NAME,DLX_EXCHANGE_NAME,DLX_ROUTE_KEY);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
            }
        };
        channel.basicConsume(DLX_QUEUE_NAME,true,consumer);

    }

}
