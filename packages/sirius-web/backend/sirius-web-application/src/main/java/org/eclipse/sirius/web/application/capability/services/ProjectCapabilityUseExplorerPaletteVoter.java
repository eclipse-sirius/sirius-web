package org.eclipse.sirius.web.application.capability.services;

import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Used to configure the server to grant the ProjectCapabilityUseExplorerPaletteVoter if the profile is set to dev.
 *
 * @author mcharfadi
 */
@Service
public class ProjectCapabilityUseExplorerPaletteVoter implements ICapabilityVoter {

    @Override
    public CapabilityVote vote(String type, String identifier, String capability) {
        boolean useExplorerPalette = Boolean.parseBoolean(System.getenv("USE_EXPLORER_PALETTE"));

        if (SiriusWebCapabilities.PROJECT.equals(type) && SiriusWebCapabilities.Project.USE_EXPLORER_PALETTE.equals(capability)) {
            if (useExplorerPalette) {
                return CapabilityVote.GRANTED;
            } else {
                return CapabilityVote.DENIED;
            }
        }
        return CapabilityVote.GRANTED;
    }
}