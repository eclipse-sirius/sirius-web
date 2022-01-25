/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.annotations.spring.graphql;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * Indicates that an annotated class is a mutation "DataFetcher". Such class is used to modify the state of the server
 * thanks to the use of some services. A mutation data fetcher will always accept one parameter named input which should
 * be deserialized to an XxxInput POJO. In order to respond to the mutation, the data fetcher will return a payload
 * response thanks to a XxxPayload POJO.
 *
 * @author sbegaudeau
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Component
public @interface MutationDataFetcher {
    String type();

    String field();
}
