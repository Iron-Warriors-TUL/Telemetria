package com.julian.iwtelemetria;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@SpringBootApplication
public class IronWarriosTelemetriaApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(IronWarriosTelemetriaApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	@Bean
	public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageProducer inbound() {
		// Placeholder for the actual MQTT connection
		MqttPahoMessageDrivenChannelAdapter adapter =
				new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "testClient",
						"telemetria/+/+");
		adapter.setOutputChannel(mqttInputChannel());
		adapter.setQos(1);
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		return adapter;
	}

	@Bean
	public MessageHandler handler() {
		return message -> {
			System.out.println("TEST");
		};
	}

}
