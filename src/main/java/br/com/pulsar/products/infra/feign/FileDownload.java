package br.com.pulsar.products.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "service-file")
public interface FileDownload {

    @GetMapping("/service-file/v1/download/{request_id}")
    ResponseEntity<byte[]> downloadFile(@PathVariable("request_id") String request_id);
}
