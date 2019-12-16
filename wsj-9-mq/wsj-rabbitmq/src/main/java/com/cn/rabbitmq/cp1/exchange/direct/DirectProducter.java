package com.cn.rabbitmq.cp1.exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DirectProducter {

    public   static String  DIRECT_EXCHANGE="directExchange";
    public static void main(String[] args) throws  Exception {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("127.0.0.1");
        cf.setUsername("guest");
        cf.setPassword("guest");
        Connection con= cf.newConnection();
        Channel channel =con.createChannel();
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        String queueName="test_queue1";
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,DIRECT_EXCHANGE,"test");
        String[] severities={"error","info","warning"};
        for (int i=0 ;i<5;i++) {
            String severity = severities[i%3];//每一次发送一条不同严重性的日志
            String message = "Hello World_"+(i+1);
            channel.basicPublish(DIRECT_EXCHANGE,severity,null,message.getBytes());
            System.out.println(" [x] Sent '" + severity +"':'"
                    + message + "'");
        }
        channel.close();
        con.close();
    }
}
