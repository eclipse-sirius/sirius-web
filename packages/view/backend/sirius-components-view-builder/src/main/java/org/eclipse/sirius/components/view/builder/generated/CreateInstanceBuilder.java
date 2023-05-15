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
 * Builder for CreateInstanceBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class CreateInstanceBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.CreateInstance.
     * @generated
     */
    private org.eclipse.sirius.components.view.CreateInstance createInstance = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createCreateInstance();

    /**
     * Return instance org.eclipse.sirius.components.view.CreateInstance.
     * @generated
     */
    protected org.eclipse.sirius.components.view.CreateInstance getCreateInstance() {
        return this.createInstance;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.CreateInstance.
     * @generated
     */
    public org.eclipse.sirius.components.view.CreateInstance build() {
        return this.getCreateInstance();
    }

    /**
     * Setter for Children.
     *
     * @generated
     */
    public CreateInstanceBuilder children(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getCreateInstance().getChildren().add(value);
        }
        return this;
    }

    /**
     * Setter for TypeName.
     *
     * @generated
     */
    public CreateInstanceBuilder typeName(java.lang.String value) {
        this.getCreateInstance().setTypeName(value);
        return this;
    }
    /**
     * Setter for ReferenceName.
     *
     * @generated
     */
    public CreateInstanceBuilder referenceName(java.lang.String value) {
        this.getCreateInstance().setReferenceName(value);
        return this;
    }
    /**
     * Setter for VariableName.
     *
     * @generated
     */
    public CreateInstanceBuilder variableName(java.lang.String value) {
        this.getCreateInstance().setVariableName(value);
        return this;
    }

}

