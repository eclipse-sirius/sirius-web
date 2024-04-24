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
import org.eclipse.sirius.components.collaborative.diagrams.dto.FadeDiagramElementInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.services.diagramfilter.api.IDiagramFilterActionContributionProvider;
import org.eclipse.sirius.web.services.diagramfilter.api.IDiagramFilterHelper;
import org.eclipse.sirius.web.services.messages.IServicesMessageService;
import org.springframework.stereotype.Service;

/**
 * Provides the description of the reveal faded elements button for the diagram filter's split button.
 *
 * @author gdaniel
 */
@Service
public class RevealFadedElementsButtonDescriptionProvider implements IDiagramFilterActionContributionProvider {

    private final IObjectService objectService;

    private final IDiagramFilterHelper diagramFilterHelper;

    private final IServicesMessageService messageService;

    public RevealFadedElementsButtonDescriptionProvider(IObjectService objectService, IDiagramFilterHelper diagramFilterHelper, IServicesMessageService messageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramFilterHelper = Objects.requireNonNull(diagramFilterHelper);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public ButtonDescription getButtonDescription() {
        return ButtonDescription.newButtonDescription("diagram-filter/split-button/reveal-faded-elements")
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .labelProvider(variableManager -> "Reveal faded elements")
                .iconURLProvider(variableManager -> List.of())
                .isReadOnlyProvider(variableManager -> false)
                .buttonLabelProvider(variableManager -> "Reveal faded elements")
                .imageURLProvider(variableManager -> DiagramImageConstants.REVEAL_FADED_ELEMENTS_SVG)
                .pushButtonHandler(variableManager -> {
                    var diagram = variableManager.get(DiagramFilterDescriptionProvider.DIAGRAM, Diagram.class).get();
                    var nodeIds = this.diagramFilterHelper.getSelectedElementIds(variableManager);
                    var editingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).get();
                    return this.diagramFilterHelper.sendDiagramEvent(variableManager, new FadeDiagramElementInput(UUID.randomUUID(), editingContext.getId(), diagram.getId(), nodeIds, false));
                })
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(variableManager -> "")
                .messageProvider(variableManager -> "")
                .styleProvider(variableManager -> ButtonStyle.newButtonStyle()
                        .backgroundColor("#ffffff")
                        .foregroundColor("#261E58")
                        .build()
                )
                .helpTextProvider(variableManager -> this.messageService.revealSelectedFadedElements())
                .build();
    }

}
