/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.freediagram.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IObjectService;

/**
 * Free diagram service.
 *
 * @author hmarchadour
 */
public class FreeDiagramService {

    private final IObjectService objectService;

    public FreeDiagramService(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    public String getIcon(VariableManager variableManager) {
        // @formatter:off
        return variableManager.get(VariableManager.SELF, EObject.class)
                .map(this.objectService::getImagePath)
                .orElse(""); //$NON-NLS-1$
        // @formatter:on
    }

    public boolean existOnDiagram(Diagram diagram, Object semanticObject) {
        String semanticObjectId = this.objectService.getId(semanticObject);
        return this.isSemanticObjectInDiagram(semanticObjectId, diagram);
    }

    public Status insertNode(VariableManager variableManager, NodeDescription nodeDescription) {
        Status result = Status.ERROR;
        Optional<IDiagramContext> optionalDiagramContext = variableManager.get(IDiagramContext.DIAGRAM_CONTEXT, IDiagramContext.class);
        Optional<Object> optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
        if (optionalDiagramContext.isPresent() && optionalSelf.isPresent()) {
            IDiagramContext diagramContext = optionalDiagramContext.get();
            Object eObject = optionalSelf.get();
            Optional<Node> parentNode = variableManager.get(NodeDescription.NODE_CONTAINER, Node.class);
            Node newNode = this.createNode(eObject, nodeDescription);
            Diagram newDiagram = this.insertNode(diagramContext.getDiagram(), parentNode, newNode);
            diagramContext.update(newDiagram);
            result = Status.OK;
        }
        return result;
    }

    public Status removeNode(VariableManager variableManager) {
        Status result = Status.ERROR;
        Optional<IDiagramContext> optionalDiagramContext = variableManager.get(IDiagramContext.DIAGRAM_CONTEXT, IDiagramContext.class);
        if (optionalDiagramContext.isPresent()) {
            IDiagramContext diagramContext = optionalDiagramContext.get();
            Optional<Node> optionalNode = variableManager.get(NodeDescription.NODE, Node.class);
            if (optionalNode.isPresent()) {
                Node nodeToRemove = optionalNode.get();
                Diagram newDiagram = this.removeNode(diagramContext.getDiagram(), nodeToRemove);
                diagramContext.update(newDiagram);
                result = Status.OK;
            }
        }
        return result;
    }

    private Diagram removeNode(Diagram diagram, Node nodeToRemove) {
        List<Node> nodes = diagram.getNodes();
        if (nodes.contains(nodeToRemove)) {
            List<Node> newNodes = new ArrayList<>(nodes);
            newNodes.remove(nodeToRemove);
            return this.fork(diagram, builder -> builder.nodes(newNodes));
        } else {
            UnaryOperator<Node> removeFn = toFork -> this.removeChildNode(toFork, nodeToRemove);
            Predicate<Node> applyRemove = candidate -> candidate.getChildNodes().contains(nodeToRemove) || candidate.getBorderNodes().contains(nodeToRemove);
            return this.recursiveFork(diagram, applyRemove, removeFn);
        }
    }

    private Node removeChildNode(Node parentNode, Node nodeToRemove) {
        if (nodeToRemove.isBorderNode()) {
            List<Node> newBorderNodes = new ArrayList<>(parentNode.getBorderNodes());
            newBorderNodes.remove(nodeToRemove);
            return this.fork(parentNode, builder -> builder.borderNodes(newBorderNodes));
        } else {
            List<Node> newChildNodes = new ArrayList<>(parentNode.getChildNodes());
            newChildNodes.remove(nodeToRemove);
            return this.fork(parentNode, builder -> builder.childNodes(newChildNodes));
        }
    }

    private Diagram insertNode(Diagram diagram, Optional<Node> optionalParentNode, Node newNode) {
        if (optionalParentNode.isPresent()) {
            Node parentNode = optionalParentNode.get();
            UnaryOperator<Node> insertFn = toFork -> this.insertChildNode(toFork, newNode);
            Predicate<Node> applyInsert = candidate -> Objects.equals(candidate, parentNode);
            return this.recursiveFork(diagram, applyInsert, insertFn);
        } else {
            List<Node> nodes = new ArrayList<>(diagram.getNodes());
            nodes.add(newNode);
            return this.fork(diagram, builder -> builder.nodes(nodes));
        }
    }

    private Node insertChildNode(Node parentNode, Node nodeToInsert) {
        if (nodeToInsert.isBorderNode()) {
            List<Node> newBorderNodes = new ArrayList<>(parentNode.getBorderNodes());
            newBorderNodes.add(nodeToInsert);
            return this.fork(parentNode, builder -> builder.borderNodes(newBorderNodes));
        } else {
            List<Node> newChildNodes = new ArrayList<>(parentNode.getChildNodes());
            newChildNodes.add(nodeToInsert);
            return this.fork(parentNode, builder -> builder.childNodes(newChildNodes));
        }
    }

    private Diagram recursiveFork(Diagram diagram, Predicate<Node> applyFork, UnaryOperator<Node> forkFn) {
        // @formatter:off
        List<Node> nodes = diagram.getNodes()
                .stream()
                .map(node -> this.recursiveFork(node, applyFork, forkFn))
                .collect(Collectors.toList());
        // @formatter:on
        return this.fork(diagram, builder -> builder.nodes(nodes));
    }

    private Node recursiveFork(Node current, Predicate<Node> applyFork, UnaryOperator<Node> forkFn) {
        if (applyFork.test(current)) {
            return forkFn.apply(current);
        } else {
            // @formatter:off
            List<Node> borderNodes = current.getBorderNodes()
                    .stream()
                    .map(borderNode -> this.recursiveFork(borderNode, applyFork, forkFn))
                    .collect(Collectors.toList());
            List<Node> childNodes = current.getChildNodes()
                    .stream()
                    .map(childNode -> this.recursiveFork(childNode, applyFork, forkFn))
                    .collect(Collectors.toList());
            return this.fork(current, builder -> builder.borderNodes(borderNodes).childNodes(childNodes));
            // @formatter:on
        }
    }

    private Diagram fork(Diagram diagram, Consumer<org.eclipse.sirius.web.diagrams.Diagram.Builder> forkFn) {
        var builder = Diagram.newDiagram(diagram);
        forkFn.accept(builder);
        return builder.build();
    }

    private Node fork(Node node, Consumer<org.eclipse.sirius.web.diagrams.Node.Builder> forkFn) {
        var builder = Node.newNode(node);
        forkFn.accept(builder);
        return builder.build();
    }

    private Node createNode(Object self, NodeDescription nodeDescription) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, self);
        String nodeId = nodeDescription.getIdProvider().apply(variableManager);
        String type = nodeDescription.getTypeProvider().apply(variableManager);
        String targetObjectId = nodeDescription.getTargetObjectIdProvider().apply(variableManager);
        String targetObjectKind = nodeDescription.getTargetObjectKindProvider().apply(variableManager);
        String targetObjectLabel = nodeDescription.getTargetObjectLabelProvider().apply(variableManager);
        INodeStyle style = nodeDescription.getStyleProvider().apply(variableManager);

