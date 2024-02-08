/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.diagram.adapters;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;

/**
 * Adapter allowing to set default colors after the creation of node/edge style elements.
 *
 * @author arichard
 */
public class DiagramColorAdapter extends EContentAdapter {

    private final ColorPaletteService colorPaletteService;

    public DiagramColorAdapter(View colorPalettesView) {
        this.colorPaletteService = new ColorPaletteService(colorPalettesView);
    }

    @Override
    public void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof DiagramDescription && notification.getNewValue() instanceof NodeDescription nodeDescription
                && DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS == notification.getFeatureID(DiagramDescription.class)) {
            NodeStyleDescription style = nodeDescription.getStyle();
            this.setNodeColors(style, nodeDescription);
        } else if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof NodeDescription && notification.getNewValue() instanceof NodeDescription nodeDescription
                && (notification.getFeatureID(NodeDescription.class) == DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS || notification.getFeatureID(NodeDescription.class) == DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS)) {
            NodeStyleDescription style = nodeDescription.getStyle();
            this.setNodeColors(style, nodeDescription);
        } else if (Notification.SET == notification.getEventType() && notification.getNotifier() instanceof NodeDescription nodeDescription && notification.getNewValue() instanceof NodeStyleDescription style
                && DiagramPackage.NODE_DESCRIPTION__STYLE == notification.getFeatureID(NodeStyleDescription.class)) {
            this.setNodeColors(style, nodeDescription);
        } else if (Notification.SET == notification.getEventType() && notification.getNotifier() instanceof ConditionalNodeStyle condition && notification.getNewValue() instanceof NodeStyleDescription style
                && DiagramPackage.CONDITIONAL_NODE_STYLE__STYLE == notification.getFeatureID(ConditionalNodeStyle.class)) {
            this.setNodeColors(style, condition);
        } else if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof DiagramDescription && notification.getNewValue() instanceof EdgeDescription edgeDescription
                && DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS == notification.getFeatureID(DiagramDescription.class)) {
            EdgeStyle style = edgeDescription.getStyle();
            if (style.getColor() == null) {
                style.setColor(this.colorPaletteService.getColorFromPalette(edgeDescription, "black"));
            }
        } else if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof EdgeDescription condition && notification.getNewValue() instanceof EdgeStyle style
                && DiagramPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES == notification.getFeatureID(EdgeDescription.class)) {
            if (style.getColor() == null) {
                style.setColor(this.colorPaletteService.getColorFromPalette(condition, "black"));
            }
        }
    }

    private void setNodeColors(NodeStyleDescription style, Object object) {
        if (style != null && style.getColor() == null) {
            style.setColor(this.colorPaletteService.getColorFromPalette(object, "white"));
        }
        if (style != null && style.getBorderColor() == null) {
            style.setBorderColor(this.colorPaletteService.getColorFromPalette(object, "black"));
        }
    }
}
