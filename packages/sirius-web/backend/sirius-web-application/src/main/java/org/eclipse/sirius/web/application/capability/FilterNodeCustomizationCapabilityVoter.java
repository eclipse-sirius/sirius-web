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
package org.eclipse.sirius.web.application.capability;

import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * A temporary capability voter to keep the filtering of node customization disabled in the UI.
 *
 * @author gcoutable
 */
@Service
public class FilterNodeCustomizationCapabilityVoter implements ICapabilityVoter {

    private final boolean projectAppearanceSettingsEnabled;

    public FilterNodeCustomizationCapabilityVoter(@Value("${sirius.web.project.appearance.settings.enabled:false}")boolean projectAppearanceSettingsEnabled) {
        this.projectAppearanceSettingsEnabled = projectAppearanceSettingsEnabled;
    }

    @Override
    public CapabilityVote vote(String type, String identifier, String capability) {
        if (SiriusWebCapabilities.PROJECT_SETTINGS_APPEARANCE_TAB.equals(type) && !this.projectAppearanceSettingsEnabled) {
            return CapabilityVote.DENIED;
        }
        return CapabilityVote.GRANTED;
    }
}
