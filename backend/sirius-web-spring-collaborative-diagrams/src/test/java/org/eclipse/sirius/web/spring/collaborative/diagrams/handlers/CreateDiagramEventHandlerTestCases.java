/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.tests.TestDiagramBuilder;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
import org.junit.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

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
        IDiagramCreationService diagramCreationService = new NoOpDiagramCreationService() {
            @Override
            public Diagram create(String label, Object targetObject, DiagramDescription diagramDescription, IEditingContext editingContext) {
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

        CreateDiagramEventHandler handler = new CreateDiagramEventHandler(representationDescriptionService, new NoOpRepresentationService(), diagramCreationService, objectService,
                new NoOpCollaborativeDiagramMessageService(), new SimpleMeterRegistry());

        var input = new CreateRepresentationInput(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "objectId", "representationName"); //$NON-NLS-1$//$NON-NLS-2$
        assertThat(handler.canHandle(input)).isTrue();

        handler.handle(new NoOpEditingContext(), input);
        assertThat(hasBeenCalled.get()).isTrue();
    }
}
