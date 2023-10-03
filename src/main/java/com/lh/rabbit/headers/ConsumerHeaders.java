package com.lh.rabbit.headers;

import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;

public class ConsumerHeaders {

    public static void main(String[] args) throws Exception{

        String exchangeName="lh_exchange_headers_name";
        String queueName_1="lh_queue_name_headers_1";

        //创建一个链接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //配制服务地址
        factory.setHost("139.199.181.244");
        //账号
        factory.setUsername("admin");
        //密码
        factory.setPassword("123456");
        //端口号
        factory.setPort(5672);
        //创建链接
        Connection connection = factory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();


        //接收消息的回调函数
        DeliverCallback deliverCallback=(cousumerTage,message)->{
            System.out.println("接收到消息"+new String(message.getBody()));
        };


        //取消消息的回调函数
        CancelCallback cancelCallback=cousumerTage->{
            System.out.println("消费消息被中断");
        };

        Map<String,Object> headerMap=new HashMap<>();
        headerMap.put("x-match","any");
        headerMap.put("name","luohao");
        headerMap.put("sex","男");

        /**
         * 将我们的交换机和队列绑定
         * 1 队列名称
         * 2 交换机名称
         * 3 路由键，在我们直连模式下，可以为我们的队列名称
         */
        channel.queueBind(queueName_1,exchangeName,"",headerMap);

        /**
         * 消费消息
         * 1 消费哪个队列
         * 2 消费成功之后是否需要自动应答，true：自动应答
         * 3 接收消息的回调函数
         * 4 取消消息的回调
         */
        channel.basicConsume(queueName_1,true,deliverCallback,cancelCallback);

    }

}
