/*******************************************************************************
 * Copyright (c) 2021, 2023 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.diagrams.layout.incremental.data;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.TextBounds;

/**
 * A mutable structure to store/update the labels layout.
 *
 * @author wpiers
 */
public class LabelLayoutData implements ILayoutData {

    private TextBounds textBounds;

    private Position position;

    private String labelType;

    private String id;

    private boolean isHeader;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getLabelType() {
        return this.labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public TextBounds getTextBounds() {
        return this.textBounds;
    }

    public void setTextBounds(TextBounds textBounds) {
        this.textBounds = textBounds;
    }

    public boolean isHeader() {
        return this.isHeader;
    }

    public void setIsHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

}
