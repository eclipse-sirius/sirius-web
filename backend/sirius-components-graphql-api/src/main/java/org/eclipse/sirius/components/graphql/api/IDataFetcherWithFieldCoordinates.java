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
package org.eclipse.sirius.components.graphql.api;

import java.util.List;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.annotations.spring.graphql.SubscriptionDataFetcher;

import graphql.schema.DataFetcher;
import graphql.schema.FieldCoordinates;

/**
 * Interface used to define a data fetcher for specific field coordinates.
 * <p>
 * This interface will allow us to register all data fetchers more easily since data fetchers will both define the
 * coordinates of the fields that they can handle and how to handle them.
 * </p>
 *
 * @param <T>
 *            The type returned by the data fetcher
 *
 * @author sbegaudeau
 */
public interface IDataFetcherWithFieldCoordinates<T> extends DataFetcher<T> {
    /**
     * The field coordinates that are supported by the data fetcher.
     *
     * @return The field coordinates
     */
    default List<FieldCoordinates> getFieldCoordinates() {
        FieldCoordinates fieldCoordinates = null;

        QueryDataFetcher queryDataFetcher = this.getClass().getAnnotation(QueryDataFetcher.class);
        MutationDataFetcher mutationDataFetcher = this.getClass().getAnnotation(MutationDataFetcher.class);
        SubscriptionDataFetcher subscriptionDataFetcher = this.getClass().getAnnotation(SubscriptionDataFetcher.class);
        if (queryDataFetcher != null) {
            fieldCoordinates = FieldCoordinates.coordinates(queryDataFetcher.type(), queryDataFetcher.field());
        } else if (mutationDataFetcher != null) {
            fieldCoordinates = FieldCoordinates.coordinates(mutationDataFetcher.type(), mutationDataFetcher.field());
        } else if (subscriptionDataFetcher != null) {
            fieldCoordinates = FieldCoordinates.coordinates(subscriptionDataFetcher.type(), subscriptionDataFetcher.field());
        }

        return List.of(fieldCoordinates);
    }
}
