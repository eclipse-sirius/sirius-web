/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.studio.services.representations;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventProcessorFactory;
import org.springframework.stereotype.Service;

/**
 * Provides the metadata for the domain explorer representation.
 *
 * @author frouene
 */
@Service
public class DomainTreeRepresentationMetadataProvider implements IRepresentationMetadataProvider {

    private final IURLParser urlParser;

    private final DomainViewTreeDescriptionProvider domainViewTreeDescriptionProvider;

    public DomainTreeRepresentationMetadataProvider(IURLParser urlParser, DomainViewTreeDescriptionProvider domainViewTreeDescriptionProvider) {
        this.urlParser = Objects.requireNonNull(urlParser);
        this.domainViewTreeDescriptionProvider = Objects.requireNonNull(domainViewTreeDescriptionProvider);
    }

    @Override
    public Optional<RepresentationMetadata> getMetadata(String representationId) {
        var domainViewTreeDescriptionId = this.domainViewTreeDescriptionProvider.getRepresentationDescriptionId();
        if (this.getTreeDescriptionIdFromRepresentationId(representationId).equals(domainViewTreeDescriptionId)) {
            var representationMetadata = RepresentationMetadata.newRepresentationMetadata(representationId)
                    .kind(Tree.KIND)
                    .label(DomainViewTreeDescriptionProvider.DOMAIN_EXPLORER_DESCRIPTION_NAME)
                    .descriptionId(domainViewTreeDescriptionId)
                    .iconURLs(List.of("/explorer/explorer.svg"))
                    .build();

            return Optional.of(representationMetadata);
        }
        return Optional.empty();
    }

    private String getTreeDescriptionIdFromRepresentationId(String representationId) {
        if (representationId.indexOf(ExplorerEventProcessorFactory.TREE_DESCRIPTION_ID_PARAMETER) > 0) {
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(representationId);
            return parameters.get(ExplorerEventProcessorFactory.TREE_DESCRIPTION_ID_PARAMETER).get(0);
        }
        return "";
    }
}
