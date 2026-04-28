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
package org.eclipse.sirius.web.application.project.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.application.project.services.api.IProjectZipContentValidator;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to validate the content of the zipped project.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectZipContentValidator implements IProjectZipContentValidator {

    private final IEditingContextSearchService editingContextSearchService;

    private final IMessageService messageService;

    private final Logger logger = LoggerFactory.getLogger(ProjectZipContentValidator.class);

    public ProjectZipContentValidator(IEditingContextSearchService editingContextSearchService, IMessageService messageService) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    @Transactional(readOnly = true)
    public IResult<Void> validate(ProjectZipContent projectZipContent) {
        IResult<Void> result = new Success<>(null);

        var dependenciesEntry = projectZipContent.manifest().get(ProjectZipContent.DEPENDENCIES);
        if (dependenciesEntry instanceof List<?> list) {
            var unknownDependencies = list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .filter(dependencyId -> !this.editingContextSearchService.existsById(dependencyId))
                    .toList();

            if (!unknownDependencies.isEmpty()) {
                this.logger.atWarn()
                        .setMessage("Project zip content validation failed. Unknown dependencies: {}")
                        .addArgument(unknownDependencies)
                        .log();

                result = new Failure<>(this.messageService.unknownDependencies());
            }
        }

        return result;
    }
}
