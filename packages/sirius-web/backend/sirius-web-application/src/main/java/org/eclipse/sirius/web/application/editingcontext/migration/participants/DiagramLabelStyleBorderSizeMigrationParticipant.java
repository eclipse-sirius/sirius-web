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

import com.google.gson.JsonObject;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that update the BorderStyle of NodeLabelStyle.
 *
 * @author mcharfadi
 */
@Service
public class DiagramLabelStyleBorderSizeMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2024.7.0-202407090945";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void postObjectLoading(EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof NodeDescription nodeDescription) {
            var optionalNodeDescriptionData = Optional.ofNullable(jsonObject.getAsJsonObject("data"));
            optionalNodeDescriptionData.ifPresent(nodeDescriptionData -> {
                var optionalInsideLabel = Optional.ofNullable(nodeDescriptionData.get("insideLabel"));
                optionalInsideLabel.ifPresent(insideLabel -> {
                    if (nodeDescription.getInsideLabel().getStyle() != null) {
                        nodeDescription.getInsideLabel().getStyle().setBorderSize(0);
                    }
                });
                var optionalOutsideLabels = Optional.ofNullable(nodeDescriptionData.get("outsideLabels"));
                optionalOutsideLabels.ifPresent(outsideLabels -> nodeDescription.getOutsideLabels().forEach(outsideLabelDescription -> {
                    if (outsideLabelDescription.getStyle() != null) {
                        outsideLabelDescription.getStyle().setBorderSize(0);
                    }
                }));
            });
        } else if (eObject instanceof EdgeDescription edgeDescription) {
            var optionalEdgeDescriptionData = Optional.ofNullable(jsonObject.getAsJsonObject("data"));
            optionalEdgeDescriptionData.ifPresent(edgeDescriptionData -> {
                if (edgeDescription.getStyle() != null) {
                    edgeDescription.getStyle().setBorderSize(0);
                }
            });
        }
    }
}
