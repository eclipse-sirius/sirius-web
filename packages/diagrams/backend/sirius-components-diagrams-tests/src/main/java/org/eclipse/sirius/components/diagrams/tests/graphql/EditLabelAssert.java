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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;

/**
 * Custom assertion record used to perform tests on the result of the invocation of the edit label mutation.
 *
 * @author gcoutable
 */
public record EditLabelAssert(String result) {

    public EditLabelAssert {
        Objects.requireNonNull(result);
    }

    public EditLabelAssert isSuccess() {
        String typename = JsonPath.read(this.result, "$.data.editLabel.__typename");
        assertThat(typename).isEqualTo(EditLabelSuccessPayload.class.getSimpleName());
        return this;
    }

    public EditLabelAssert isError() {
        String typename = JsonPath.read(this.result, "$.data.editLabel.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
        return this;
    }
}
