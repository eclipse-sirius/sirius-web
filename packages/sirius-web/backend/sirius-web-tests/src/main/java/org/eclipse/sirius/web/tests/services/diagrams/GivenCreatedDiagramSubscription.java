/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.tests.services.diagrams;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramEventInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.DiagramEventSubscriptionRunner;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLSubscriptionResult;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

/**
 * Used to create a diagram and subscribe to it.
 *
 * @author sbegaudeau
 */
@Service
public class GivenCreatedDiagramSubscription implements IGivenCreatedDiagramSubscription {

    private final IGivenCreatedRepresentation givenCreatedRepresentation;

    private final DiagramEventSubscriptionRunner diagramEventSubscriptionRunner;

    public GivenCreatedDiagramSubscription(IGivenCreatedRepresentation givenCreatedRepresentation, DiagramEventSubscriptionRunner diagramEventSubscriptionRunner) {
        this.givenCreatedRepresentation = Objects.requireNonNull(givenCreatedRepresentation);
        this.diagramEventSubscriptionRunner = Objects.requireNonNull(diagramEventSubscriptionRunner);
    }

    @Override
    public GraphQLSubscriptionResult createAndSubscribe(CreateRepresentationInput input) {
        String representationId = this.givenCreatedRepresentation.createRepresentation(input);

        var diagramEventInput = new DiagramEventInput(UUID.randomUUID(), input.editingContextId(), representationId);
        var result = this.diagramEventSubscriptionRunner.run(diagramEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return result;
    }
}
