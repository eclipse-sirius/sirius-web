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
package org.eclipse.sirius.web.interpreter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

/**
 * Tests of the ExpressionConverter used to validate that it can properly convert non AQL expressions into AQL
 * expressions.
 *
 * @author sbegaudeau
 */
public class ResultTestCases {

    @Test(expected = NullPointerException.class)
    public void testNullResult() {
        new Result(null, Status.OK);
    }

    @Test
    public void testEmptyResult() {
        Result result = new Result(Optional.empty(), Status.OK);

        assertThat(result.asString()).isNotPresent();
        assertThat(result.asInt()).isNotPresent();
        assertThat(result.asBoolean()).isNotPresent();
        assertThat(result.asObject()).isNotPresent();
        assertThat(result.asObjects()).isNotPresent();
    }

    @Test
    public void testStringResult() {
        String stringValue = "Luke Cage"; //$NON-NLS-1$
        Result result = new Result(Optional.of(stringValue), Status.OK);

        assertThat(result.asString()).isPresent();
        assertThat(result.asString()).hasValue(stringValue);
        assertThat(result.asInt()).isNotPresent();
        assertThat(result.asBoolean()).hasValue(true);
        assertThat(result.asObject()).isPresent();
        assertThat(result.asObject().get()).isEqualTo(stringValue);

        assertThat(result.asObjects()).isPresent();
        assertThat(result.asObjects().get()).hasSize(1);
        assertThat(result.asObjects().get().get(0)).asString().isEqualTo(stringValue);
    }

    @Test
    public void testIntResult() {
        String stringValue = "42"; //$NON-NLS-1$
        Result result = new Result(Optional.of(stringValue), Status.OK);

        assertThat(result.asString()).isPresent();
        assertThat(result.asString()).hasValue(stringValue);
        assertThat(result.asInt()).isPresent();
        assertThat(result.asInt()).hasValue(42);
        assertThat(result.asBoolean()).hasValue(true);
        assertThat(result.asObject()).isPresent();
        assertThat(result.asObject().get()).isEqualTo(stringValue);

        assertThat(result.asObjects()).isPresent();
        assertThat(result.asObjects().get()).hasSize(1);
        assertThat(result.asObjects().get().get(0)).asString().isEqualTo(stringValue);
    }

    @Test
    public void testObjectResult() {
        Object objectValue = new Object();
        Result result = new Result(Optional.of(objectValue), Status.OK);

        assertThat(result.asString()).isPresent();
        assertThat(result.asInt()).isNotPresent();
        assertThat(result.asBoolean()).hasValue(true);
        assertThat(result.asObject()).isPresent();
        assertThat(result.asObject().get()).isEqualTo(objectValue);

        assertThat(result.asObjects()).isPresent();
        assertThat(result.asObjects().get()).hasSize(1);
        assertThat(result.asObjects().get().get(0)).isEqualTo(objectValue);
    }

    @Test
    public void testListObjectResult() {
        Object object = new Object();
        List<Object> listValue = List.of(object);
        Result result = new Result(Optional.of(listValue), Status.OK);

        assertThat(result.asString()).isPresent();
        assertThat(result.asInt()).isNotPresent();
        assertThat(result.asBoolean()).hasValue(true);
        assertThat(result.asObject()).isPresent();
        assertThat(result.asObject().get()).isEqualTo(listValue);

        assertThat(result.asObjects()).isPresent();
        assertThat(result.asObjects().get()).hasSize(1);
        assertThat(result.asObjects().get().get(0)).isEqualTo(object);
    }

    @Test
    public void testList2ObjectResult() {
        Object object = new Object();
        Object object2 = new Object();
        List<Object> listValue = List.of(object, object2);
        Result result = new Result(Optional.of(listValue), Status.OK);

        assertThat(result.asString()).isPresent();
        assertThat(result.asInt()).isNotPresent();
        assertThat(result.asBoolean()).hasValue(true);
        assertThat(result.asObject()).isPresent();
        assertThat(result.asObject().get()).isEqualTo(listValue);

        assertThat(result.asObjects()).isPresent();
        assertThat(result.asObjects().get()).hasSize(2);
        assertThat(result.asObjects().get().get(0)).isEqualTo(object);
        assertThat(result.asObjects().get().get(1)).isEqualTo(object2);
    }

}
