package com.cn.rabbitmq.cp1.exchange.topic;

import com.cn.rabbitmq.cp1.exchange.BaseQueue;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.cn.rabbitmq.cp1.exchange.topic.TopicProduct.BAK_EXCHANGE_NAME;

/**
 * 带死信队列的客户端
 * 队列、交换器、路由和 consummer  consummer1相同
 *
 */
public class TopicConsumer extends BaseQueue {
    public static void main(String[] args) throws Exception{
        Connection connection = getConnection();
        final Channel channel =connection.createChannel();

       /* Map<String,Object> argsExchange = new HashMap<String,Object>();
        argsExchange.put("x-dead-letter-exchange",DlxProcessConsumer.DLX_EXCHANGE_NAME); //死信交换器
        argsExchange.put("x-dead-letter-routing-key", DlxProcessConsumer.DLX_ROUTE_KEY); //死信路由键，会替换消息原来的路由键
        argsExchange.put("alternate-exchange",BAK_EXCHANGE_NAME); //设置备用交换器 ，备用开启后，发送失败和无路由会走备用交换器
        channel.exchangeDeclare(TopicProduct.TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC,false,false,argsExchange);*/
        String queueName =channel.queueDeclare().getQueue(); //消费端获取临时队列
        channel.queueBind(queueName, TopicProduct.TOPIC_EXCHANGE, "topic.test"); //临时队列绑定到指定交换器，路由。以接收消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String str = new String(body,"utf-8");
                System.out.println(str);
                channel.basicAck(envelope.getDeliveryTag(),false);
//                channel.basicNack(envelope.getDeliveryTag(),false,false); //拒绝消息  消息ID，是否批量，是否放回队列
//                channel.basicReject(envelope.getDeliveryTag(),true);
            }
        };
        channel.basicConsume(TopicProduct.TOPIC_TOPIC, consumer);
    }
}
