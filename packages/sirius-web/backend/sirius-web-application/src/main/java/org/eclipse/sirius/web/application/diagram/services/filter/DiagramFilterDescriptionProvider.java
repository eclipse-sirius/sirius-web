/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.application.diagram.services.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramEventProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventProcessor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.LabelDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.SplitButtonDescription;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.diagram.services.filter.api.IDiagramFilterActionContributionProvider;
import org.eclipse.sirius.web.application.diagram.services.filter.api.IDiagramFilterDescriptionProvider;
import org.eclipse.sirius.web.application.diagram.services.filter.api.IDiagramFilterHelper;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Provides a tree description listing diagram elements and allowing to perform actions on them.
 *
 * @author gdaniel
 */
@Service
public class DiagramFilterDescriptionProvider implements IDiagramFilterDescriptionProvider {

    public static final String FORM_DESCRIPTION_ID = "diagram_filter_form_description";

    public static final String SELECTED_TREE_NODES = "selectedTreeNodes";

    public static final String EDITING_CONTEXT_EVENT_PROCESSOR = "editingContextEventProcessor";

    public static final String DIAGRAM_EVENT_PROCESSOR = "diagramEventProcessor";

    public static final String DIAGRAM = "diagram";

    private static final String GROUP_DESCRIPTION_ID = "defaultDiagramFilterGroup";

    private static final String PAGE_DESCRIPTION_ID = "defaultDiagramFilterPage";

    private static final String FORM_TITLE = "Diagram Filters";

    private final IObjectService objectService;

    private final ILabelService labelService;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final List<IDiagramFilterActionContributionProvider> diagramFilterActionContributionProviders;

    private final IDiagramFilterHelper diagramFilterHelper;

    private final IMessageService messageService;

    public DiagramFilterDescriptionProvider(IObjectService objectService, ILabelService labelService, @Lazy IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, List<IDiagramFilterActionContributionProvider> diagramFilterActionContributionProviders, IDiagramFilterHelper diagramFilterHelper, IMessageService messageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.labelService = Objects.requireNonNull(labelService);
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.diagramFilterActionContributionProviders = Objects.requireNonNull(diagramFilterActionContributionProviders);
        this.diagramFilterHelper = Objects.requireNonNull(diagramFilterHelper);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public FormDescription getFormDescription() {
        List<GroupDescription> groupDescriptions = List.of(this.getGroupDescription());

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse(null);

        return FormDescription.newFormDescription(FORM_DESCRIPTION_ID)
                .label(FORM_TITLE)
                .idProvider(new GetOrCreateRandomIdProvider())
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .labelProvider(variableManager -> FORM_TITLE)
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> false)
                .variableManagerInitializer(vm -> {
                    String editingContextId = vm.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(IEditingContext::getId).orElse("");
                    Optional<RepresentationMetadata> optionalRepresentationMetadata = vm.get(VariableManager.SELF, RepresentationMetadata.class);
                    String representationId = optionalRepresentationMetadata
                            .map(RepresentationMetadata::getRepresentationMetadataId)
                            .map(UUID::toString)
                            .orElse(null);

                    IEditingContextEventProcessor editingContextEventProcessor = this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                            .filter(processor -> processor.getEditingContextId().equals(editingContextId))
                            .findFirst()
                            .orElse(null);
                    IDiagramEventProcessor diagramEventProcessor = Optional.ofNullable(editingContextEventProcessor)
                            .flatMap(processor -> processor.getRepresentationEventProcessors().stream()
                                    .filter(IDiagramEventProcessor.class::isInstance)
                                    .map(IDiagramEventProcessor.class::cast)
                                    .filter(p -> p.getRepresentation().getId().equals(representationId))
                                    .findFirst()
                            )
                            .orElse(null);
                    var optDiagram = Optional.ofNullable(diagramEventProcessor)
                            .map(IRepresentationEventProcessor::getRepresentation)
                            .filter(Diagram.class::isInstance)
                            .map(Diagram.class::cast);
                    vm.put(SELECTED_TREE_NODES, this.getCheckMap(optDiagram));
                    vm.put(EDITING_CONTEXT_EVENT_PROCESSOR, editingContextEventProcessor);
                    vm.put(DIAGRAM_EVENT_PROCESSOR, diagramEventProcessor);
                    vm.put(DIAGRAM, optDiagram.orElse(null));
                    return vm;
                })
                .pageDescriptions(List.of(this.getPageDescription(groupDescriptions)))
                .iconURLsProvider(variableManager -> List.of())
                .build();
    }

