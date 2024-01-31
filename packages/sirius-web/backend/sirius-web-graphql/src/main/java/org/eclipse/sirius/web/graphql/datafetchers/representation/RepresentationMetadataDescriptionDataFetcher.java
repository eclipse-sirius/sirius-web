/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.representation;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionInput;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionPayload;
import org.eclipse.sirius.components.collaborative.forms.PropertiesEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.trees.TreeEventProcessorFactory;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.trees.description.TreeDescription;

import graphql.schema.DataFetchingEnvironment;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import reactor.core.publisher.Mono;

/**
 * The data fetcher used to retrieve the description from the representation metadata.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "RepresentationMetadata", field = "description")
public class RepresentationMetadataDescriptionDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IRepresentationDescription>> {

    // @formatter:off
    private static final IRepresentationDescription FAKE_DETAILS_DESCRIPTION = FormDescription.newFormDescription(PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
            .label(PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
            .idProvider(new GetOrCreateRandomIdProvider())
            .labelProvider(variableManager -> PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
            .targetObjectIdProvider(variableManager -> PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
            .canCreatePredicate(variableManager -> true)
            .pageDescriptions(List.of())
            .build();
    // @formatter:on

    // @formatter:off
    private static final IRepresentationDescription FAKE_DETAILS_TREE = TreeDescription.newTreeDescription(TreeEventProcessorFactory.TREE_ID)
            .label("Explorer")
            .idProvider(new GetOrCreateRandomIdProvider())
            .treeItemIdProvider(variableManager -> TreeEventProcessorFactory.TREE_ID)
            .kindProvider(variableManager -> TreeEventProcessorFactory.TREE_ID)
            .labelProvider(variableManager -> new StyledString(List.of()))
            .iconURLProvider(variableManager -> List.of(TreeEventProcessorFactory.TREE_ID))
            .editableProvider(variableManager -> null)
            .deletableProvider(variableManager -> null)
            .elementsProvider(variableManager -> null)
            .hasChildrenProvider(variableManager -> null)
            .childrenProvider(variableManager -> null)
            .canCreatePredicate(variableManager -> true)
            .deleteHandler(variableManager -> null)
            .renameHandler((variableManager, newValue) -> new Failure(""))
            .build();
    // @formatter:on

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;


    public RepresentationMetadataDescriptionDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<IRepresentationDescription> get(DataFetchingEnvironment environment) throws Exception {
        CompletableFuture<IRepresentationDescription> result = Mono.<IRepresentationDescription>empty().toFuture();

        RepresentationMetadata representationMetadata = environment.getSource();
        if (Objects.equals(PropertiesEventProcessorFactory.DETAILS_VIEW_ID, representationMetadata.getDescriptionId()) || (Objects.equals(TreeEventProcessorFactory.TREE_ID, representationMetadata.getDescriptionId()))) {
            /*
             * The FormDescription used for the details view and the TreeDescription used for the explorer can not be found by
             * IRepresentationDescriptionSearchService. We can get away by returning fake ones with the same ids as
             * no GraphQL query actually needs to see their content. We need to return *something* with the correct ids
             * only to allow GraphQL resolution to continue on queries like "completionProposals" defined on
             * FormDescription.
             */
            if (Objects.equals(PropertiesEventProcessorFactory.DETAILS_VIEW_ID, representationMetadata.getDescriptionId())) {
                result = Mono.just(FAKE_DETAILS_DESCRIPTION).toFuture();
            }
            if (Objects.equals(TreeEventProcessorFactory.TREE_ID, representationMetadata.getDescriptionId())) {
                result = Mono.just(FAKE_DETAILS_TREE).toFuture();
            }
        } else {
            Map<String, Object> localContext = environment.getLocalContext();

            String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
            String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
            if (editingContextId != null && representationId != null) {
                GetRepresentationDescriptionInput input = new GetRepresentationDescriptionInput(UUID.randomUUID(), editingContextId, representationId);
                // @formatter:off
                result = this.editingContextEventProcessorRegistry.dispatchEvent(input.editingContextId(), input)
                        .filter(GetRepresentationDescriptionPayload.class::isInstance)
                        .map(GetRepresentationDescriptionPayload.class::cast)
                        .map(GetRepresentationDescriptionPayload::representationDescription)
                        .toFuture();
                // @formatter:on
            }
        }
        return result;
    }

}
