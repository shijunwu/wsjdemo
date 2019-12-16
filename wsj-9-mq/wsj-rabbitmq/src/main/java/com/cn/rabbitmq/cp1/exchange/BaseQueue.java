package com.cn.rabbitmq.cp1.exchange;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.ErrorOnWriteListener;

import java.io.IOException;

public class BaseQueue {

    public static Connection getConnection()  throws  Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.23.0.15");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setErrorOnWriteListener(new ErrorOnWriteListener() {
            public void handle(Connection connection, IOException exception) throws IOException {
            }
        });
        return factory.newConnection();
    }

}
