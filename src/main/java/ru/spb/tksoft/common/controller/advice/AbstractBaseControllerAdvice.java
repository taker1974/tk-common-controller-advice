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

/**
 * Base class for controller advice.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public abstract class AbstractBaseControllerAdvice {

    /** Message prefix for any exception message. */
    public static final String MESSAGE_PREFIX = "Exception caught";

    /** Default constructor. */
    protected AbstractBaseControllerAdvice() {}

    /**
     * Default message.
     * 
     * @param obj Any object whose name will be displayed in message body.
     * @return Common message.
     */
    protected String getCommonMessage(Object obj) {

        if (obj == null) {
            return MESSAGE_PREFIX + " null";
        }

        return String.format("%s %s", MESSAGE_PREFIX, obj.getClass().getSimpleName());
    }
}
