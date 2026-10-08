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
package org.eclipse.sirius.web.tests.graphql;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.UpdateProjectStyleCustomizationStateInput;
import org.springframework.boot.test.system.CapturedOutput;

/**
 * Used to perform tests on the result of the update of the project style customization state.
 *
 * @author gcoutable
 */
public class UpdateProjectStyleCustomizationStateAssert {

    private final UpdateProjectStyleCustomizationStateInput input;

    private final GraphQLResult result;

    private final CapturedOutput capturedOutput;

    public UpdateProjectStyleCustomizationStateAssert(UpdateProjectStyleCustomizationStateInput input, GraphQLResult result, CapturedOutput capturedOutput) {
        this.input = Objects.requireNonNull(input);
        this.result = Objects.requireNonNull(result);
        this.capturedOutput = Objects.requireNonNull(capturedOutput);
    }

    public UpdateProjectStyleCustomizationStateAssert isSuccess() {
        assertThat(this.result.errors()).isEmpty();

        String typename = JsonPath.read(this.result.data(), "$.data.updateProjectStyleCustomizationState.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        assertThat(this.capturedOutput.getOut()).contains("Update the project style customization " + this.input.styleCustomizationDescriptionId());
        return this;
    }
}
