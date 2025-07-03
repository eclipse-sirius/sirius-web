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
package org.eclipse.sirius.web.services.capabilities;

import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.springframework.stereotype.Service;

/**
 * Disable capabilities of the Sysml sample project for test purpose.
 *
 * @author gcoutable
 */
@Service
public class DisableSysmlSampleProjectCapabilitiesCapabilityVoter implements ICapabilityVoter {
    @Override
    public CapabilityVote vote(String type, String identifier, String capability) {
        if (SiriusWebCapabilities.PROJECT.equals(type) && TestIdentifiers.SYSML_SAMPLE_PROJECT.equals(identifier)) {
            return CapabilityVote.DENIED;
        }
        return CapabilityVote.GRANTED;
    }
}
