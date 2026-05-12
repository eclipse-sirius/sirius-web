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
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.boot.test.system.CapturedOutput;

/**
 * Custom assertion class used to perform tests on the result of the creation of a project.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class CreateProjectAssert {

    private final IMessageService messageService;

    private final CreateProjectInput input;

    private final GraphQLResult result;

    private final CapturedOutput capturedOutput;

    public CreateProjectAssert(IMessageService messageService, CreateProjectInput input, GraphQLResult result, CapturedOutput capturedOutput) {
        this.messageService = Objects.requireNonNull(messageService);
        this.input = Objects.requireNonNull(input);
        this.result = Objects.requireNonNull(result);
        this.capturedOutput = Objects.requireNonNull(capturedOutput);
    }

    public CreateProjectAssert isSuccess() {
        assertThat(this.result.errors()).isEmpty();

        String typename = JsonPath.read(this.result.data(), "$.data.createProject.__typename");
        assertThat(typename).isEqualTo(CreateProjectSuccessPayload.class.getSimpleName());

        String projectId = JsonPath.read(result.data(), "$.data.createProject.project.id");
        assertThat(this.capturedOutput.getOut()).contains("Project " + projectId + " created");
        return this;
    }

    public String getProjectId() {
        return JsonPath.read(result.data(), "$.data.createProject.project.id");
    }

    public CreateProjectAssert isProjectCreationFailedError() {
        String typename = JsonPath.read(result.data(), "$.data.createProject.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());

        assertThat(capturedOutput.getOut()).contains("Project creation failed");
        return this;
    }

    public CreateProjectAssert isProjectTemplateMissingError() {
        String typename = JsonPath.read(result.data(), "$.data.createProject.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());

        assertThat(capturedOutput.getOut()).contains("Project creation failed because the project template " + input.templateId() + " is missing");
        return this;
    }

    public CreateProjectAssert isCapabilityError() {
        String message = JsonPath.read(result.data(), "$.data.createProject.message");
        assertThat(message).isEqualTo(this.messageService.unauthorized());
        return this;
    }
}
