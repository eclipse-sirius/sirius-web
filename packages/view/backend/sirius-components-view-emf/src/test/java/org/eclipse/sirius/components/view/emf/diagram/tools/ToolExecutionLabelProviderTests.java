/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramVariables;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.DiagramStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LabelVisibility;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.components.BorderNodePosition;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.messages.ViewEMFMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.StaticMessageSource;

/**
 * Tests for {@link ToolExecutionLabelProvider}.
 *
 * @author gdaniel
 */
public class ToolExecutionLabelProviderTests {

    private static final String EDITING_CONTEXT_ID = "editingContextId";

    private static final String DIAGRAM_ID = "diagramId";

    private static final String DIAGRAM_LABEL = "Diagram label";

    @Test
    public void givenSelectedElementsWhenTheLabelIsComputedThenTheirNonBlankLabelsAreKeptInOrder() {
        var variableManager = this.createVariableManager();
        variableManager.put(DiagramVariables.SELECTED_NODE.name(), this.createNode("first", "First"));
        variableManager.put(DiagramVariables.SELECTED_NODES.name(), List.of(
                this.createNode("blank", ""),
                this.createNode("second", "Second"),
                this.createNode("third", "Third")
        ));

        String label = this.createToolExecutionLabelProvider().getLabel(this.createTool("Rename"), variableManager);

        assertThat(label).isEqualTo("Used \"Rename\" on the First, Second, Third");
    }

    @Test
    public void givenOnlyUnlabelledSelectedElementsWhenTheLabelIsComputedThenTheDiagramLabelIsUsed() {
        var variableManager = this.createVariableManager();
        variableManager.put(DiagramVariables.SELECTED_NODE.name(), this.createNode("first", ""));
        variableManager.put(DiagramVariables.SELECTED_NODES.name(), List.of(this.createNode("second", " ")));

        String label = this.createToolExecutionLabelProvider().getLabel(this.createTool("Delete"), variableManager);

        assertThat(label).isEqualTo("Used \"Delete\" on the Diagram label");
    }

    @Test
    public void givenNoExecutionContextWhenTheLabelIsComputedThenTheTargetIsUnknown() {
        String label = this.createToolExecutionLabelProvider().getLabel(this.createTool("Create"), new VariableManager());

        assertThat(label).isEqualTo("Used \"Create\" on unknown target");
    }

    @Test
    public void givenAnUnnamedToolWhenTheLabelIsComputedThenItsClassNameIsUsed() {
        Tool tool = this.createTool(null);

        String label = this.createToolExecutionLabelProvider().getLabel(tool, new VariableManager());

        assertThat(label).isEqualTo("Used \"" + tool.getClass().getSimpleName() + "\" on unknown target");
    }

    private ToolExecutionLabelProvider createToolExecutionLabelProvider() {
        var metadata = new RepresentationMetadata(DIAGRAM_ID, Diagram.KIND, DIAGRAM_LABEL, "diagramDescriptionId", List.of());
        IRepresentationMetadataProvider representationMetadataProvider = (editingContextId, representationId) -> Optional.of(metadata);

        var messageSource = new StaticMessageSource();
        messageSource.addMessage("USED_TOOL_ON_TARGET_LABEL", Locale.ENGLISH, "Used \"{0}\" on the {1}");
        messageSource.addMessage("USED_TOOL_UNKNOWN_TARGET_LABEL", Locale.ENGLISH, "Used \"{0}\" on unknown target");
        var messageService = new ViewEMFMessageService(new MessageSourceAccessor(messageSource, Locale.ENGLISH));

        return new ToolExecutionLabelProvider(List.of(representationMetadataProvider), messageService);
    }

    private VariableManager createVariableManager() {
        var diagram = Diagram.newDiagram(DIAGRAM_ID)
                .targetObjectId("targetObjectId")
                .descriptionId("diagramDescriptionId")
                .nodes(List.of())
                .edges(List.of())
                .style(DiagramStyle.newDiagramStyle().build())
                .build();
        IEditingContext editingContext = () -> EDITING_CONTEXT_ID;

        var variableManager = new VariableManager();
        variableManager.put(CoreVariables.EDITING_CONTEXT.name(), editingContext);
        variableManager.put(DiagramVariables.DIAGRAM_CONTEXT.name(), new DiagramContext(diagram));
        return variableManager;
    }

    private Tool createTool(String name) {
        var tool = DiagramFactory.eINSTANCE.createNodeTool();
        tool.setName(name);
        return tool;
    }

    private Node createNode(String id, String label) {
        var style = RectangularNodeStyle.newRectangularNodeStyle()
                .background("white")
                .borderColor("black")
                .borderSize(1)
                .borderRadius(0)
                .borderStyle(LineStyle.Solid)
                .childrenLayoutStrategy(new FreeFormLayoutStrategy())
                .build();

        return Node.newNode(id)
                .type(NodeType.NODE_RECTANGLE)
                .targetObjectId(id)
                .targetObjectKind("")
                .targetObjectLabel(label)
                .descriptionId("nodeDescriptionId")
                .initialBorderNodePosition(BorderNodePosition.EAST)
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .collapsingState(CollapsingState.EXPANDED)
                .insideLabel(this.createInsideLabel(id, label))
                .style(style)
                .borderNodes(List.of())
                .childNodes(List.of())
                .customizedStyleProperties(Set.of())
                .decorators(List.of())
                .build();
    }

    private InsideLabel createInsideLabel(String id, String text) {
        var style = LabelStyle.newLabelStyle()
                .color("black")
                .fontSize(14)
                .iconURL(List.of())
                .background("transparent")
                .borderColor("transparent")
                .borderStyle(LineStyle.Solid)
                .visibility(LabelVisibility.visible)
                .build();

        return InsideLabel.newLabel(id + "Label")
                .text(text)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .style(style)
                .isHeader(false)
                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.NEVER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .customizedStyleProperties(Set.of())
                .build();
    }
}
