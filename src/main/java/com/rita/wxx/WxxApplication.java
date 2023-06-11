package com.rita.wxx;

import com.rita.wxx.aop.springaop.InnerInvoke;
import com.rita.wxx.aop.springaop.MyServiceA;
import com.rita.wxx.aop.springaop.UsageTracked;
import org.springframework.aop.framework.Advised;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class WxxApplication {

	public static void main(String[] args) {

		SpringApplication.run(WxxApplication.class, args);

		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();) {
			ctx.register(WxxApplication.class);
			ctx.refresh();
			MyServiceA myServiceA = ctx.getBean(MyServiceA.class);

			String myServiceAStr = myServiceA.toString();
			System.out.println(myServiceAStr);

			Advised advised = (Advised) myServiceA;
			System.out.println("isFrozen: " + advised.isFrozen());

			myServiceA.sayHello("lisp");

			UsageTracked usageTracked = ctx.getBean("myServiceA", UsageTracked.class);
			System.out.println("usage tracked: " + usageTracked.get());

			System.out.println(ctx.getBean(WxxApplication.class));

			// 内部转调，不会触发切面
			InnerInvoke innerInvoke = ctx.getBean(InnerInvoke.class);
			innerInvoke.sayHello("innerInvoke");
		}
	}

}
