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
package org.eclipse.sirius.components.collaborative.widget.reference.datafetchers;

import java.util.Objects;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
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

    private final IEMFKindService emfKindService;

    public ReferenceWidgetReferenceDataFetcher(IEMFKindService emfKindService) {
        this.emfKindService = Objects.requireNonNull(emfKindService);
    }


    @Override
    public Reference get(DataFetchingEnvironment environment) throws Exception {
        ReferenceWidget referenceWidget = environment.getSource();
        var feature = referenceWidget.getSetting().getEStructuralFeature();
        return new Reference(this.emfKindService.getKind(referenceWidget.getSetting().getEObject().eClass()),
                feature.getName(),
                this.emfKindService.getKind(((EReference) feature).getEReferenceType()),
                ((EReference) feature).isContainment(),
                feature.isMany());
    }
}
