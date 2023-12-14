package com.samply.feedbackhub;

import org.springframework.http.HttpStatus;

public interface ProxyResultListener {
    void onResult(HttpStatus status);
}
