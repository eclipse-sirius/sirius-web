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
package org.eclipse.sirius.web.application.diagram.services.filter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.HideDiagramElementInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.diagram.services.filter.api.IDiagramFilterActionContributionProvider;
import org.eclipse.sirius.web.application.diagram.services.filter.api.IDiagramFilterHelper;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Provides the description of the hide button for the diagram filter's split button.
 *
 * @author gdaniel
 */
@Service
public class HideButtonDescriptionProvider implements IDiagramFilterActionContributionProvider {

    private final IObjectService objectService;

    private final IDiagramFilterHelper diagramFilterHelper;

    private final IMessageService messageService;

    public HideButtonDescriptionProvider(IObjectService objectService, IDiagramFilterHelper diagramFilterHelper, IMessageService messageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramFilterHelper = Objects.requireNonNull(diagramFilterHelper);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public ButtonDescription getButtonDescription() {
        return ButtonDescription.newButtonDescription("diagram-filter/split-button/hide")
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .labelProvider(variableManager -> "Hide")
                .iconURLProvider(variableManager -> List.of())
                .isReadOnlyProvider(variableManager -> false)
                .buttonLabelProvider(variableManager -> "Hide")
                .imageURLProvider(variableManager -> "/icons/full/obj16/HideTool.svg")
                .pushButtonHandler(variableManager -> {
                    var diagram = variableManager.get(DiagramFilterDescriptionProvider.DIAGRAM, Diagram.class).get();
                    var nodeIds = this.diagramFilterHelper.getSelectedElementIds(variableManager);
                    var editingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).get();
                    return this.diagramFilterHelper.sendDiagramEvent(variableManager, new HideDiagramElementInput(UUID.randomUUID(), editingContext.getId(), diagram.getId(), nodeIds, true));
                })
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(variableManager -> "")
                .messageProvider(variableManager -> "")
                .styleProvider(variableManager -> ButtonStyle.newButtonStyle()
                        .backgroundColor("#ffffff")
                        .foregroundColor("#261E58")
                        .build()
                )
                .helpTextProvider(variableManager -> this.messageService.hideSelectedElements())
                .build();
    }

}
