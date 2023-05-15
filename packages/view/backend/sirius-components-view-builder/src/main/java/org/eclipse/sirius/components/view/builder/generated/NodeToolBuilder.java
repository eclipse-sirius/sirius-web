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
 * Builder for NodeToolBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class NodeToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.NodeTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.NodeTool nodeTool = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createNodeTool();

    /**
     * Return instance org.eclipse.sirius.components.view.NodeTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.NodeTool getNodeTool() {
        return this.nodeTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.NodeTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.NodeTool build() {
        return this.getNodeTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public NodeToolBuilder name(java.lang.String value) {
        this.getNodeTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public NodeToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getNodeTool().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for SelectionDescription.
     *
     * @generated
     */
    public NodeToolBuilder selectionDescription(org.eclipse.sirius.components.view.SelectionDescription value) {
        this.getNodeTool().setSelectionDescription(value);
        return this;
    }

}

