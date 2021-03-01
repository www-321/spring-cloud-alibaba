# spring-cloud-alibaba

# nacos 配置文件名称定义规则
${prefix}-${spring.profiles.active}.${file-extension}
1，prefix 默认为 spring.application.name 的值，
   也可以通过配置项 spring.cloud.nacos.config.prefix来配置
2，spring.profiles.active 即为当前环境对应的 profile，详情可以参考 Spring Boot文档。 
  注意：当 spring.profiles.active 为空时，对应的连接符 - 也将不存在，
  dataId 的拼接格式变成 ${prefix}.${file-extension}
3，file-exetension 为配置内容的数据格式，
  可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。
  目前只支持 properties 和 yaml 类型

## feign 和 sentinel都有降级配置
1，A 接口配置feign降级，B 配置sentinel降级，A调用B，B出现异常（sentinel此时把异常请求包装成正常请求，返回给A），所以客户端最终返回的是B的降级信息
2，A 调用B时候，B的服务停止，客户端最终返回A配置的feign降级信息
## client-8081主要内容
# 限流
 * 自定义流控规则
 * resource	资源名，资源名是限流规则的作用对象
 * count	限流阈值
 * grade	限流阈值类型，QPS 模式（1）或并发线程数模式（0）	QPS 模式
 * limitApp	流控针对的调用来源	default，代表不区分调用来源
 * strategy	调用关系限流策略：直接、链路、关联	根据资源本身（直接）
 * controlBehavior	流控效果（直接拒绝/WarmUp/匀速+排队等待），不支持按调用关系限流	直接拒绝
 * clusterMode	是否集群限流	否
 
 nacos持久化配置exp：
 [{
 	"resource": "sen",
 	"count": 3,
 	"grade": 0
 }]
 
 
# 熔断降级
Sentinel 提供以下几种熔断策略：

1，慢调用比例 (SLOW_REQUEST_RATIO)：选择以慢调用比例作为阈值，需要设置允许的慢调用 RT（即最大的响应时间），
  请求的响应时间大于该值则统计为慢调用。当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目，
  并且慢调用的比例大于阈值，则接下来的熔断时长内请求会自动被熔断。经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），
  若接下来的一个请求响应时间小于设置的慢调用 RT 则结束熔断，若大于设置的慢调用 RT 则会再次被熔断。
2，异常比例 (ERROR_RATIO)：当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目，
   并且异常的比例大于阈值，则接下来的熔断时长内请求会自动被熔断。经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），
   若接下来的一个请求成功完成（没有错误）则结束熔断，否则会再次被熔断。异常比率的阈值范围是 [0.0, 1.0]，代表 0% - 100%。
3，异常数 (ERROR_COUNT)：当单位统计时长内的异常数目超过阈值之后会自动进行熔断。
  经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），若接下来的一个请求成功完成（没有错误）则结束熔断，
  否则会再次被熔断。
  
  Field	                 说明	                                                           默认值
  resource	             资源名，即规则的作用对象	
  grade	                 熔断策略，支持慢调用比例/异常比例/异常数策略	                      慢调用比例
  count	                 慢调用比例模式下为慢调用临界 RT（超出该值计为慢调用）；
                         异常比例/异常数模式下为对应的阈值	
  timeWindow	         熔断时长，单位为 s	
  minRequestAmount	     熔断触发的最小请求数，请求数小于该值时
                         即使异常比率超出阈值也不会熔断（1.7.0 引入）	                        5
  statIntervalMs	     统计时长（单位为 ms），如 60*1000 代表分钟级（1.8.0 引入）	        1000 ms
  slowRatioThreshold	 慢调用比例阈值，仅慢调用比例模式有效（1.8.0 引入）	
  
# 热点参数限流
热点即经常访问的数据。很多时候我们希望统计某个热点数据中访问频次最高的 Top K 数据，并对其访问进行限制。比如
商品 ID 为参数，统计一段时间内最常购买的商品 ID 并进行限制
用户 ID 为参数，针对一段时间内频繁访问的用户 ID 进行限制


属性	              说明	                                                            默认值
resource	          资源名，必填	
count	              限流阈值，必填	
grade	              限流模式	                                                        QPS 模式
durationInSec	      统计窗口时间长度（单位为秒），1.6.0 版本开始支持	1s
controlBehavior	      流控效果（支持快速失败和匀速排队模式），1.6.0 版本开始支持	        快速失败
maxQueueingTimeMs	  最大排队等待时长（仅在匀速排队模式生效），
                      1.6.0 版本开始支持	                                                0ms
paramIdx	          热点参数的索引，必填，对应 SphU.entry(xxx, args) 中的参数索引位置	
paramFlowItemList	  参数例外项，可以针对指定的参数值单独设置限流阈值，
                      不受前面 count 阈值的限制。仅支持基本类型和字符串类型	
clusterMode	          是否是集群参数流控规则	                                            false
clusterConfig	      集群流控相关配置	

# 网关限流 gw-flow



  