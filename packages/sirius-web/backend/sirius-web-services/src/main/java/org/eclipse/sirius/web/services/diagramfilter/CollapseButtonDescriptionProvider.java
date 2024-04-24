/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.services.diagramfilter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.dto.UpdateCollapsingStateInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.services.diagramfilter.api.IDiagramFilterActionContributionProvider;
import org.eclipse.sirius.web.services.diagramfilter.api.IDiagramFilterHelper;
import org.eclipse.sirius.web.services.messages.IServicesMessageService;
import org.springframework.stereotype.Service;

/**
 * Provides the description of the collapse button for the diagram filter's split button.
 *
 * @author gdaniel
 */
@Service
public class CollapseButtonDescriptionProvider implements IDiagramFilterActionContributionProvider {

    private final IObjectService objectService;

    private final IDiagramFilterHelper diagramFilterHelper;

    private final IServicesMessageService messageService;

    public CollapseButtonDescriptionProvider(IObjectService objectService, IDiagramFilterHelper diagramFilterHelper, IServicesMessageService messageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramFilterHelper = Objects.requireNonNull(diagramFilterHelper);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public ButtonDescription getButtonDescription() {
        return ButtonDescription.newButtonDescription("diagram-filter/split-button/collapse")
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .labelProvider(variableManager -> "Collapse")
                .iconURLProvider(variableManager -> List.of())
                .isReadOnlyProvider(variableManager -> false)
                .buttonLabelProvider(variableManager -> "Collapse")
                .imageURLProvider(variableManager -> DiagramImageConstants.COLLAPSE_SVG)
                .pushButtonHandler(variableManager -> {
                    var diagram = variableManager.get(DiagramFilterDescriptionProvider.DIAGRAM, Diagram.class).get();
                    var nodeIds = this.diagramFilterHelper.getSelectedElementIds(variableManager);
                    var editingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).get();
                    boolean hasFailure = nodeIds.stream()
                            .map(nodeId -> this.diagramFilterHelper.sendDiagramEvent(variableManager, new UpdateCollapsingStateInput(UUID.randomUUID(), editingContext.getId(), diagram.getId(), nodeId, CollapsingState.COLLAPSED)))
                            .anyMatch(Failure.class::isInstance);
                    if (hasFailure) {
                        return new Failure("An error occurred");
                    } else {
                        return new Success();
                    }
                })
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(variableManager -> "")
                .messageProvider(variableManager -> "")
                .styleProvider(variableManager -> ButtonStyle.newButtonStyle()
                        .backgroundColor("#ffffff")
                        .foregroundColor("#261E58")
                        .build()
                )
                .helpTextProvider(variableManager -> this.messageService.collapseSelectedElements())
                .build();
    }

}
