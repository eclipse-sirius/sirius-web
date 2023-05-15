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
 * Builder for FixedColorBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class FixedColorBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.FixedColor.
     * @generated
     */
    private org.eclipse.sirius.components.view.FixedColor fixedColor = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createFixedColor();

    /**
     * Return instance org.eclipse.sirius.components.view.FixedColor.
     * @generated
     */
    protected org.eclipse.sirius.components.view.FixedColor getFixedColor() {
        return this.fixedColor;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.FixedColor.
     * @generated
     */
    public org.eclipse.sirius.components.view.FixedColor build() {
        return this.getFixedColor();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public FixedColorBuilder name(java.lang.String value) {
        this.getFixedColor().setName(value);
        return this;
    }
    /**
     * Setter for Value.
     *
     * @generated
     */
    public FixedColorBuilder value(java.lang.String value) {
        this.getFixedColor().setValue(value);
        return this;
    }

}

