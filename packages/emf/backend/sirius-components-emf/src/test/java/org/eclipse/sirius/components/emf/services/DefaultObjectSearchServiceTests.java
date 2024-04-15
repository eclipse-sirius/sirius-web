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
package org.eclipse.sirius.components.emf.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IDefaultObjectSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.junit.jupiter.api.Test;

/**
 * Test for the default implementation if {@link IDefaultObjectSearchService}.
 *
 * @author pcdavid
 */
public class DefaultObjectSearchServiceTests {

    @Test
    public void testFindsRepresentationFromItsId() {
        var fakeId = "fake";
        IRepresentation fakeRepresentation = new IRepresentation.NoOp() {
            @Override
            public String getId() {
                return fakeId;
            }
        };

        IRepresentationSearchService representationSearchService = new IRepresentationSearchService.NoOp() {
            @Override
            public <T extends IRepresentation> Optional<T> findById(IEditingContext editingContext, String representationId, Class<T> representationClass) {
                if (fakeId.equals(representationId)) {
                    return Optional.of(fakeRepresentation).map(representationClass::cast);
                } else {
                    return Optional.empty();
                }
            }
        };
        DefaultObjectSearchService defaultObjectSearchService = new DefaultObjectSearchService(representationSearchService);

        var optionalObject = defaultObjectSearchService.getObject(new IEditingContext.NoOp(), fakeId);
        assertThat(optionalObject).containsSame(fakeRepresentation);
    }

    @Test
    public void testFindsEditingContextFromItsId() {
        var editingContextId = "editingContextId";
        IEditingContext editingContext = () -> editingContextId;

        DefaultObjectSearchService defaultObjectSearchService = new DefaultObjectSearchService(new IRepresentationSearchService.NoOp());

        var optionalObject = defaultObjectSearchService.getObject(editingContext, editingContextId);
        assertThat(optionalObject).containsSame(editingContext);
    }

}
