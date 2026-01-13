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
package org.eclipse.sirius.web.tests.services.deck;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;

/**
 * Custom assertion class used to perform tests on the result of a deck lane collapsed state change.
 *
 * @author sbegaudeau
 * @since v2026.9.0
 */
public class ChangeLaneCollapsedStateAssert {

    private final GraphQLResult result;

    public ChangeLaneCollapsedStateAssert(GraphQLResult result) {
        this.result = Objects.requireNonNull(result);
    }

    public ChangeLaneCollapsedStateAssert isSuccess() {
        assertThat(this.result.errors()).isEmpty();

        String typename = JsonPath.read(this.result.data(), "$.data.changeLaneCollapsedState.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        return this;
    }
}
