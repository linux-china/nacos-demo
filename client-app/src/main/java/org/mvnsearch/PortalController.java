package org.mvnsearch;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
@RestController
public class PortalController {

    private final ReactiveDiscoveryClient discoveryClient;
    private final WebClient.Builder loadBalancedWebClientBuilder;

    public PortalController(ReactiveDiscoveryClient discoveryClient,
                            WebClient.Builder webClientBuilder) {
        this.discoveryClient = discoveryClient;
        this.loadBalancedWebClientBuilder = webClientBuilder;
    }


    @GetMapping("/")
    public String index() {
        return "Hello";
    }

    @GetMapping("/instances/{name}")
    public Mono<List<ServiceInstance>> instances(@PathVariable("name") String name) {
        return discoveryClient.getInstances(name).collectList();
    }

    @GetMapping("/server-call")
    public Mono<String> callServer() {
        return loadBalancedWebClientBuilder.build()
                .get()
                .uri("http://server-app/")
                .retrieve()
                .bodyToMono(String.class);
    }
}
