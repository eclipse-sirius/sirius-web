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
 * Builder for UnsetValueBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class UnsetValueBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.UnsetValue.
     * @generated
     */
    private org.eclipse.sirius.components.view.UnsetValue unsetValue = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createUnsetValue();

    /**
     * Return instance org.eclipse.sirius.components.view.UnsetValue.
     * @generated
     */
    protected org.eclipse.sirius.components.view.UnsetValue getUnsetValue() {
        return this.unsetValue;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.UnsetValue.
     * @generated
     */
    public org.eclipse.sirius.components.view.UnsetValue build() {
        return this.getUnsetValue();
    }

    /**
     * Setter for Children.
     *
     * @generated
     */
    public UnsetValueBuilder children(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getUnsetValue().getChildren().add(value);
        }
        return this;
    }

    /**
     * Setter for FeatureName.
     *
     * @generated
     */
    public UnsetValueBuilder featureName(java.lang.String value) {
        this.getUnsetValue().setFeatureName(value);
        return this;
    }
    /**
     * Setter for ElementExpression.
     *
     * @generated
     */
    public UnsetValueBuilder elementExpression(java.lang.String value) {
        this.getUnsetValue().setElementExpression(value);
        return this;
    }

}

