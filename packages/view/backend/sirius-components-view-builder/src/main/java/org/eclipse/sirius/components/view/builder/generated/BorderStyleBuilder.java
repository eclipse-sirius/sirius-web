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
package org.eclipse.sirius.components.view.builder.generated;

/**
 * Builder for org.eclipse.sirius.components.view.diagram.BorderStyle.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class BorderStyleBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.diagram.BorderStyle.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.diagram.BorderStyle getBorderStyle();

    /**
     * Setter for BorderColor.
     *
     * @generated
     */
    public BorderStyleBuilder borderColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getBorderStyle().setBorderColor(value);
        return this;
    }
    /**
     * Setter for BorderRadius.
     *
     * @generated
     */
    public BorderStyleBuilder borderRadius(java.lang.Integer value) {
        this.getBorderStyle().setBorderRadius(value);
        return this;
    }
    /**
     * Setter for BorderSize.
     *
     * @generated
     */
    public BorderStyleBuilder borderSize(java.lang.Integer value) {
        this.getBorderStyle().setBorderSize(value);
        return this;
    }
    /**
     * Setter for BorderLineStyle.
     *
     * @generated
     */
    public BorderStyleBuilder borderLineStyle(org.eclipse.sirius.components.view.diagram.LineStyle value) {
        this.getBorderStyle().setBorderLineStyle(value);
        return this;
    }

}

