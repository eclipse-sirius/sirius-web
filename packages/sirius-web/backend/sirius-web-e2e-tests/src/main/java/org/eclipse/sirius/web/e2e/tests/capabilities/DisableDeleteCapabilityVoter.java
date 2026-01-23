/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.e2e.tests.capabilities;

import java.util.Objects;

import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.eclipse.sirius.web.application.project.services.api.IProjectSearchApplicationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Disable the rename capability for a specific project.
 *
 * @author gcoutable
 */
@Profile("test")
@Service
public class DisableDeleteCapabilityVoter implements ICapabilityVoter {

    private final IProjectSearchApplicationService projectSearchApplicationService;

    public DisableDeleteCapabilityVoter(IProjectSearchApplicationService projectSearchApplicationService) {
        this.projectSearchApplicationService = Objects.requireNonNull(projectSearchApplicationService);
    }

    @Override
    public CapabilityVote vote(String type, String identifier, String capability) {
        if (SiriusWebCapabilities.PROJECT.equals(type) && SiriusWebCapabilities.Project.DELETE.equals(capability) && identifier != null && !identifier.isBlank()) {
            var optionalProject = this.projectSearchApplicationService.findById(identifier);
            if (optionalProject.isPresent()) {
                var project = optionalProject.get();
                if (project.name().contains("Cypress - Disabled Delete Project")) {
                    return CapabilityVote.DENIED;
                }
            }
        }

        return CapabilityVote.ABSTAIN;
    }
}
