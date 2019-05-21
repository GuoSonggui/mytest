package com.tensquare.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 接收用户注册验证码信息监听类
 */
@Component
@RabbitListener(queues = "viking")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.temp_code}")
    private String tempCode;  //短信模块

    @Value("${aliyun.sms.sign_name}")
    private String signName;//短信签名

    /**
     * 处理方法
     */
    @RabbitHandler
    public void handlerMessage(Map data, Channel channel, Message message) {
        String mobile = (String) data.get("mobile");
        String code = (String) data.get("code");

        System.out.println("手机号码：" + mobile);
        System.out.println("验证码：" + code);

        //使用阿里短信发送验证码短信
        // 关键的两步：1）申请短信签名  2）申请短信模板
        /**
         * 参数一: 接收短信的手机号码
         * 参数二：短信模板ID
         * 参数三：短信签名
         * 参数四：在短信模块里面的参数内容，格式是json
         */
        try {
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(mobile, tempCode, signName, "{\"code\":\"" + code + "\"}");
            //获取阿里短信的返回码
            String respCode = sendSmsResponse.getCode();

            if ("OK".equals(respCode)) {
                System.out.println("短信发送成功");

                //给MQ发送ACK确认请求
                //手动发送RabbitMQ的确认消息，一旦发送确认消息，MQ的消息就会被删除
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("短信发送失败：" + respCode);
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
