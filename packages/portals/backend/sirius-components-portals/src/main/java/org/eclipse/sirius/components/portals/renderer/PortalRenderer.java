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
package org.eclipse.sirius.components.portals.renderer;

import java.util.Objects;

import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.description.PortalDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Renderer used to create a concrete Portal from a Portal description and state variables.
 *
 * @author pcdavid
 */
public class PortalRenderer {

    private final VariableManager variableManager;

    private final PortalDescription portalDescription;

    public PortalRenderer(VariableManager variableManager, PortalDescription portalDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.portalDescription = Objects.requireNonNull(portalDescription);
    }

    public Portal render() {
        String id = this.portalDescription.getIdProvider().apply(this.variableManager);
        String label = this.portalDescription.getLabelProvider().apply(this.variableManager);
        String targetObjectId = this.portalDescription.getTargetObjectIdProvider().apply(this.variableManager);
        var views = this.portalDescription.getViewsProvider().apply(this.variableManager);

        return Portal.newPortal(id)
                .label(label)
                .descriptionId(this.portalDescription.getId())
                .targetObjectId(targetObjectId)
                .views(views)
                .build();
    }

}
