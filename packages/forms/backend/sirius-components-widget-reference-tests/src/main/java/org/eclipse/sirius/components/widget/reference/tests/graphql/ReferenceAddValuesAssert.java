/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.widget.reference.tests.graphql;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;

/**
 * Assertions on reference value addition mutation results.
 *
 * @author tgiraudet
 */
public class ReferenceAddValuesAssert {

    private final GraphQLResult result;

    public ReferenceAddValuesAssert(GraphQLResult result) {
        this.result = Objects.requireNonNull(result);
    }

    public ReferenceAddValuesAssert isSuccess() {
        assertThat(this.result.errors()).isEmpty();
        String typename = JsonPath.read(this.result.data(), "$.data.addReferenceValues.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        return this;
    }

    public ReferenceAddValuesAssert isError() {
        assertThat(this.result.errors()).isEmpty();
        String typename = JsonPath.read(this.result.data(), "$.data.addReferenceValues.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
        return this;
    }

    public ReferenceAddValuesAssert hasMessage(String expectedMessage) {
        List<String> messages = JsonPath.read(this.result.data(), "$.data.addReferenceValues.messages[*].body");
        assertThat(messages).containsExactly(expectedMessage);
        return this;
    }
}
