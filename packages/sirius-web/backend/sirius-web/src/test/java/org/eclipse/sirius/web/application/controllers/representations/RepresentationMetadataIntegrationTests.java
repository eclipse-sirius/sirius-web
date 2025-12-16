/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.controllers.representations;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationMetadataDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataDeletionService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the representation metadata controllers.
 *
 * @author ntinsalhi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepresentationMetadataIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IRepresentationMetadataSearchService representationMetadataSearchService;

    @Autowired
    private ISemanticDataSearchService semanticDataSearchService;

    @Autowired
    private ISemanticDataDeletionService semanticDataDeletionService;

    @Autowired
    private IDomainEventCollector domainEventCollector;

    @BeforeEach
    public void beforeEach() {
        this.domainEventCollector.clear();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given representations linked to semantic data, when a SemanticDataDeletedEvent is published, then the representation metadata is deleted")
    public void givenRepresentationsLinkedToSemanticDataWhenASemanticDataDeletedEventIsPublishedThenTheRepresentationMetadataIsDeleted() {
        Optional<SemanticData> optionalSemanticData = this.semanticDataSearchService.findById(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID);
        assertThat(optionalSemanticData).isPresent();

        var semanticDataRepresentationsMetadata = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticData(AggregateReference.to(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID));
        assertThat(semanticDataRepresentationsMetadata).isNotEmpty();

        this.semanticDataDeletionService.deleteSemanticData(null, optionalSemanticData.get().getId());

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        assertThat(this.domainEventCollector.getDomainEvents()).anyMatch(SemanticDataDeletedEvent.class::isInstance);
        assertThat(this.domainEventCollector.getDomainEvents()).anyMatch(RepresentationMetadataDeletedEvent.class::isInstance);

        semanticDataRepresentationsMetadata = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticData(AggregateReference.to(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID));
        assertThat(semanticDataRepresentationsMetadata).isEmpty();
    }
}
