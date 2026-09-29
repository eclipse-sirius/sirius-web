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

import java.util.Objects;

import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;

/**
 * Assertions on reference clearing mutation results.
 *
 * @author tgiraudet
 */
public class ReferenceClearAssert {

    private final GraphQLResult result;

    public ReferenceClearAssert(GraphQLResult result) {
        this.result = Objects.requireNonNull(result);
    }

    public ReferenceClearAssert isSuccess() {
        assertThat(this.result.errors()).isEmpty();
        String typename = JsonPath.read(this.result.data(), "$.data.clearReference.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        return this;
    }
}

