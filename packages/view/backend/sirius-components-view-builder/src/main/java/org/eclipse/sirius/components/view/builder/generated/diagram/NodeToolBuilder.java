/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.builder.generated.diagram;

/**
 * Builder for NodeToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class NodeToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.NodeTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.NodeTool nodeTool = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createNodeTool();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodeTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.NodeTool getNodeTool() {
        return this.nodeTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodeTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.NodeTool build() {
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
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public NodeToolBuilder preconditionExpression(java.lang.String value) {
        this.getNodeTool().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * <p>Available variables:</p>
     * <ul>
     *   <li>{@code self: Object} - The current element on which the operation is performed</li>
     *   <li>{@code editingContext: IEditingContext} - The editing context is an abstraction used to access all the semantic data</li>
     *   <li>{@code environment: Environment} - The environment may contain some information on the application currently running</li>
     *   <li>{@code diagramContext: DiagramContext} - Used to retrieve the diagram context which contains the diagram, the view creation and deletion requests and the diagram events</li>
     *   <li>{@code diagramServices: IDiagramService} - Used to access generic diagram services</li>
     *   <li>{@code selectedNode: Node} - The node on which the tool is being executed</li>
     *   <li>{@code selectedEdge: Edge} - The edge on which the tool is being executed</li>
     * </ul>
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
     * Setter for Description.
     *
     * @generated
     */
    public NodeToolBuilder description(java.lang.String value) {
        this.getNodeTool().setDescription(value);
        return this;
    }

    /**
     * Setter for DialogDescription.
     *
     * @generated
     */
    public NodeToolBuilder dialogDescription(org.eclipse.sirius.components.view.diagram.DialogDescription value) {
        this.getNodeTool().setDialogDescription(value);
        return this;
    }
    /**
     * Setter for IconURLsExpression.
     *
     * @generated
     */
    public NodeToolBuilder iconURLsExpression(java.lang.String value) {
        this.getNodeTool().setIconURLsExpression(value);
        return this;
    }
    /**
     * Setter for ElementsToSelectExpression.
     *
     * @generated
     */
    public NodeToolBuilder elementsToSelectExpression(java.lang.String value) {
        this.getNodeTool().setElementsToSelectExpression(value);
        return this;
    }
    /**
     * Setter for WithImpactAnalysis.
     *
     * @generated
     */
    public NodeToolBuilder withImpactAnalysis(java.lang.Boolean value) {
        this.getNodeTool().setWithImpactAnalysis(value);
        return this;
    }

    /**
     * Setter for KeyBindings.
     *
     * @generated
     */
    public NodeToolBuilder keyBindings(org.eclipse.sirius.components.view.KeyBinding ... values) {
        for (org.eclipse.sirius.components.view.KeyBinding value : values) {
            this.getNodeTool().getKeyBindings().add(value);
        }
        return this;
    }

}