        VariableManager nodeVariableManager = variableManager.createChild();
        nodeVariableManager.put(LabelDescription.OWNER_ID, nodeId);
        Label label = this.createLabel(nodeVariableManager, nodeDescription.getLabelDescription());
        // @formatter:off
        return Node.newNode(nodeId)
                .type(type)
                .targetObjectId(targetObjectId)
                .targetObjectKind(targetObjectKind)
                .targetObjectLabel(targetObjectLabel)
                .descriptionId(nodeDescription.getId())
                .borderNode(false)
                .label(label)
                .style(style)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .borderNodes(List.of())
                .childNodes(List.of())
                .build();
        // @formatter:on
    }

    private Label createLabel(VariableManager variableManager, LabelDescription labelDescription) {
        String labelId = labelDescription.getIdProvider().apply(variableManager);
        String text = labelDescription.getTextProvider().apply(variableManager);

        LabelStyleDescription labelStyleDescription = labelDescription.getStyleDescription();

        String color = labelStyleDescription.getColorProvider().apply(variableManager);
        Integer fontSize = labelStyleDescription.getFontSizeProvider().apply(variableManager);
        Boolean bold = labelStyleDescription.getBoldProvider().apply(variableManager);
        Boolean italic = labelStyleDescription.getItalicProvider().apply(variableManager);
        Boolean strikeThrough = labelStyleDescription.getStrikeThroughProvider().apply(variableManager);
        Boolean underline = labelStyleDescription.getUnderlineProvider().apply(variableManager);
        String iconURL = labelStyleDescription.getIconURLProvider().apply(variableManager);

        // @formatter:off
        var labelStyle = LabelStyle.newLabelStyle()
                .color(color)
                .fontSize(fontSize)
                .bold(bold)
                .italic(italic)
                .strikeThrough(strikeThrough)
                .underline(underline)
                .iconURL(iconURL)
                .build();
        // @formatter:on

        // @formatter:off
        return Label.newLabel(labelId)
                .type("label:inside-center") //$NON-NLS-1$
                .text(text)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .alignment(Position.UNDEFINED)
                .style(labelStyle)
                .build();
        // @formatter:on
    }

    /**
     * For a given diagram, this method checks if at least one node of the diagram is binded to a given semantic object.
     *
     * @param semanticObjectId
     *            The identifier of the semantic object we are looking for.
     * @param diagram
     *            the diagram.
     * @return true if the semantic object is associated with a node in the the diagram, false otherwise.
     */
    private boolean isSemanticObjectInDiagram(String semanticObjectId, Diagram diagram) {
        return this.isSemanticObjectInNodes(semanticObjectId, diagram.getNodes());
    }

    private boolean isSemanticObjectInNodes(String semanticObjectId, List<Node> nodes) {
        boolean found = false;
        Iterator<Node> it = nodes.iterator();
        while (!found && it.hasNext()) {
            Node node = it.next();
            found = semanticObjectId.equals(node.getTargetObjectId());
            if (!found) {
                found = this.isSemanticObjectInNodes(semanticObjectId, node.getBorderNodes());
            }
            if (!found) {
                found = this.isSemanticObjectInNodes(semanticObjectId, node.getChildNodes());
            }
        }
        return found;
    }
}
