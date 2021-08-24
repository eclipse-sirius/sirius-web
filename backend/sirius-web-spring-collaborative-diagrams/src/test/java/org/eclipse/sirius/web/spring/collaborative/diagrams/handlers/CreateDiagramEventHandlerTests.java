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

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.tests.TestDiagramBuilder;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.representations.ISemanticRepresentation;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.web.spring.collaborative.dto.CreateRepresentationInput;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Tests of the create representation event handler.
 *
 * @author sbegaudeau
 */
public class CreateDiagramEventHandlerTests {
    @Test
    public void testDiagramCreation() {
        IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService() {
            @Override
            public Optional<IRepresentationDescription> findById(UUID id) {
                // @formatter:off
                DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(UUID.randomUUID())
                        .label("label") //$NON-NLS-1$
                        .canCreatePredicate(variableManager -> Boolean.TRUE)
                        .edgeDescriptions(new ArrayList<>())
                        .labelProvider(variableManager -> "label") //$NON-NLS-1$
                        .toolSections(List.of())
                        .nodeDescriptions(new ArrayList<>())
                        .targetObjectIdProvider(variableManager -> "targetObjectId") //$NON-NLS-1$
                        .dropHandler(variableManager -> Status.ERROR)
                        .build();
                // @formatter:on

                return Optional.of(diagramDescription);
            }
        };

        AtomicBoolean hasBeenCalled = new AtomicBoolean();
        IDiagramCreationService diagramCreationService = new IDiagramCreationService.NoOp() {
            @Override
            public Diagram create(String label, Object targetObject, DiagramDescription diagramDescription, IEditingContext editingContext) {
                hasBeenCalled.set(true);
                return new TestDiagramBuilder().getDiagram(UUID.randomUUID());
            }

        };

        IObjectService objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };

        IRepresentationPersistenceService representationPersistenceService = new IRepresentationPersistenceService() {
            @Override
            public void save(IEditingContext editingContext, ISemanticRepresentation representation) {
            }
        };

        CreateDiagramEventHandler handler = new CreateDiagramEventHandler(representationDescriptionSearchService, representationPersistenceService, diagramCreationService, objectService,
                new NoOpCollaborativeDiagramMessageService(), new SimpleMeterRegistry());

        var input = new CreateRepresentationInput(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "objectId", "representationName"); //$NON-NLS-1$//$NON-NLS-2$
        assertThat(handler.canHandle(input)).isTrue();

        IEditingContext editingContext = () -> UUID.randomUUID();
        handler.handle(editingContext, input);
        assertThat(hasBeenCalled.get()).isTrue();
    }
}
