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

import org.eclipse.sirius.components.collaborative.widget.reference.dto.CreateElementInReferenceSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;

/**
 * Custom assertions on the result of element creation in references.
 *
 * @author tgiraudet
 */
public class ReferenceCreateElementAssert {

    private final GraphQLResult result;

    public ReferenceCreateElementAssert(GraphQLResult result) {
        this.result = Objects.requireNonNull(result);
    }

    public ReferenceCreateElementAssert isSuccess() {
        assertThat(this.result.errors()).isEmpty();
        String typename = JsonPath.read(this.result.data(), "$.data.createElementInReference.__typename");
        assertThat(typename).isEqualTo(CreateElementInReferenceSuccessPayload.class.getSimpleName());
        return this;
    }

    public String getObjectId() {
        String objectId = JsonPath.read(this.result.data(), "$.data.createElementInReference.object.id");
        assertThat(objectId).isNotBlank();
        return objectId;
    }

    public ReferenceCreateElementAssert isError() {
        assertThat(this.result.errors()).isEmpty();
        String typename = JsonPath.read(this.result.data(), "$.data.createElementInReference.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
        return this;
    }

    public ReferenceCreateElementAssert hasMessage(String expectedMessage) {
        List<String> messages = JsonPath.read(this.result.data(), "$.data.createElementInReference.messages[*].body");
        assertThat(messages).containsExactly(expectedMessage);
        return this;
    }
}
