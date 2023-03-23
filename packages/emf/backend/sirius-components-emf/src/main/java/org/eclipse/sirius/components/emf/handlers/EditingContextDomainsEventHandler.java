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
package org.eclipse.sirius.components.emf.handlers;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.dto.EditingContextDomainsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextDomainsPayload;
import org.eclipse.sirius.components.core.api.Domain;
import org.eclipse.sirius.components.core.api.IDomainSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Objects;

/**
 * Event handler to find/compute all the domains accessible from a given editing context.
 *
 * @author frouene
 */
@Service
public class EditingContextDomainsEventHandler implements IEditingContextEventHandler {

    private final IDomainSearchService domainSearchService;

    public EditingContextDomainsEventHandler(IDomainSearchService domainSearchService) {
        this.domainSearchService = Objects.requireNonNull(domainSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof EditingContextDomainsInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        List<Domain> domains = List.of();
        if (input instanceof EditingContextDomainsInput) {
            domains = domainSearchService.findAllByEditingContext(editingContext);
        }
        payloadSink.tryEmitValue(new EditingContextDomainsPayload(input.id(), domains));
    }
}
