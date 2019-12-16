package com.cn.rabbitmq.cp1.exchange.topic;

import com.cn.rabbitmq.cp1.exchange.BaseQueue;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *  队列参数说明:
 *      队列名称  queue :队列名
 *      是否持久队列 durable  ：true  持久队列，该队列将在服务器重新启动后继续存在。当然了，队列的消息内容也会被写入磁盘
 *      是否独占队列 （单消费者队列）exclusive : 如果需要消费者独占队列，在队列创建的时候，设定属性exclusive为true。 只能有一个消费者
 *      是否自动删除 autoDelete ：自动删除队列，客户端连接时自动删除。 服务端会连接会不会删除？
 *
 *
 *      其他参数  arguments
 *              参数名 	                    目的
 *              x-dead-letter-exchange      死信交换器
 *              x-dead-letter-routing-key 	 死信消息的可选路由键
 *              x-expires 	                 队列在指定毫秒数后被删除
 *              x-ha-policy 	             创建HA队列
 *              x-ha-nodes 	                 HA队列的分布节点
 *              x-max-length 	             队列的最大消息数
 *              x-message-ttl 	             毫秒为单位的消息过期时间，队列级别
 *              x-max-priority 	             最大优先值为255的队列优先排序功能
 *
 */
public class TopicProduct extends BaseQueue {

    public static final  String  TOPIC_EXCHANGE="topic_exchange";  //交换器名称
    public static final  String  BAK_EXCHANGE_NAME ="bak_exchange"; //备用交换器名称

    public static final  String TOPIC_TOPIC = "topic_queue";  //队列名称

    public static void main(String[] args) throws  Exception {
        Connection connection = getConnection(); //获取连接
        Channel channel =connection.createChannel(); //创建通道
        Map<String, Object> exchangeArguments = new HashMap();
        exchangeArguments.put("alternate-exchange",BAK_EXCHANGE_NAME); //设置备用交换器 ，备用开启后，发送失败和无路由会走备用交换器
        exchangeArguments.put("x-dead-letter-exchange",DlxProcessConsumer.DLX_EXCHANGE_NAME); //死信交换器
        exchangeArguments.put("x-dead-letter-routing-key", DlxProcessConsumer.DLX_ROUTE_KEY); //死信路由键，会替换消息原来的路由键
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC,false,false,exchangeArguments); //创建交换器， 名称，类型
        //备用交换器
        channel.exchangeDeclare(BAK_EXCHANGE_NAME,BuiltinExchangeType.FANOUT, false,false,null);


        Map<String, Object> arguments = new HashMap();
//        arguments.put("x-expires",45*1000); //队列在超过一定时间没有被使用，会被从RabbitMQ中删除
//        arguments.put("x-message-ttl",20*1000);//设定了该队列所有消息的存活时间，时间单位毫秒
        //创建队列 当然了，也可以再消费者方创建队列
        channel.queueDeclare(TOPIC_TOPIC,false,false,false,arguments); //队列名称，是否持久队列,是否独占队列，是否自动删除，其他参数
        //队列绑定交换器
        channel.queueBind(TOPIC_TOPIC,TOPIC_EXCHANGE,"*",null);//队列名称，交换器名称，路由key，arguments
        channel.confirmSelect();//启动生产者确认
        // 3种确认方式 channel.waitForConfirms();普通发送方确认模式   channel.waitForConfirmsOrDie();批量确认模式   channel.addConfirmListener异步监听发送方确认模式
        channel.addConfirmListener(new ConfirmListener() { //发送结果监听
            public void handleAck(long deliveryTag, boolean multiple) throws IOException { //成功
                System.out.println("消息发送成功");
            }
            public void handleNack(long deliveryTag, boolean multiple) throws IOException { //失败
                System.out.println("消息发送失败");
            }
        });
        for (int i=0;i<10;i++) {
            String[] keys = new String[]{"topic.test","topic.mymsg","topic.mytest"};
            Random random = new Random();
            String routingKey =keys[random.nextInt(3)];
            String message="这是个测试数据"+i+"使用的路由是"+routingKey;
            //BasicProperties props消息属性
            //发送消息到交换器，路由器
            channel.basicPublish(TOPIC_EXCHANGE,routingKey,null,message.getBytes("utf-8")); //交换器名称，路由键，消息属性，消息内容
            System.out.println("发送了第"+i+"条消息。消息内容为："+message);
        }
        //不可路由到队列的消息监听
        channel.addReturnListener(new ReturnListener() {
            /**
             * int replyCode, 响应吗， 路由成没成功
             * String replyText, 回复内容
             * String exchange,
             * String routingKey,
             * AMQP.BasicProperties properties,
             * byte[] body 实际的消息体内容
             * @param replyCode
             * @param replyText
             * @param exchange
             * @param routingKey
             * @param properties
             * @param body
             * @throws IOException 跑出ioexception 异常
             */
            public void handleReturn(int replyCode, String replyText,
                                     String exchange, String routingKey,
                                     AMQP.BasicProperties properties,
                                     byte[] body) {
                String message = new String(body);
                System.out.println("RabbitMq返回的replyCode:  "+replyCode+"RabbitMq返回的replyText:  "+replyText);
                System.out.println("RabbitMq返回的exchange:  "+exchange+"RabbitMq返回的routingKey:  "+routingKey);
                System.out.println("RabbitMq返回的message:  "+message);
            }
        });
        //信道关闭监听
        channel.addShutdownListener(new ShutdownListener() {
            public void shutdownCompleted(ShutdownSignalException cause) {
                System.out.println("关闭了监听连接");
            }
        });
        //连接关闭监听   不知道为什么创建连接的时候没有连接创建监听？
        connection.addShutdownListener(new ShutdownListener() { //连接关闭加你听
            public void shutdownCompleted(ShutdownSignalException cause) {

            }
        });
        /**
         * 这个监听器在服务器内存报警或者硬盘报警的情况下，使用发送接口会产生阻塞，如果发送和接受等使用同一个连接，则会都阻塞，因此我们应该为消费者和生产者使用不同的CachingConnectionFactory，或者设置rabbitTemplate.setUsePublisherConnection(true);
         */
        connection.addBlockedListener(new BlockedListener() { //连接阻塞情况的监听器
            public void handleBlocked(String reason) throws IOException {
                System.out.println("connection blocked, reason: "+reason);
            }
            public void handleUnblocked() throws IOException { //需要同时解除内存和磁盘的报警才会收到unblock的消息
                System.out.println("==============================connection unblocked");
            }
        });
        channel.close();
        connection.close();

    }
}
