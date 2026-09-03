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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.dto.PinDiagramElementInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.web.application.diagram.services.filter.api.IDiagramFilterActionContributionProvider;
import org.eclipse.sirius.web.application.diagram.services.filter.api.IDiagramFilterHelper;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Provides the description of the unpin button for the diagram filter's split button.
 *
 * @author gdaniel
 */
@Service
public class UnpinButtonDescription implements IDiagramFilterActionContributionProvider {

    private final IIdentityService identityService;

    private final IDiagramFilterHelper diagramFilterHelper;

    private final IMessageService messageService;

    public UnpinButtonDescription(IIdentityService identityService, IDiagramFilterHelper diagramFilterHelper, IMessageService messageService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.diagramFilterHelper = Objects.requireNonNull(diagramFilterHelper);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public ButtonDescription getButtonDescription() {
        return ButtonDescription.newButtonDescription("diagram-filter/split-button/unpin")
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(variableManager -> variableManager.get(RepresentationVariables.SELF.name(), Object.class).map(this.identityService::getId).orElse(null))
                .labelProvider(variableManager -> this.messageService.diagramFilterUnpinElements())
                .iconURLProvider(variableManager -> List.of())
                .isReadOnlyProvider(variableManager -> false)
                .buttonLabelProvider(variableManager -> this.messageService.diagramFilterUnpinElements())
                .imageURLProvider(variableManager -> DiagramImageConstants.UNPIN_SVG)
                .pushButtonHandler(variableManager -> {
                    var diagram = variableManager.get(DiagramFilterDescriptionProvider.DIAGRAM, Diagram.class).get();
                    var nodeIds = this.diagramFilterHelper.getSelectedElementIds(variableManager);
                    var editingContext = variableManager.get(CoreVariables.EDITING_CONTEXT.name(), IEditingContext.class).get();
                    return this.diagramFilterHelper.sendDiagramEvent(variableManager, new PinDiagramElementInput(UUID.randomUUID(), editingContext.getId(), diagram.getId(), nodeIds, false));
                })
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(variableManager -> "")
                .messageProvider(variableManager -> "")
                .styleProvider(variableManager -> ButtonStyle.newButtonStyle()
                        .backgroundColor("#ffffff")
                        .foregroundColor("#261E58")
                        .build()
                )
                .helpTextProvider(variableManager -> this.messageService.unpinSelectedElements())
                .build();
    }

}
