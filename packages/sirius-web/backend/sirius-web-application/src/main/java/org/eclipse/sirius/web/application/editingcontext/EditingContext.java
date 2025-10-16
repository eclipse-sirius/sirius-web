/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.web.application.editingcontext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.representations.change.IRepresentationChange;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewConversionData;

/**
 * Implementation of the editing context.
 *
 * @author sbegaudeau
 */
public class EditingContext implements IEMFEditingContext {

    private final String id;

    private final AdapterFactoryEditingDomain editingDomain;

    private final Map<String, IRepresentationDescription> representationDescriptions;

    private final List<View> views;

    private final Map<UUID, ChangeDescription> inputId2change = new HashMap<>();

    private final Map<UUID, List<IRepresentationChange>> inputId2RepresentationChanges = new HashMap<>();

    private final Map<String, IViewConversionData> viewConversionData = new HashMap<>();

    private final ChangeRecorder changeRecorder;

    public EditingContext(String id, AdapterFactoryEditingDomain editingDomain, Map<String, IRepresentationDescription> representationDescriptions, List<View> views) {
        this.id = Objects.requireNonNull(id);
        this.editingDomain = Objects.requireNonNull(editingDomain);
        this.changeRecorder = new ChangeRecorder(this.editingDomain.getResourceSet());
        this.representationDescriptions = Objects.requireNonNull(representationDescriptions);
        this.views = Objects.requireNonNull(views);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public AdapterFactoryEditingDomain getDomain() {
        return this.editingDomain;
    }

    public Map<String, IRepresentationDescription> getRepresentationDescriptions() {
        return this.representationDescriptions;
    }

    public List<View> getViews() {
        return this.views;
    }
    public ChangeRecorder getChangeRecorder() {
        return this.changeRecorder;
    }

    public Map<UUID, ChangeDescription> getInputId2change() {
        return this.inputId2change;
    }

    public Map<UUID, List<IRepresentationChange>> getInputId2RepresentationChanges() {
        return this.inputId2RepresentationChanges;
    }

    public Map<String, IViewConversionData> getViewConversionData() {
        return this.viewConversionData;
    }

    @Override
    public void dispose() {
        this.changeRecorder.dispose();
        IEMFEditingContext.super.dispose();
    }

}
