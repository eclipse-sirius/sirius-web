/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.editingcontext.migration.participants;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.UserResizableDirection;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that update the UserResizable of NodeDescription, from a boolean to UserResizableDirection enum.
 *
 * @author frouene
 */
@Service
public class NodeDescriptionUserResizableMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2024.7.0-202406121000";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public Object getValue(EObject eObject, EStructuralFeature feature, Object value) {
        if (eObject instanceof NodeDescription && feature.getName().equals("userResizable")) {
            if (value instanceof String valueStr && this.isBoolean(valueStr)) {
                var newValue = UserResizableDirection.NONE;
                if (Boolean.parseBoolean(valueStr)) {
                    newValue = UserResizableDirection.BOTH;
                }
                return newValue;
            }
        }
        return null;
    }

    private boolean isBoolean(String str) {
        return "true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str);
    }
}
