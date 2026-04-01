/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.web.application.diagram.services;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.api.ILayoutConfigurationProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutConfiguration;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutOptionEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * The default provider used to compute the layout configurations of a diagram.
 *
 * @author ocailleau
 */

@Component
public class DefaultLayoutConfigurationProvider implements ILayoutConfigurationProvider {

    private final IMessageService messageService;

    public DefaultLayoutConfigurationProvider(IMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription) {
        return true;
    }

    @Override
    public List<LayoutConfiguration> getLayoutConfiguration(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription) {

        String direction = diagramDescription.getArrangeLayoutDirection().name();

        LayoutConfiguration layoutLayered = LayoutConfiguration.newLayoutConfiguration("elk-layered")
                .label(this.messageService.diagramLayoutFlow())
                .iconURL(List.of(DiagramImageConstants.LAYERED_SVG))
                .layoutOptions(getLayeredOptions(direction))
                .build();

        LayoutConfiguration layoutRectPacking = LayoutConfiguration.newLayoutConfiguration("elk-rect-packing")
                .label(this.messageService.diagramLayoutCompact())
                .iconURL(List.of(DiagramImageConstants.RECTPACKING_SVG))
                .layoutOptions(getRectPackingOptions())
                .build();

        return List.of(layoutLayered, layoutRectPacking);
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private List<LayoutOptionEntry> getLayeredOptions(String direction) {
        return List.of(
                new LayoutOptionEntry("elk.algorithm", "layered"),
                new LayoutOptionEntry("elk.layered.spacing.nodeNodeBetweenLayers", "80"),
                new LayoutOptionEntry("layering.strategy", "NETWORK_SIMPLEX"),
                new LayoutOptionEntry("elk.spacing.componentComponent", "60"),
                new LayoutOptionEntry("elk.spacing.nodeNode", "80"),
                new LayoutOptionEntry("elk.direction", direction),
                new LayoutOptionEntry("elk.layered.spacing.edgeNodeBetweenLayers", "80"),
                new LayoutOptionEntry("elk.layered.nodePlacement.strategy", "NETWORK_SIMPLEX")
        );
    }

    private List<LayoutOptionEntry> getRectPackingOptions() {
        return List.of(
                new LayoutOptionEntry("elk.algorithm", "rectpacking"),
                new LayoutOptionEntry("elk.spacing.nodeNode", "50"),
                new LayoutOptionEntry("elk.rectpacking.trybox", "true"),
                new LayoutOptionEntry("widthApproximation.targetWidth", "1"),
                new LayoutOptionEntry("elk.contentAlignment", "V_TOP H_CENTER")
        );
    }
}