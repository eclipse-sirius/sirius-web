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
package org.eclipse.sirius.web.spring.collaborative.diagrams.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.sirius.web.collaborative.api.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.collaborative.diagrams.api.DiagramCreationParameters;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.tests.TestDiagramBuilder;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Tests of the create representation event handler.
 *
 * @author sbegaudeau
 */
public class CreateDiagramEventHandlerTestCases {
    @Test
    public void testDiagramCreation() {
        IRepresentationDescriptionService representationDescriptionService = new NoOpRepresentationDescriptionService() {
            @Override
            public Optional<IRepresentationDescription> findRepresentationDescriptionById(UUID id) {
                // @formatter:off
                DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(UUID.randomUUID())
                        .label("label") //$NON-NLS-1$
                        .idProvider(variableManager -> UUID.randomUUID())
                        .canCreatePredicate(variableManager -> Boolean.TRUE)
                        .edgeDescriptions(new ArrayList<>())
                        .labelProvider(variableManager -> "label") //$NON-NLS-1$
                        .toolSections(List.of())
                        .nodeDescriptions(new ArrayList<>())
                        .targetObjectIdProvider(variableManager -> "targetObjectId") //$NON-NLS-1$
                        .build();
                // @formatter:on

                return Optional.of(diagramDescription);
            }
        };

        AtomicBoolean hasBeenCalled = new AtomicBoolean();
        IDiagramService diagramService = new NoOpDiagramService() {
            @Override
            public Diagram create(DiagramCreationParameters parameters) {
                hasBeenCalled.set(true);
                return new TestDiagramBuilder().getDiagram(UUID.randomUUID());
            }
        };

        IObjectService objectService = new NoOpObjectService() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };

        CreateDiagramEventHandler handler = new CreateDiagramEventHandler(representationDescriptionService, new NoOpRepresentationService(), diagramService, objectService,
                new NoOpCollaborativeDiagramMessageService());

        var input = new CreateRepresentationInput(UUID.randomUUID(), UUID.randomUUID(), "objectId", "representationName"); //$NON-NLS-1$//$NON-NLS-2$
        var context = new Context(new UsernamePasswordAuthenticationToken(null, null));
        assertThat(handler.canHandle(input)).isTrue();

        handler.handle(new NoOpEditingContext(), input, context);
        assertThat(hasBeenCalled.get()).isTrue();
    }
}
