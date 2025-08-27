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

import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.springframework.stereotype.Service;

/**
 * Used to configure the server to grant the TreeCapabilityUseExplorerPaletteVoter if the env variable USE_EXPLORER_PALETTE is set to true.
 *
 * @author mcharfadi
 */
@Service
public class TreeCapabilityUseExplorerPaletteVoter implements ICapabilityVoter {

    @Override
    public CapabilityVote vote(String type, String identifier, String capability) {
        boolean useExplorerPalette = Boolean.parseBoolean(System.getenv("USE_EXPLORER_PALETTE"));

        if (SiriusWebCapabilities.TREE.equals(type) && SiriusWebCapabilities.Tree.USE_EXPLORER_PALETTE.equals(capability) && !useExplorerPalette) {
            return CapabilityVote.DENIED;
        } else {
            return CapabilityVote.GRANTED;
        }
    }
}