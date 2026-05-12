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
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.boot.test.system.CapturedOutput;

/**
 * Custom assertion class used to perform tests on the result of the deletion of a project.
 *
 * @author sbegaudeau
 */
public class DeleteProjectAssert {

    private final IMessageService messageService;

    private final DeleteProjectInput input;

    private final GraphQLResult result;

    private final CapturedOutput capturedOutput;

    public DeleteProjectAssert(IMessageService messageService, DeleteProjectInput input, GraphQLResult result, CapturedOutput capturedOutput) {
        this.messageService = Objects.requireNonNull(messageService);
        this.input = Objects.requireNonNull(input);
        this.result = Objects.requireNonNull(result);
        this.capturedOutput = Objects.requireNonNull(capturedOutput);
    }

    public DeleteProjectAssert isSuccess() {
        assertThat(this.result.errors()).isEmpty();

        String typename = JsonPath.read(this.result.data(), "$.data.deleteProject.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

        assertThat(capturedOutput.getOut()).contains("Project " + this.input.projectId() + " deleted");
        return this;
    }

    public DeleteProjectAssert isProjectDeletionFailedError() {
        String typename = JsonPath.read(this.result.data(), "$.data.deleteProject.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());

        assertThat(this.capturedOutput.getOut()).contains("Deletion of project " + this.input.projectId() + " failed");
        return this;
    }

    public DeleteProjectAssert isCapabilityError() {
        String message = JsonPath.read(result.data(), "$.data.deleteProject.message");
        assertThat(message).isEqualTo(this.messageService.unauthorized());
        return this;
    }
}
