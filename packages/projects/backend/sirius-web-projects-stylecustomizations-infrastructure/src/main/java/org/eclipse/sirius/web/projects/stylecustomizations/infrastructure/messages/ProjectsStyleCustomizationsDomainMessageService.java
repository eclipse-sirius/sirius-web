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
package org.eclipse.sirius.web.projects.stylecustomizations.infrastructure.messages;

import java.util.Objects;

import org.eclipse.sirius.web.projects.stylecustomizations.domain.messages.api.IProjectsStyleCustomizationsDomainMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Provide internationalized messages for the projects style customizations domain.
 *
 * @author gcoutable
 */
@Service
public class ProjectsStyleCustomizationsDomainMessageService implements IProjectsStyleCustomizationsDomainMessageService {

    private final MessageSourceAccessor messageSourceAccessor;

    public ProjectsStyleCustomizationsDomainMessageService(
            @Qualifier("projectsStyleCustomizationsDomainMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String notFound() {
        return this.messageSourceAccessor.getMessage("NOT_FOUND");
    }
}
