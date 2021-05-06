package com.techmojo.ratelimit.TechmojoRateLimit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;

@RestController
public class TechMojoRateLimitController {
	 private final Bucket bucket;
	 DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	    public TechMojoRateLimitController() {
	        Bandwidth limit = Bandwidth.classic(2, Refill.intervally(2, Duration.ofHours(1)));
	        this.bucket = Bucket4j.builder()
	            .addLimit(limit)
	            .build();
	    }
    @GetMapping(value = "/techmojo")
    public ResponseEntity rectangle() {
    	ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
    		return ResponseEntity.ok("Welcome to TechMojo");
        }
        long waitForRefill = probe.getNanosToWaitForRefill();
         waitForRefill = (long) (waitForRefill/1_000_000_000);
        waitForRefill = waitForRefill/60l;
        
        RateLimitErrorResponse errorResponse = new RateLimitErrorResponse(
        		HttpStatus.TOO_MANY_REQUESTS,"Try After "+waitForRefill+" minutes",df.format(new Date()));
        
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .header("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill))
                .body(errorResponse);
        
    }
}