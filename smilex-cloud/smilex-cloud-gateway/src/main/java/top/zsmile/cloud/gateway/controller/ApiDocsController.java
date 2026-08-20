package top.zsmile.cloud.gateway.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * API 文档 UI 辅助端点：返回注册中心（Nacos）中发现的服务名列表。
 * 页面 /api-docs.html 据此探测各服务的 /v3/api-docs 并聚合展示。
 * 约定：服务名 smilex-cloud-xxx 对应子服务 context-path /xxx，
 * 页面探测顺序 /{serviceId}/{xxx}/v3/api-docs -> /{serviceId}/v3/api-docs
 */
@RestController
public class ApiDocsController {

    private final DiscoveryClient discoveryClient;

    public ApiDocsController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/api-docs/services")
    public Mono<List<String>> services() {
        return Mono.justOrEmpty(discoveryClient.getServices());
    }
}
