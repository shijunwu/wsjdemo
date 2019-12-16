package com.cn.rabbitmq.cp1.exchange.fanout;

import com.cn.rabbitmq.cp1.exchange.BaseQueue;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.Random;

import static com.cn.rabbitmq.cp1.exchange.fanout.FanoutConsumer.FANOUT_EXCHANGE;
import static com.cn.rabbitmq.cp1.exchange.fanout.FanoutConsumer.FANOUT_QUEUE;

public class FanoutProduct extends BaseQueue {

    public static void main(String[] args) throws  Exception {
        Connection connection = getConnection(); //获取连接
        Channel channel = connection.createChannel(); //创建通道
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(FANOUT_QUEUE,false,false,false,null);
        channel.queueBind(FANOUT_QUEUE,FANOUT_EXCHANGE,FANOUT_QUEUE);
        for (int i = 0; i < 10; i++) {
            String[] keys = new String[]{"topic.test", "topic.mymsg", "topic.mytest"};
            Random random = new Random();
            String routingKey = keys[random.nextInt(3)];
            String message = "这是个测试数据" + i + "使用的路由是" + routingKey;
            //BasicProperties props消息属性
            //发送消息到交换器，路由器
            channel.basicPublish(FANOUT_EXCHANGE, "*", null, message.getBytes("utf-8")); //交换器名称，路由键，消息属性，消息内容
        }
    }
}
