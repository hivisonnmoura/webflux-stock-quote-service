package hivison.moura.springframework.webflux.webfluxstockquoteservice.service;

import hivison.moura.springframework.webflux.webfluxstockquoteservice.model.Quote;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface QuoteGeneratorService {

    Flux<Quote> fetchQuoteStream(Duration period);
}
