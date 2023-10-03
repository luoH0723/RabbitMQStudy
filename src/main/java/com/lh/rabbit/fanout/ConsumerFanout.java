package com.lh.rabbit.fanout;

import com.rabbitmq.client.*;

public class ConsumerFanout {

    public static void main(String[] args) throws Exception{

        String queueName="lh_queue_name";
        String exchangeName="lh_exchange_fanout_name";
        String queueName_1="lh_queue_name_fanout_1";
        String queueName_2="lh_queue_name_fanout_2";
        String queueName_3="lh_queue_name_fanout_3";
        String queueName_4="lh_queue_name_fanout_4";

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

        /**
         * 消费消息
         * 1 消费哪个队列
         * 2 消费成功之后是否需要自动应答，true：自动应答
         * 3 接收消息的回调函数
         * 4 取消消息的回调
         */
        channel.basicConsume(queueName_1,true,deliverCallback,cancelCallback);
        channel.basicConsume(queueName_2,true,deliverCallback,cancelCallback);
        channel.basicConsume(queueName_3,true,deliverCallback,cancelCallback);
        channel.basicConsume(queueName_4,true,deliverCallback,cancelCallback);

    }

}
