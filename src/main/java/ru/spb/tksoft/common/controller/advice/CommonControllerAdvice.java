/*
 * Copyright 2025 Konstantin Terskikh
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package ru.spb.tksoft.common.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.spb.tksoft.common.controller.dto.CommonErrorResponseDto;
import ru.spb.tksoft.utils.log.LogEx;

/**
 * Processing of very common exceptions. Order is important! @see @Order()
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ControllerAdvice
@Order()
public class CommonControllerAdvice extends AbstractBaseControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(CommonControllerAdvice.class);

    private static final int ORDER_BASE = 0;

    /** Default error code. */
    public static final int E_CODE = 160;

    /**
     * Default constructor.
     */
    public CommonControllerAdvice() {
        super();
    }

    /**
     * Default/fallback handler.
     * 
     * @param e Exception.
     * @return Error DTO.
     */
    @ExceptionHandler(Exception.class)
    @Order()
    public ResponseEntity<CommonErrorResponseDto> handleException(Exception e) {

        LogEx.error(log, LogEx.me(), LogEx.EXCEPTION_THROWN, E_CODE, e);

        String safeMessage = getSafeMessage(e);
        return new ResponseEntity<>(
                new CommonErrorResponseDto(E_CODE, safeMessage, null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Error code: RuntimeException. */
    public static final int RTE_CODE = 170;

    /**
     * Handling RuntimeException.
     * 
     * @param e Exception.
     * @return Error DTO.
     */
    @ExceptionHandler(RuntimeException.class)
    @Order(Ordered.LOWEST_PRECEDENCE - ORDER_BASE - 1)
    public ResponseEntity<CommonErrorResponseDto> handleRuntimeException(RuntimeException e) {

        LogEx.error(log, LogEx.me(), LogEx.EXCEPTION_THROWN, RTE_CODE, e);

        String safeMessage = getSafeMessage(e);
        return new ResponseEntity<>(
                new CommonErrorResponseDto(RTE_CODE, safeMessage, null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Error code: NullPointerException. */
    public static final int NPE_CODE = 171;

    /**
     * Handling NullPointerException.
     * 
     * @param e Exception.
     * @return Error DTO.
     */
    @ExceptionHandler(NullPointerException.class)
    @Order(Ordered.LOWEST_PRECEDENCE - ORDER_BASE - 2)
    public ResponseEntity<CommonErrorResponseDto> handleNpe(NullPointerException e) {

        LogEx.error(log, LogEx.me(), LogEx.EXCEPTION_THROWN, NPE_CODE, e);

        String safeMessage = getSafeMessage(e);
        return new ResponseEntity<>(
                new CommonErrorResponseDto(NPE_CODE, safeMessage, null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Error code: IllegalArgumentException. */
    public static final int IAE_CODE = 172;

    /**
     * Handling IllegalArgumentException.
     * 
     * @param e Exception.
     * @return Error DTO.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @Order(Ordered.LOWEST_PRECEDENCE - ORDER_BASE - 3)
    public ResponseEntity<CommonErrorResponseDto> handleIAE(IllegalArgumentException e) {

        LogEx.error(log, LogEx.me(), LogEx.EXCEPTION_THROWN, IAE_CODE, e);

        String safeMessage = getSafeMessage(e);
        return new ResponseEntity<>(
                new CommonErrorResponseDto(IAE_CODE, safeMessage, null),
                HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Get safe message from exception, handling null case.
     * 
     * @param e Exception.
     * @return Safe message string.
     */
    private String getSafeMessage(Exception e) {
        String message = e.getMessage();
        if (message == null || message.isBlank()) {
            return e.getClass().getSimpleName() + " occurred";
        }
        return message;
    }
}
