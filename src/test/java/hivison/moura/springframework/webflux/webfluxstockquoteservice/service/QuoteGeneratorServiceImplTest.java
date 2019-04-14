package hivison.moura.springframework.webflux.webfluxstockquoteservice.service;

import hivison.moura.springframework.webflux.webfluxstockquoteservice.model.Quote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuoteGeneratorServiceImplTest {

    @Autowired
    private QuoteGeneratorService quoteGeneratorService;


    @Test
    public void fetchQuoteStream() {

        /*
         * Get quoteflux of quotes
         */

        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(1L));

        quoteFlux.take(22000).subscribe(System.out::println);
    }

    @Test
    public void fetchQuoteStreamCountDown() throws InterruptedException {

        //get quoteFlux of quotes
        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

        //subscriber lambda
        Consumer<Quote> println = System.out::println;

        //error handler
        Consumer<Throwable> errorHandler = e -> System.out.println("Some Error Occurred");

        //set Countdown latch to 1
        CountDownLatch countDownLatch = new CountDownLatch(1);

        //runnable called upon complete, count down latch
        Runnable allDone = countDownLatch::countDown;

        quoteFlux.take(30)
                .subscribe(println, errorHandler, allDone);

        countDownLatch.await();
    }
}