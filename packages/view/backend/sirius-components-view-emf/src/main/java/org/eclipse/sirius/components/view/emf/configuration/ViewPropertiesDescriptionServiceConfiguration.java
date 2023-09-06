/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf.configuration;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.springframework.stereotype.Service;

/**
 * Bundles the common dependencies that view properties description services need into a single object for convenience.
 *
 * @author Jerome Gout
 */
@Service
public class ViewPropertiesDescriptionServiceConfiguration {

    private final IObjectService objectService;

    private final IEditService editService;

    private final IEMFKindService emfKindService;

    private final IFeedbackMessageService feedbackMessageService;

    public ViewPropertiesDescriptionServiceConfiguration(IObjectService objectService, IEditService editService, IEMFKindService emfKindService, IFeedbackMessageService feedbackMessageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.emfKindService = Objects.requireNonNull(emfKindService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    public IObjectService getObjectService() {
        return this.objectService;
    }

    public IEditService getEditService() {
        return this.editService;
    }

    public IEMFKindService getEmfKindService() {
        return this.emfKindService;
    }

    public IFeedbackMessageService getFeedbackMessageService() {
        return this.feedbackMessageService;
    }
}
