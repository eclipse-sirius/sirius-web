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
 * Builder for SetValueBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class SetValueBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.SetValue.
     * @generated
     */
    private org.eclipse.sirius.components.view.SetValue setValue = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createSetValue();

    /**
     * Return instance org.eclipse.sirius.components.view.SetValue.
     * @generated
     */
    protected org.eclipse.sirius.components.view.SetValue getSetValue() {
        return this.setValue;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.SetValue.
     * @generated
     */
    public org.eclipse.sirius.components.view.SetValue build() {
        return this.getSetValue();
    }

    /**
     * Setter for Children.
     *
     * @generated
     */
    public SetValueBuilder children(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getSetValue().getChildren().add(value);
        }
        return this;
    }

    /**
     * Setter for FeatureName.
     *
     * @generated
     */
    public SetValueBuilder featureName(java.lang.String value) {
        this.getSetValue().setFeatureName(value);
        return this;
    }
    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public SetValueBuilder valueExpression(java.lang.String value) {
        this.getSetValue().setValueExpression(value);
        return this;
    }

}

