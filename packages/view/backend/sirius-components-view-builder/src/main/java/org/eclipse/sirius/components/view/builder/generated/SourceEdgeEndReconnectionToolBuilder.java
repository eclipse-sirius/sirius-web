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
 * Builder for SourceEdgeEndReconnectionToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class SourceEdgeEndReconnectionToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool sourceEdgeEndReconnectionTool = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createSourceEdgeEndReconnectionTool();

    /**
     * Return instance org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool getSourceEdgeEndReconnectionTool() {
        return this.sourceEdgeEndReconnectionTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool build() {
        return this.getSourceEdgeEndReconnectionTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public SourceEdgeEndReconnectionToolBuilder name(java.lang.String value) {
        this.getSourceEdgeEndReconnectionTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public SourceEdgeEndReconnectionToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getSourceEdgeEndReconnectionTool().getBody().add(value);
        }
        return this;
    }


}

