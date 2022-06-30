package com.itech.guide.global.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class TraceStatusVO {
    private TraceId traceId;
    private Long startTimesMs;
    private String message;
}
