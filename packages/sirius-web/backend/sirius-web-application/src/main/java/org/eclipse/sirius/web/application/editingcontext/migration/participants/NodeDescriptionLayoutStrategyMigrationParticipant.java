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
package org.eclipse.sirius.web.application.editingcontext.migration.participants;

import com.google.gson.JsonObject;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that move the childrenLayoutStrategy from NodeDescription to NodeStyleDescription.
 *
 * @author frouene
 */
@Service
public class NodeDescriptionLayoutStrategyMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2025.6.0-202505141000";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void postObjectLoading(EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof NodeDescription nodeDescription) {
            var optionalNodeDescriptionData = Optional.ofNullable(jsonObject.getAsJsonObject("data"));
            optionalNodeDescriptionData.ifPresent(nodeDescriptionData -> {
                var optionalLayoutStrategy = Optional.ofNullable(nodeDescriptionData.get("childrenLayoutStrategy"));
                optionalLayoutStrategy.ifPresent(layoutStrategyJsonElement -> {
                    if (nodeDescription.getStyle() != null) {
                        String layoutStrategyEClass = ((JsonObject) layoutStrategyJsonElement).get("eClass").getAsString();
                        if (layoutStrategyEClass.equals("diagram:FreeFormLayoutStrategyDescription")) {
                            var layoutStrategy = DiagramPackage.eINSTANCE.getDiagramFactory().createFreeFormLayoutStrategyDescription();
                            nodeDescription.getStyle().setChildrenLayoutStrategy(layoutStrategy);
                        } else if (layoutStrategyEClass.equals("diagram:ListLayoutStrategyDescription")) {
                            var layoutStrategy = DiagramPackage.eINSTANCE.getDiagramFactory().createListLayoutStrategyDescription();
                            Optional.ofNullable(((JsonObject) layoutStrategyJsonElement).getAsJsonObject("data")).ifPresent(layoutStrategyData -> {
                                layoutStrategy.setAreChildNodesDraggableExpression(layoutStrategyData.get("areChildNodesDraggableExpression").getAsString());
                                layoutStrategy.setTopGapExpression(layoutStrategyData.get("topGapExpression").getAsString());
                                layoutStrategy.setBottomGapExpression(layoutStrategyData.get("bottomGapExpression").getAsString());
                            });
                            nodeDescription.getStyle().setChildrenLayoutStrategy(layoutStrategy);
                        }
                    }
                });
            });
        }
    }
}
