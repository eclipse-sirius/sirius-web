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
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that update the LabelExpression of NodeDescription with
 * InsideLabelDescription or OutsideLabelDescription.
 *
 * @author mcharfadi
 */
@Service
public class NodeDescriptionLabelExpressionMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2024.5.0-202404221400";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void postObjectLoading(EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof NodeDescription nodeDescription) {
            var optionalNodeDescriptionData = Optional.ofNullable(jsonObject.getAsJsonObject("data"));
            optionalNodeDescriptionData.ifPresent(nodeDescriptionData -> {
                var optionalLabelExpression = Optional.ofNullable(nodeDescriptionData.get("labelExpression"));
                optionalLabelExpression.ifPresent(labelExpression -> {
                    if (nodeDescription.getStyle() instanceof RectangularNodeStyleDescription) {
                        InsideLabelDescription insideLabelDescription = DiagramPackage.eINSTANCE.getDiagramFactory().createInsideLabelDescription();
                        insideLabelDescription.setLabelExpression(labelExpression.getAsString());
                        nodeDescription.setInsideLabel(insideLabelDescription);
                    }
                    if (nodeDescription.getStyle() instanceof ImageNodeStyleDescription) {
                        OutsideLabelDescription outsideLabelDescription = DiagramPackage.eINSTANCE.getDiagramFactory().createOutsideLabelDescription();
                        outsideLabelDescription.setLabelExpression(labelExpression.getAsString());
                        nodeDescription.getOutsideLabels().add(outsideLabelDescription);
                    }
                });
            });
        }
    }
}
