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
package org.eclipse.sirius.components.collaborative.diagrams.configuration;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.springframework.stereotype.Service;

/**
 * Bundles the common dependencies that most event handler needs into a single object for convenience.
 *
 * @author frouene
 */
@Service
public class DiagramEventHandlerConfiguration {

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final IFeedbackMessageService feedbackMessageService;

    public DiagramEventHandlerConfiguration(IObjectService objectService, IDiagramQueryService diagramQueryService, IDiagramDescriptionService diagramDescriptionService, IRepresentationDescriptionSearchService representationDescriptionSearchService, ICollaborativeDiagramMessageService messageService, IFeedbackMessageService feedbackMessageService) {
        this.objectService = objectService;
        this.diagramQueryService = diagramQueryService;
        this.diagramDescriptionService = diagramDescriptionService;
        this.representationDescriptionSearchService = representationDescriptionSearchService;
        this.messageService = messageService;
        this.feedbackMessageService = feedbackMessageService;
    }

    public IObjectService getObjectService() {
        return this.objectService;
    }

    public IDiagramQueryService getDiagramQueryService() {
        return this.diagramQueryService;
    }

    public IDiagramDescriptionService getDiagramDescriptionService() {
        return this.diagramDescriptionService;
    }

    public IRepresentationDescriptionSearchService getRepresentationDescriptionSearchService() {
        return this.representationDescriptionSearchService;
    }

    public ICollaborativeDiagramMessageService getMessageService() {
        return this.messageService;
    }

    public IFeedbackMessageService getFeedbackMessageService() {
        return this.feedbackMessageService;
    }
}
