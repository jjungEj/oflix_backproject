package com.oflix.OFlix_back.global.entity.exception;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
