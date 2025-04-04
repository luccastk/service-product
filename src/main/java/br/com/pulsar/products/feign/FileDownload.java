package br.com.pulsar.products.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@FeignClient(name = "file-service", url = "http://localhost:8080/file-service")
public interface FileDownload {

    @GetMapping("/download/{request_id}")
    ResponseEntity<Resource> downloadFile(@PathVariable("request_id") String request_id);
}
