/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.capability.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.dto.FormCapabilitiesDTO;
import org.eclipse.sirius.components.collaborative.forms.services.api.IFormCapabilitiesService;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextService;
import org.springframework.stereotype.Service;

/**
 * The default implementation of IFormCapabilitiesService that delegate to project capabilities.
 *
 * @author gcoutable
 */
@Service
public class FormCapabilitiesService implements IFormCapabilitiesService {

    private final IProjectEditingContextService projectEditingContextService;

    private final ICapabilityEvaluator capabilityEvaluator;

    public FormCapabilitiesService(IProjectEditingContextService projectEditingContextService, ICapabilityEvaluator capabilityEvaluator) {
        this.projectEditingContextService = Objects.requireNonNull(projectEditingContextService);
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
    }

    @Override
    public FormCapabilitiesDTO getFormCapabilities(String editingContextId, String formId) {
        boolean canEdit = true;
        var optionalProjectId = this.projectEditingContextService.getProjectId(editingContextId);
        if (optionalProjectId.isPresent()) {
            canEdit = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.PROJECT, optionalProjectId.get(), SiriusWebCapabilities.Project.EDIT);
        }
        return new FormCapabilitiesDTO(canEdit);
    }
}
