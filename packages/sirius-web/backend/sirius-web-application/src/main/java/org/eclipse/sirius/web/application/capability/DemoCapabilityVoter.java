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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Used to configure the server for a demo.
 *
 * @author sbegaudeau
 */
@Service
@Profile("demo")
public class DemoCapabilityVoter implements ICapabilityVoter {
    @Override
    public CapabilityVote vote(String type, String identifier, String capability) {
        return CapabilityVote.DENIED;
    }
}
