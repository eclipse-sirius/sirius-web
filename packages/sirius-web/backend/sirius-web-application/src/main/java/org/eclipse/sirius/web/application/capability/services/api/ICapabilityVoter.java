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
package org.eclipse.sirius.web.application.capability.services.api;

import org.eclipse.sirius.web.application.capability.services.CapabilityVote;

/**
 * Used to vote on the capability.
 *
 * <p>
 *     It is meant to be used in a data fetcher that requests for capabilities to enable or disable a frontend capability.
 * </p>
 *
 * <p>
 *    <em>Ensure your capability voter returns {@link CapabilityVote#GRANTED} by default in order to prevent all other capabilities to be disabled by default.</em>
 * </p>
 * <hr/>
 * <p>
 *     Example to disable a capability:
 * </p>
 *
 * {@snippet id = "disable-create-project-capability" lang = "java":
 * import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
 *
 * public class DisableCanCreateProjectCapabilityVoter implements ICapabilityVoter {
 *
 *     @Override
 *      public CapabilityVote vote(String type, String identifier, String capability) {
 *          if (SiriusWebCapabilities.PROJECT.equals(type) && identifier == null && SiriusWebCapabilities.Project.CREATE.equals(capability)) {
 *              return CapabilityVote.DENIED;
 *          }
 *          return CapabilityVote.GRANTED;
 *      }
 * }
 *}
 *
 *
 * @see org.eclipse.sirius.web.application.capability.controllers.ProjectsCapabilitiesCanCreateDataFetcher
 * @author gcoutable
 */
public interface ICapabilityVoter {
    CapabilityVote vote(String type, String identifier, String capability);
}
