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
package org.eclipse.sirius.web.application.capability;

import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Used to control the project duplication capability while the complete feature is not complete.
 *
 * @author Arthur Daussy
 */
@Service
public class ProjectDuplicationCapabilityVoter implements ICapabilityVoter {

    private final boolean enableProjectDuplication;

    public ProjectDuplicationCapabilityVoter(@Value("${sirius.web.capability.project.duplication:false}") boolean enableProjectDuplication) {
        this.enableProjectDuplication = enableProjectDuplication;
    }

    @Override
    public CapabilityVote vote(String type, String identifier, String capability) {
        //This feature is not fully implemented yet so disable it
        if (SiriusWebCapabilities.Project.DUPLICATE.equals(capability) && !this.enableProjectDuplication) {
            return CapabilityVote.DENIED;
        }
        return CapabilityVote.GRANTED;
    }

}
