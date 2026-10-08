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

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.eclipse.sirius.web.application.project.dto.RenameProjectSuccessPayload;
import org.springframework.boot.test.system.CapturedOutput;

/**
 * Used to perform tests on the result of the rename of a project.
 *
 * @author gcoutable
 */
public class RenameProjectAssert {

    private final RenameProjectInput input;

    private final GraphQLResult result;

    private final CapturedOutput capturedOutput;

    public RenameProjectAssert(RenameProjectInput input, GraphQLResult result, CapturedOutput capturedOutput) {
        this.input = Objects.requireNonNull(input);
        this.result = Objects.requireNonNull(result);
        this.capturedOutput = Objects.requireNonNull(capturedOutput);
    }

    public RenameProjectAssert isSuccess() {
        assertThat(this.result.errors()).isEmpty();

        String typename = JsonPath.read(this.result.data(), "$.data.renameProject.__typename");
        assertThat(typename).isEqualTo(RenameProjectSuccessPayload.class.getSimpleName());

        assertThat(this.capturedOutput.getOut()).contains("Project " + this.input.projectId() + " renamed");
        return this;
    }

    public RenameProjectAssert isError() {
        String typename = JsonPath.read(this.result.data(), "$.data.renameProject.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
        return this;
    }
}
