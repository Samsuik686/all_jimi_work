package cn.concox.comm.util.http;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @FileName HttpCmd.java
 * @Description:
 *
 * @Date 2017年4月11日 下午3:08:42
 * @author ChengXuWei
 * @version 1.0
 */
public class HttpCmd extends HystrixCommand<Object> {

	private static final Logger logger = LoggerFactory.getLogger(HttpCmd.class);

	private RequestParam requestParam;

	private String url;

	private String callMethod;

	public HttpCmd(String group, String cmdKey, RequestParam requestParam, String callClass, String callMethod) {

		super(Setter
				// 分组
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
				// 指定命令key，可
				.andCommandKey(HystrixCommandKey.Factory.asKey(cmdKey))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(50) // 线程池核心线程数
						.withMaxQueueSize(150) // 最大排队长度
						.withQueueSizeRejectionThreshold(50))
				// 容许排队线程数量阈值
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutEnabled(true) // 否否启用超时
						.withExecutionTimeoutInMilliseconds(5000) // 超时时间，默认1秒
						.withCircuitBreakerRequestVolumeThreshold(20) // 默认20，10秒钟内至少20此请求，熔断器才发挥起作用
						.withCircuitBreakerErrorThresholdPercentage(20) // 当出错率超过20%后熔断器启动.
						.withCircuitBreakerSleepWindowInMilliseconds(60000) // 熔断器中断请求3秒后会关闭重试,如果请求仍然失败,继续打开熔断器3秒
				)
				// 隔离策略为线程时使用的线程池名称
				/*.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool"))
				.andCommandPropertiesDefaults(
						HystrixCommandProperties.Setter()
								.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE) // 隔离策略
								.withExecutionTimeoutEnabled(true) // 否否启用超时
								.withExecutionTimeoutInMilliseconds(3000) // 超时时间，默认1秒
								.withFallbackIsolationSemaphoreMaxConcurrentRequests(100) // 信号量模式下，最大并发请求限流，默认值10
								.withCircuitBreakerRequestVolumeThreshold(4) // 默认20，10秒钟内至少20此请求失败，熔断器才发挥起作用
								.withCircuitBreakerErrorThresholdPercentage(3) // 熔断器关闭到打开阈值
								.withCircuitBreakerSleepWindowInMilliseconds(3000)// 熔断器打开到关闭的时间窗长度
				)*/);
		this.url = group; // url 作为组 方法(cmdKey)作为key
		this.requestParam = requestParam; //
		this.callMethod = callMethod;

	}

	/**
	 * 异常返回结果
	 */
	@Override
	protected Object getFallback() {
		getFailedExecutionException().getMessage();
		throw new RuntimeException("服务器熔断了,请联系管理人员");
	}

	@Override
	protected Object run() throws Exception {
		Object result = null;
		switch (callMethod) {
		case "runSendGet":
			result = SendRequest.runSendGet(requestParam);
			break;
		case "runSendPost":
			result = SendRequest.runSendPost(requestParam);
			break;
		case "runSendOKHttpRequest":
			result = SendRequest.runSendOKHttpRequest(requestParam);
			break;
		case "runPostBody":
			result = SendRequest.runPostBody(requestParam);
			break;

		case "runUploadFile":
			result = SendRequest.runUploadFile(requestParam);
			break;
		default:
			throw new RuntimeException("api请求方法名称异常");
		}
		return result;
	}
}
