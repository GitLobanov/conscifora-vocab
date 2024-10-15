package com.conscifora.token.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CVToken implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;
    private String tokenValue;
    private long createdAt;
    private long expiresIn;

    private long monthlyRequests;
    private long totalRequests;

    public void incrementRequests() {
        monthlyRequests++;
        totalRequests++;
    }


}
