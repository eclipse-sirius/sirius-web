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
package org.eclipse.sirius.components.flow.starter.services;

import fr.obeo.dsl.designer.sample.flow.FlowPackage;
import fr.obeo.dsl.designer.sample.flow.Named;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerLabelServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Used to prevent the edition of all the flow concepts that do not have a name.
 *
 * @author sbegaudeau
 */
@Service
public class FlowExplorerLabelServiceDelegate implements IExplorerLabelServiceDelegate {

    @Override
    public boolean canHandle(Object object) {
        return object instanceof EObject eObject
                && eObject.eClass().getEPackage().getNsURI().equals(FlowPackage.eNS_URI)
                && !(object instanceof Named);
    }

    @Override
    public boolean isEditable(Object self) {
        return false;
    }

    @Override
    public void editLabel(Object self, String newValue) {
        // Do nothing
    }
}
