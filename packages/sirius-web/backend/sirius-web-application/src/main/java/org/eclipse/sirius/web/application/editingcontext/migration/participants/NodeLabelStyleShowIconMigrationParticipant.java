/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import com.google.gson.JsonObject;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.diagram.NodeLabelStyle;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that update the showIcon (to ShowIconExpression) of NodeLabelStyle.
 *
 * @author arichard
 */
@Service
public class NodeLabelStyleShowIconMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2024.9.0-202407191430";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void postObjectLoading(JsonResource resource, EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof NodeLabelStyle nodeLabelStyle) {
            var optionalNodeLabelStyle = Optional.ofNullable(jsonObject.getAsJsonObject("data"));
            optionalNodeLabelStyle.ifPresent(nodeLabelStyleData -> {
                var optionalShowIcon = Optional.ofNullable(nodeLabelStyleData.get("showIcon"));
                optionalShowIcon.ifPresent(showIcon -> {
                    nodeLabelStyle.setShowIconExpression("aql:" + showIcon);
                });
            });
        }
    }
}
