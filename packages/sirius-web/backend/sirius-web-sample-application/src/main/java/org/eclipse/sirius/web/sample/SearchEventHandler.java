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
package org.eclipse.sirius.web.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.dto.SearchInput;
import org.eclipse.sirius.components.collaborative.dto.SearchResult;
import org.eclipse.sirius.components.collaborative.dto.SearchResultItem;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Event handler for search queries.
 *
 * @author pcdavid
 */
@Service
public class SearchEventHandler implements IEditingContextEventHandler {
    private final IObjectService objectService;

    public SearchEventHandler(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof SearchInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        if (input instanceof SearchInput searchInput) {
            ResourceSet rs = ((IEMFEditingContext) editingContext).getDomain().getResourceSet();
            List<SearchResultItem> matches = new ArrayList<>();
            var iter = rs.getAllContents();
            while (iter.hasNext()) {
                var current = iter.next();
                this.check(searchInput, current).ifPresent(matches::add);
            }
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), searchInput));
            payloadSink.tryEmitValue(new SearchResult(input.id(), matches));
        } else {
            payloadSink.tryEmitValue(new ErrorPayload(input.id(), "Invalid input"));
        }
    }

    private Optional<SearchResultItem> check(SearchInput searchInput, Object object) {
        String label = this.objectService.getLabel(object);
        if (label != null && label.contains(searchInput.terms())) {
            return Optional.of(new SearchResultItem(UUID.nameUUIDFromBytes(this.objectService.getId(object).getBytes()), label, this.objectService.getKind(object), this.objectService.getImagePath(object)));
        } else {
            return Optional.empty();
        }
    }

}
