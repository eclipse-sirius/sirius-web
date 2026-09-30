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

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;

/**
 * Custom assertion record used to perform tests on the result of the invocation of the edit rectangular node appearance mutation.
 *
 * @author gcoutable
 */
public record EditRectangularNodeAppearanceAssert(String result) {
    public EditRectangularNodeAppearanceAssert {
        Objects.requireNonNull(result);
    }

    public EditRectangularNodeAppearanceAssert isSuccess() {
        String typename = JsonPath.read(this.result, "$.data.editRectangularNodeAppearance.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        return this;
    }

    public EditRectangularNodeAppearanceAssert isError() {
        String typename = JsonPath.read(this.result, "$.data.editRectangularNodeAppearance.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
        return this;
    }
}
