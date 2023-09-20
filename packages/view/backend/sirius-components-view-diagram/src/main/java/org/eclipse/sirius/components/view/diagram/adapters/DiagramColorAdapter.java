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

    private ColorPaletteService colorPaletteService;

    public DiagramColorAdapter(View colorPalettesView) {
        this.colorPaletteService = new ColorPaletteService(colorPalettesView);
    }

    @Override
    public void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof DiagramDescription && notification.getNewValue() instanceof NodeDescription nodeDescription
                && DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS == notification.getFeatureID(DiagramDescription.class)) {
            NodeStyleDescription style = nodeDescription.getStyle();
            if (style != null && style.getColor() == null) {
                style.setColor(this.colorPaletteService.getColorFromPalette(nodeDescription, "white"));
            }
            if (style != null && style.getBorderColor() == null) {
                style.setBorderColor(this.colorPaletteService.getColorFromPalette(nodeDescription, "black"));
            }
            if (style != null && style.getLabelColor() == null) {
                style.setLabelColor(this.colorPaletteService.getColorFromPalette(nodeDescription, "black"));
            }
        } else if (Notification.SET == notification.getEventType() && notification.getNotifier() instanceof ConditionalNodeStyle condition && notification.getNewValue() instanceof NodeStyleDescription style
                && DiagramPackage.CONDITIONAL_NODE_STYLE__STYLE == notification.getFeatureID(ConditionalNodeStyle.class)) {
            if (style.getColor() == null) {
                style.setColor(this.colorPaletteService.getColorFromPalette(condition, "white"));
            }
            if (style.getBorderColor() == null) {
                style.setBorderColor(this.colorPaletteService.getColorFromPalette(condition, "black"));
            }
            if (style.getLabelColor() == null) {
                style.setLabelColor(this.colorPaletteService.getColorFromPalette(condition, "black"));
            }
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
}
