/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.layout.incremental.data;

import java.util.List;

import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Ratio;

/**
 * A mutable structure to store/update the edges layout.
 *
 * @author wpiers
 */
public class EdgeLayoutData implements ILayoutData {

    private List<Position> routingPoints;

    private Ratio sourceAnchorRelativePosition;

    private Ratio targetAnchorRelativePosition;

    private NodeLayoutData source;

    private NodeLayoutData target;

    private LabelLayoutData beginLabel;

    private LabelLayoutData centerLabel;

    private LabelLayoutData endLabel;

    private String id;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Position> getRoutingPoints() {
        return this.routingPoints;
    }

    public void setRoutingPoints(List<Position> routingPoints) {
        this.routingPoints = routingPoints;
    }

    public Ratio getSourceAnchorRelativePosition() {
        return this.sourceAnchorRelativePosition;
    }

    public void setSourceAnchorRelativePosition(Ratio sourceAnchorRelativePosition) {
        this.sourceAnchorRelativePosition = sourceAnchorRelativePosition;
    }

    public Ratio getTargetAnchorRelativePosition() {
        return this.targetAnchorRelativePosition;
    }

    public void setTargetAnchorRelativePosition(Ratio targetAnchorRelativePosition) {
        this.targetAnchorRelativePosition = targetAnchorRelativePosition;
    }

    public NodeLayoutData getSource() {
        return this.source;
    }

    public void setSource(NodeLayoutData source) {
        this.source = source;
    }

    public NodeLayoutData getTarget() {
        return this.target;
    }

    public void setTarget(NodeLayoutData target) {
        this.target = target;
    }

    public LabelLayoutData getBeginLabel() {
        return this.beginLabel;
    }

    public void setBeginLabel(LabelLayoutData beginLabel) {
        this.beginLabel = beginLabel;
    }

    public LabelLayoutData getCenterLabel() {
        return this.centerLabel;
    }

    public void setCenterLabel(LabelLayoutData centerLabel) {
        this.centerLabel = centerLabel;
    }

    public LabelLayoutData getEndLabel() {
        return this.endLabel;
    }

    public void setEndLabel(LabelLayoutData endLabel) {
        this.endLabel = endLabel;
    }
}
