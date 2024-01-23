/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.portals.api;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.portals.services.PortalServices;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.portals.Portal;

/**
 * Context for the execution of a portal event handler.
 *
 * @author pcdavid
 */
public class PortalContext {
    private final IRepresentationSearchService representationSearchService;

    private final IEditingContext editingContext;

    private final Portal currentPortal;

    private final IPortalInput input;

    private Optional<Portal> nextPortal = Optional.empty();

    public PortalContext(IRepresentationSearchService representationSearchService, IEditingContext editingContext, Portal currentPortal, IPortalInput input) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.editingContext = Objects.requireNonNull(editingContext);
        this.currentPortal = Objects.requireNonNull(currentPortal);
        this.input = Objects.requireNonNull(input);
    }

    public PortalServices getServices() {
        return new PortalServices(this.representationSearchService, this.editingContext);
    }

    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    public Portal getCurrentPortal() {
        return this.currentPortal;
    }

    public IPortalInput getInput() {
        return this.input;
    }

    public Optional<Portal> getNextPortal() {
        return this.nextPortal;
    }

    public void setNextPortal(Portal nextPortal) {
        this.nextPortal = Optional.ofNullable(nextPortal);
    }

}
