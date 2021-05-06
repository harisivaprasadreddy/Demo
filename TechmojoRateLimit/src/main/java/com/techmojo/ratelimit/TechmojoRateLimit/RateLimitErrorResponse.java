package com.techmojo.ratelimit.TechmojoRateLimit;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class RateLimitErrorResponse {
private HttpStatus status;
private String message;
private String currentTime;
public RateLimitErrorResponse() {
	super();
}
public RateLimitErrorResponse(HttpStatus tooManyRequests, String message, String currentTime) {
	super();
	this.status = tooManyRequests;
	this.message = message;
	this.currentTime = currentTime;
}
public HttpStatus getStatus() {
	return status;
}
public void setStatus(HttpStatus status) {
	this.status = status;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getCurrentTime() {
	return currentTime;
}
public void setCurrentTime(String timeStamp) {
	this.currentTime = timeStamp;
}

}
