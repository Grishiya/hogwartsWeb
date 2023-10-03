package sky.pro.hogwartsWeb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/info")
public class InfoController {
    @Value("${server.port}")
    Integer port;

    @GetMapping("/getPort")
    public Integer getPort() {
        return port;
    }

    @GetMapping("/sum")
    public int sum() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
//                .parallel()
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        long finis = System.currentTimeMillis();
        System.out.println(finis - start);
        return sum;
    }
}
