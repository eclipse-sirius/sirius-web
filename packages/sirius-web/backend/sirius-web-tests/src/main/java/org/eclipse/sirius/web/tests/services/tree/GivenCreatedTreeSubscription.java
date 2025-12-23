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
package org.eclipse.sirius.web.tests.services.tree;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeEventInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLSubscriptionResult;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedRepresentation;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedTreeSubscription;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

/**
 * Used to create a tree and subscribe to it.
 *
 * @author Jerome Gout
 */
@Service
public class GivenCreatedTreeSubscription implements IGivenCreatedTreeSubscription {

    private final IGivenCommittedTransaction givenCommittedTransaction;

    private final IGivenCreatedRepresentation givenCreatedRepresentation;

    private final TreeEventSubscriptionRunner treeEventSubscriptionRunner;

    private final RepresentationIdBuilder representationIdBuilder;

    public GivenCreatedTreeSubscription(IGivenCommittedTransaction givenCommittedTransaction, IGivenCreatedRepresentation givenCreatedRepresentation, TreeEventSubscriptionRunner treeEventSubscriptionRunner, RepresentationIdBuilder representationIdBuilder) {
        this.givenCommittedTransaction = Objects.requireNonNull(givenCommittedTransaction);
        this.givenCreatedRepresentation = Objects.requireNonNull(givenCreatedRepresentation);
        this.treeEventSubscriptionRunner = Objects.requireNonNull(treeEventSubscriptionRunner);
        this.representationIdBuilder = Objects.requireNonNull(representationIdBuilder);
    }

    @Override
    public GraphQLSubscriptionResult createAndSubscribe(CreateRepresentationInput input) {
        this.givenCommittedTransaction.commit();

        String representationId = this.givenCreatedRepresentation.createRepresentation(input);

        var treeEventInput = new TreeEventInput(UUID.randomUUID(), input.editingContextId(), this.representationIdBuilder.buildTreeRepresentationId(representationId, List.of()));
        var result = this.treeEventSubscriptionRunner.run(treeEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return result;
    }
}
