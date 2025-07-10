package com.docdock.group09.notification_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationFilterRequest {
    private String userId;
    private String type;
    private String channel;
    private Integer limit = 8;
    private Integer offset = 0;
}
