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
package org.eclipse.sirius.web.tests.services.representation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.tests.graphql.RepresentationMetadataQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to verify the label of a representation thanks to its metadata.
 *
 * @author gcoutable
 */
@Service
public class RepresentationMetadataLabelVerifier {

    private final RepresentationMetadataQueryRunner representationMetadataQueryRunner;

    public RepresentationMetadataLabelVerifier(RepresentationMetadataQueryRunner representationMetadataQueryRunner) {
        this.representationMetadataQueryRunner = Objects.requireNonNull(representationMetadataQueryRunner);
    }

    public void verify(UUID editingContextId, IRepresentation representation, String expectedRepresentationName, String errorIfMissing) {
        Consumer<Map<String, Object>> checkRepresentationMetadataLabel = (variables) -> {
            var result = this.representationMetadataQueryRunner.run(variables);

            String label = JsonPath.read(result, "$.data.viewer.editingContext.representation.label");
            assertThat(label).isEqualTo(expectedRepresentationName);
        };

        Optional.of(representation)
                .map(IRepresentation::getId)
                .flatMap(new UUIDParser()::parse)
                .ifPresentOrElse(representationId -> {
                    Map<String, Object> getRepresentationMetadataVariables = Map.of("editingContextId", editingContextId, "representationId", representationId);
                    checkRepresentationMetadataLabel.accept(getRepresentationMetadataVariables);
                }, () -> fail(errorIfMissing));
    }
}
