# tk-common-controller-advice

## About the module

Common module for implementing `@ControllerAdvice`.  
Spring Boot, Java.

## Using the module

### Using with Maven Central

Add dependencies to your _pom.xml_:

```xml
<properties>
    <tk-common-controller-advice.version>2.0.2</tk-common-controller-advice.version>
</properties>

<!-- Add module itself -->
<dependency>
    <groupId>ru.spb.tksoft</groupId>
    <artifactId>tk-common-controller-advice</artifactId>
    <version>${tk-common-controller-advice.version}</version>
</dependency>
```

See the dependencies of the tk-common-controller-advice module at Maven Central.

Extend the CommonControllerAdvice class to implement very common exception handlers:

```Java
package my.cool.app.controller.advice;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.spb.tksoft.common.controller.advice.CommonControllerAdvice;

/**
 * Handles very common exceptions. ORDER IS IMPORTANT! @see @Order annotation.
 */
@ControllerAdvice
@Order()
public class MyAppCommonControllerAdvice extends CommonControllerAdvice {

    /** Default constructor. */
    public MyAppCommonControllerAdvice() {
        super();
    }
}
```

Extend the ValidatedControllerAdvice class to implement validation exception handlers.

```Java
package my.cool.app.controller.advice;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.spb.tksoft.common.controller.advice.ValidatedControllerAdvice;

/**
 * Handles validation exceptions. ORDER IS IMPORTANT! @see @Order annotation.
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyAppValidatedControllerAdvice extends ValidatedControllerAdvice {

    /** Default constructor. */
    public MyAppValidatedControllerAdvice() {
        super();
    }
}
```

That's it - basic exception handling applied.

To implement application-specific exception handling, extend the base class:

```Java
package ru.spb.tksoft.flowforge.restapi.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.spb.tksoft.flowforge.common.exception.base.FlowForgeBaseException;
import ru.spb.tksoft.flowforge.common.exception.base.NullArgumentException;
import ru.spb.tksoft.flowforge.common.exception.base.TemplateNotFoundException;
import ru.spb.tksoft.flowforge.common.exception.base.UserNotFoundException;
import ru.spb.tksoft.flowforge.restapi.exception.TemplateSaveFailedException;
import ru.spb.tksoft.flowforge.restapi.exception.TemplateDeleteFailedException;
import ru.spb.tksoft.common.controller.advice.AbstractBaseControllerAdvice;
import ru.spb.tksoft.common.controller.dto.CommonErrorResponseDto;
import ru.spb.tksoft.utils.log.LogEx;

/**
 * Application-specific exceptions.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpecificControllerAdvice extends AbstractBaseControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(SpecificControllerAdvice.class);

    private static final int ORDER_BASE = 100;

    /** Default constructor. */
    private SpecificControllerAdvice() {
        super();
    }

    /**
     * This is an example of specific exception handling.
     * 
     * Exceptions that cause errors of type "not found"
     *
     * @param e - exception.
     * @return response entity.
     */
    @ExceptionHandler({SomethingNotFoundException.class, ThingNotFoundException.class})
    @Order(Ordered.LOWEST_PRECEDENCE - ORDER_BASE - 1)
    public ResponseEntity<CommonErrorResponseDto> handleNotFound(MyAppBaseBaseException e) {

        LogEx.error(log, LogEx.me(), LogEx.EXCEPTION_THROWN, e.getCode(), e.getMessage());
        return new ResponseEntity<>(
                new CommonErrorResponseDto(e.getCode(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    ...
}
```

Where SomethingNotFoundException.class and ThingNotFoundException.class are your domain-specific exceptions and
MyAppBaseBaseException is your base class for your domain-specific exceptions.

MyAppBaseBaseException can be like this:

```Java
package my.cool.app.exception.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Getter;
import ru.spb.tksoft.utils.log.LogEx;

/**
 * Application-specific base exception abstract class.
 */
public abstract class MyAppBaseException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(FlowForgeBaseException.class);

    @Getter
    private final int code;

    /** 
     * Base constructor.
     * 
     * @param code - error code.
     * @param message - error message.
     */
    protected MyAppBaseException(int code, String message) {

        super(message);
        this.code = code;
        LogEx.error(log, LogEx.me(), LogEx.EXCEPTION_THROWN, this.code, this);
    }
}
```

## Build the module

1. Install Java 21 + Maven.
2. Build the module:

```bash
cd tk-common-controller-advice && mvn clean install
```

## Prerequisites

Java >= 17.

**Java**:

- Install JDK or JRE version 17 or higher (development is carried out on this version; there are no obvious restrictions on the use of other versions of Java);
- Make sure that the installation is correct and that java, javac, and maven (mvn) are available in your PATH;

## Compile and install

See pom.xml. Change to the module root directory and run:

```bash
mvn clean install
mvn compile javadoc:javadoc
```

## Author

Konstantin Terskikh  
Email: <kostus.online.1974@yandex.ru>, <kostus.online@gmail.com>  
Saint-Petersburg 2025-2026
