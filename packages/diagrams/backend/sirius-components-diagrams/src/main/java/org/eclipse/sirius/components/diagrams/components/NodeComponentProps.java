/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo and others.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.diagrams.renderer.INodeAppearanceHandler;
import org.eclipse.sirius.components.representations.IOperationValidator;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the node component.
 *
 * @author sbegaudeau
 */
@Immutable
public final class NodeComponentProps implements IProps {

    private VariableManager variableManager;

    private NodeDescription nodeDescription;

    private INodesRequestor nodesRequestor;

    private INodeDescriptionRequestor nodeDescriptionRequestor;

    private NodeContainmentKind containmentKind;

    private DiagramRenderingCache cache;

    private List<ViewCreationRequest> viewCreationRequests;

    private List<ViewDeletionRequest> viewDeletionRequests;

    private String parentElementId;

    private ViewModifier parentElementState;

    private List<String> previousTargetObjectIds;

    private IOperationValidator operationValidator;

    private List<IDiagramEvent> diagramEvents;

    private List<INodeAppearanceHandler> nodeAppearanceHandlers;

    private NodeComponentProps() {
        // Prevent instantiation
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public NodeDescription getNodeDescription() {
        return this.nodeDescription;
    }

    public INodesRequestor getNodesRequestor() {
        return this.nodesRequestor;
    }

    public INodeDescriptionRequestor getNodeDescriptionRequestor() {
        return this.nodeDescriptionRequestor;
    }

    public NodeContainmentKind getContainmentKind() {
        return this.containmentKind;
    }

    public DiagramRenderingCache getCache() {
        return this.cache;
    }

    public List<ViewCreationRequest> getViewCreationRequests() {
        return this.viewCreationRequests;
    }

    public List<ViewDeletionRequest> getViewDeletionRequests() {
        return this.viewDeletionRequests;
    }

    public String getParentElementId() {
        return this.parentElementId;
    }

    public ViewModifier getParentElementState() {
        return this.parentElementState;
    }

    public List<String> getPreviousTargetObjectIds() {
        return this.previousTargetObjectIds;
    }

    public List<IDiagramEvent> getDiagramEvents() {
        return this.diagramEvents;
    }

    public IOperationValidator getOperationValidator() {
        return this.operationValidator;
    }

    public List<INodeAppearanceHandler> getNodeAppearanceHandlers() {
        return this.nodeAppearanceHandlers;
    }

    public static Builder newNodeComponentProps() {
        return new Builder();
    }

    /**
     * The Builder to create a new {@link NodeComponentProps}.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private VariableManager variableManager;

        private NodeDescription nodeDescription;

        private INodesRequestor nodesRequestor;

        private INodeDescriptionRequestor nodeDescriptionRequestor;

        private NodeContainmentKind containmentKind;

        private DiagramRenderingCache cache;

        private List<ViewCreationRequest> viewCreationRequests;

        private List<ViewDeletionRequest> viewDeletionRequests;

        private String parentElementId;

        private ViewModifier parentElementState;

        private List<String> previousTargetObjectIds;

        private IOperationValidator operationValidator;

        private List<IDiagramEvent> diagramEvents;

        private List<INodeAppearanceHandler> nodeAppearanceHandlers;

        public Builder variableManager(VariableManager variableManager) {
            this.variableManager = Objects.requireNonNull(variableManager);
            return this;
        }

        public Builder nodeDescription(NodeDescription nodeDescription) {
            this.nodeDescription = Objects.requireNonNull(nodeDescription);
            return this;
        }

        public Builder nodesRequestor(INodesRequestor nodesRequestor) {
            this.nodesRequestor = Objects.requireNonNull(nodesRequestor);
            return this;
        }

        public Builder nodeDescriptionRequestor(INodeDescriptionRequestor nodeDescriptionRequestor) {
            this.nodeDescriptionRequestor = Objects.requireNonNull(nodeDescriptionRequestor);
            return this;
        }

        public Builder containmentKind(NodeContainmentKind containmentKind) {
            this.containmentKind = Objects.requireNonNull(containmentKind);
            return this;
        }

        public Builder cache(DiagramRenderingCache cache) {
            this.cache = Objects.requireNonNull(cache);
            return this;
        }

        public Builder viewCreationRequests(List<ViewCreationRequest> viewCreationRequests) {
            this.viewCreationRequests = Objects.requireNonNull(viewCreationRequests);
            return this;
        }

        public Builder viewDeletionRequests(List<ViewDeletionRequest> viewDeletionRequests) {
            this.viewDeletionRequests = Objects.requireNonNull(viewDeletionRequests);
            return this;
        }

        public Builder parentElementId(String parentElementId) {
            this.parentElementId = Objects.requireNonNull(parentElementId);
            return this;
        }

        public Builder parentElementState(ViewModifier parentElementState) {
            this.parentElementState = Objects.requireNonNull(parentElementState);
            return this;
        }

        public Builder previousTargetObjectIds(List<String> previousTargetObjectIds) {
            this.previousTargetObjectIds = Objects.requireNonNull(previousTargetObjectIds);
            return this;
        }

        public Builder operationValidator(IOperationValidator operationValidator) {
            this.operationValidator = Objects.requireNonNull(operationValidator);
            return this;
        }

        public Builder diagramEvents(List<IDiagramEvent> diagramEvents) {
            this.diagramEvents = Objects.requireNonNull(diagramEvents);
            return this;
        }

        public Builder nodeAppearanceHandlers(List<INodeAppearanceHandler> nodeAppearanceHandlers) {
            this.nodeAppearanceHandlers = Objects.requireNonNull(nodeAppearanceHandlers);
            return this;
        }

        public NodeComponentProps build() {
            NodeComponentProps nodeComponentProps = new NodeComponentProps();
            nodeComponentProps.variableManager = Objects.requireNonNull(this.variableManager);
            nodeComponentProps.nodeDescription = Objects.requireNonNull(this.nodeDescription);
            nodeComponentProps.nodesRequestor = Objects.requireNonNull(this.nodesRequestor);
            nodeComponentProps.nodeDescriptionRequestor = Objects.requireNonNull(this.nodeDescriptionRequestor);
            nodeComponentProps.containmentKind = Objects.requireNonNull(this.containmentKind);
            nodeComponentProps.cache = Objects.requireNonNull(this.cache);
            nodeComponentProps.viewCreationRequests = Objects.requireNonNull(this.viewCreationRequests);
            nodeComponentProps.viewDeletionRequests = Objects.requireNonNull(this.viewDeletionRequests);
            nodeComponentProps.parentElementId = Objects.requireNonNull(this.parentElementId);
            nodeComponentProps.previousTargetObjectIds = Objects.requireNonNull(this.previousTargetObjectIds);
            nodeComponentProps.diagramEvents = Objects.requireNonNull(this.diagramEvents);
            nodeComponentProps.operationValidator = Objects.requireNonNull(this.operationValidator);
            nodeComponentProps.parentElementState = Objects.requireNonNull(this.parentElementState);
            nodeComponentProps.nodeAppearanceHandlers = Objects.requireNonNull(this.nodeAppearanceHandlers);
            return nodeComponentProps;
        }
    }

}
