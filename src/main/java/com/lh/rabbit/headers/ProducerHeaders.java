package com.lh.rabbit.headers;

import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;

public class ProducerHeaders {

    public static void main(String[] args) throws Exception {


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

        /**
         * 创建交换机
         * 1 交换机名称
         * 2 交换机类型，direct，topic，fanout和headers
         * 3 指定交换机石佛需要持久化，如果设置为true，那么减缓及的元数据需要持久化
         * 4 指定交换机在没有队列绑定时，是否需要删除，设置为false表示不删除
         * 5 Map<String,Object>类型，用来指定我们交换机其他的一些结构化参数，我们这里直接设置成null
         */
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.HEADERS,true,false,null);


        /**
         * 生成一个队列
         * 1 队列名称
         * 2 队列是否需要持久化，但是要注意，这里的持久化只是队列名称等这些元数据的持久化，不是队列中消息的持久化
         * 3 表示队列是不是私有的，如果是私有的，只有创建他的应用程序才能消费消息
         * 4 队列在没有消费者订阅的情况下是否删除
         * 5 队列的一些结构化信息，比如声明死信队列，磁盘队列会用到
         */
        channel.queueDeclare(queueName_1,true,false,false,null);

        Map<String,Object> headerMap=new HashMap<>();

        headerMap.put("name","luohaoXX");
        headerMap.put("sex","男");

        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder().headers(headerMap);


        //发送消息
        String message="hello rabbitmq";
        String key_1_message="key1 topic message";
//        String key_3_message="key3 message";
//        String key_4_message="key4 message";

        /**
         * 发送消息
         * 1 发送到哪个交换机
         * 2 发送到哪个队列
         * 3 其他参数信息
         * 4 发送消息的消息体
         */
        channel.basicPublish(exchangeName,"",properties.build(),"all massage".getBytes());

        channel.close();
        connection.close();

        System.out.println("发送消息成功");

    }

}
