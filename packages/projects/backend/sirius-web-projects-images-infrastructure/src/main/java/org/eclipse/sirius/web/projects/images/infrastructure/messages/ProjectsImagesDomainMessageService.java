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
package org.eclipse.sirius.web.projects.images.infrastructure.messages;

import java.util.Objects;

import org.eclipse.sirius.web.projects.images.domain.messages.api.IProjectsImagesDomainMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Provides internationalized messages for the projects images domain.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectsImagesDomainMessageService implements IProjectsImagesDomainMessageService {

    private final MessageSourceAccessor messageSourceAccessor;

    public ProjectsImagesDomainMessageService(@Qualifier("projectsImagesDomainMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String invalidName() {
        return this.messageSourceAccessor.getMessage("INVALID_NAME");
    }

    @Override
    public String notFound() {
        return this.messageSourceAccessor.getMessage("NOT_FOUND");
    }

    @Override
    public String unexpectedError() {
        return this.messageSourceAccessor.getMessage("UNEXPECTED_ERROR");
    }
}
