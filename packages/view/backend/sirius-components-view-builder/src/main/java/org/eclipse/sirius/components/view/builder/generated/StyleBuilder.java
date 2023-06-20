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
 * Builder for org.eclipse.sirius.components.view.diagram.Style.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class StyleBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.diagram.Style.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.diagram.Style getStyle();

    /**
     * Setter for Color.
     *
     * @generated
     */
    public StyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getStyle().setColor(value);
        return this;
    }

}