    private PageDescription getPageDescription(List<GroupDescription> groupDescriptions) {
        Function<VariableManager, String> idProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElseGet(() -> UUID.randomUUID().toString());

        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.labelService::getStyledLabel)
                .map(Object::toString)
                .orElse("");

        Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList();

        return PageDescription.newPageDescription(PAGE_DESCRIPTION_ID)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .semanticElementsProvider(semanticElementsProvider)
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(variableManager -> true)
                .build();
    }

    private GroupDescription getGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();
        controlDescriptions.add(this.createTreeLabelDescription());
        controlDescriptions.add(this.createTreeCheckboxDescription());
        controlDescriptions.add(this.createTreeDescription());
        controlDescriptions.add(this.createSplitButtonDescription());

        return GroupDescription.newGroupDescription(GROUP_DESCRIPTION_ID)
                .idProvider(variableManager -> FORM_TITLE)
                .labelProvider(variableManager -> this.messageService.diagramFilterFormTitle())
                .semanticElementsProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList())
                .controlDescriptions(controlDescriptions)
                .build();
    }

    private LabelDescription createTreeLabelDescription() {
        return LabelDescription.newLabelDescription("diagram-filter/tree-label")
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .idProvider(new WidgetIdProvider())
                .labelProvider(variableManager -> "")
                .valueProvider(variableManager -> this.messageService.diagramFilterElementsOnDiagram())
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(variableManager -> "")
                .messageProvider(variableManager -> "")
                .styleProvider(variableManager -> null)
                .build();
    }

    private CheckboxDescription createTreeCheckboxDescription() {
        return CheckboxDescription.newCheckboxDescription("diagram-filter/tree-checkbox")
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .idProvider(variableManager -> "TreeCheckBox")
                .labelProvider(variableManager -> {
                    int selectedElementCount = this.diagramFilterHelper.getSelectedElementIds(variableManager).size();
                    if (selectedElementCount == 0) {
                        return this.messageService.diagramFilterElementsSelected(selectedElementCount);
                    }
                    return this.messageService.diagramFilterElementsSelectedPlural(selectedElementCount);
                })
                .iconURLProvider(variableManager -> List.of())
                .isReadOnlyProvider(variableManager -> false)
                .valueProvider(variableManager -> this.diagramFilterHelper.getSelectedElementIds(variableManager).size() > 0)
                .newValueHandler((variableManager, newValue) -> {
                    Map<String, Boolean> checkMap = variableManager.get(SELECTED_TREE_NODES, Map.class).get();
                    checkMap.entrySet().forEach(entry -> entry.setValue(newValue));
                    return new Success();
                })
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(variableManager -> "")
                .messageProvider(variableManager -> "")
                .styleProvider(variableManager -> null)
                .build();
    }

    private TreeDescription createTreeDescription() {
        return TreeDescription.newTreeDescription("diagram-filter/tree")
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(variableManager -> "")
                .messageProvider(variableManager -> "")
                .labelProvider(variableManager -> "")
                .nodeIdProvider(this::getNodeId)
                .nodeLabelProvider(this::getNodeLabel)
                .nodeIconURLProvider(vm -> List.of())
                .nodeEndIconsURLProvider(this::computeNodeEndIcons)
                .nodeKindProvider(vm -> "")
                .nodeSelectableProvider(vm -> false)
                .childrenProvider(this::getNodeChildren)
                .expandedNodeIdsProvider(vm -> List.of())
                .isCheckableProvider(variableManager -> true)
                .checkedValueProvider(variableManager -> this.diagramFilterHelper.getSelectedElementIds(variableManager).contains(this.getNodeId(variableManager)))
                .newCheckedValueHandler((variableManager, newValue) -> {
                    Map<String, Boolean> selectedTreeNodes = variableManager.get(SELECTED_TREE_NODES, Map.class).get();
                    String selfId = this.getNodeId(variableManager);
                    selectedTreeNodes.put(selfId, newValue);
                    return new Success();
                })
                .build();
    }

    private SplitButtonDescription createSplitButtonDescription() {
        return SplitButtonDescription.newSplitButtonDescription("diagram-filter/split-button")
                .idProvider(variableManager -> "TreeSplitButton")
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .labelProvider(variableManager -> {
                    int selectedElementCount = this.diagramFilterHelper.getSelectedElementIds(variableManager).size();
                    if (selectedElementCount <= 1) {
                        return this.messageService.diagramFilterApplyTo(selectedElementCount);
                    }
                    return this.messageService.diagramFilterApplyToPlural(selectedElementCount);
                })
                .diagnosticsProvider(variableManager -> List.of())
                .iconURLProvider(variableManager -> List.of())
                .isReadOnlyProvider(variableManager -> false)
                .kindProvider(variableManager -> "")
                .messageProvider(variableManager -> "")
                .actions(this.diagramFilterActionContributionProviders.stream()
                        .map(IDiagramFilterActionContributionProvider::getButtonDescription)
                        .toList()
                )
                .build();

    }

    private String getNodeId(VariableManager vm) {
        var self = vm.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof Node node) {
            return node.getId();
        } else {
            return "";
        }
    }

    private String getNodeLabel(VariableManager vm) {
        var self = vm.get(VariableManager.SELF, Object.class).orElse(null);
        String result = "";
        if (self instanceof Node node) {
            if (node.getInsideLabel() != null) {
                result = node.getInsideLabel().getText();
            } else if (!node.getOutsideLabels().isEmpty()) {
                result = node.getOutsideLabels().get(0).text();
            } else if (node.getTargetObjectLabel() != null) {
                result = node.getTargetObjectLabel();
            }
        }
        return result;
    }

    private List<Node> getNodeChildren(VariableManager vm) {
        var self = vm.get(VariableManager.SELF, Object.class).orElse(null);
        List<Node> result = new ArrayList<>();
        if (self instanceof RepresentationMetadata) {
            var diagramEventProcessor = vm.get(DIAGRAM_EVENT_PROCESSOR, DiagramEventProcessor.class).orElse(null);
            if (diagramEventProcessor != null && diagramEventProcessor.getRepresentation() instanceof Diagram diagram) {
                result = diagram.getNodes();
            }
        } else if (self instanceof Node node) {
            result = node.getChildNodes();
        } else {
            result = List.of();
        }
        return result;
    }

    private List<List<String>> computeNodeEndIcons(VariableManager vm) {
        var node = vm.get(VariableManager.SELF, Object.class)
                .filter(Node.class::isInstance)
                .map(Node.class::cast)
                .orElse(null);
        List<List<String>> result = new ArrayList<>();
        if (node.getModifiers().contains(ViewModifier.Hidden)) {
            result.add(List.of("/icons/full/obj16/HideTool.svg"));
        }
        if (node.getModifiers().contains(ViewModifier.Faded)) {
            result.add(List.of(DiagramImageConstants.FADE_SVG));
        }
        if (node.getCollapsingState().equals(CollapsingState.COLLAPSED)) {
            result.add(List.of(DiagramImageConstants.COLLAPSE_SVG));
        }
        if (node.isPinned()) {
            result.add(List.of(DiagramImageConstants.PIN_SVG));
        }
        return result;
    }

    private Map<String, Boolean> getCheckMap(Optional<Diagram> optDiagram) {
        Map<String, Boolean> checkMap = new HashMap<>();
        if (optDiagram.isPresent()) {
            var diagram = optDiagram.get();
            for (Node node : diagram.getNodes()) {
                this.fillCheckMap(node, checkMap);
            }
        }
        return checkMap;
    }

    private void fillCheckMap(Node node, Map<String, Boolean> checkMap) {
        checkMap.put(node.getId(), false);
        Stream.concat(node.getChildNodes().stream(), node.getBorderNodes().stream())
                .forEach(n -> this.fillCheckMap(n, checkMap));
    }
}
