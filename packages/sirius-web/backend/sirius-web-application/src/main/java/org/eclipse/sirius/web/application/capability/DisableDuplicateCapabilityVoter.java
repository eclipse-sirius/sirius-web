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
 * Temporary disable duplicate capability while the complete feature is not complete.
 *
 * @author Arthur Daussy
 */
@Service
public class DisableDuplicateCapabilityVoter implements ICapabilityVoter {

    private final boolean disableDuplication;

    public DisableDuplicateCapabilityVoter(@Value("${sirius.web.capability.disable:true}") boolean disableDuplication) {
        this.disableDuplication = disableDuplication;
    }

    @Override
    public CapabilityVote vote(String type, String identifier, String capability) {
        // Uncomment before committing
        //This feature is not fully implemented yet so disable it
        if (SiriusWebCapabilities.Project.DUPLICATE.equals(capability) && disableDuplication) {
            return CapabilityVote.DENIED;
        }
        return CapabilityVote.GRANTED;
    }

}
