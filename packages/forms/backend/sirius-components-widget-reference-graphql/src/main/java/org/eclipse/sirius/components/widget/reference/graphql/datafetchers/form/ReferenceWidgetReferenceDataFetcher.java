/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widget.reference.graphql.datafetchers.form;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.widget.reference.Reference;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for ReferenceWidget.reference.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "ReferenceWidget", field = "reference")
public class ReferenceWidgetReferenceDataFetcher implements IDataFetcherWithFieldCoordinates<Reference> {

    @Override
    public Reference get(DataFetchingEnvironment environment) throws Exception {
        ReferenceWidget referenceWidget = environment.getSource();
        return new Reference(referenceWidget.getOwnerKind(),
                referenceWidget.getReferenceKind(),
                referenceWidget.isContainment(),
                referenceWidget.isMany());
    }
}
