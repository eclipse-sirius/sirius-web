/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.api.configuration;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import org.eclipse.sirius.web.annotations.PublicApi;

/**
 * The description of a stereotype used to create a document.
 *
 * @author hmarchadour
 */
@PublicApi
public class StereotypeDescription {

    private final UUID id;

    private final String label;

    private final String documentName;

    private final Supplier<String> contentProvider;

    public StereotypeDescription(UUID id, String label, Supplier<String> contentProvider) {
        this(id, label, label, contentProvider);
    }

    public StereotypeDescription(UUID id, String label, String documentName, Supplier<String> contentProvider) {
        this.id = Objects.requireNonNull(id);
        this.label = Objects.requireNonNull(label);
        this.documentName = Objects.requireNonNull(documentName);
        this.contentProvider = Objects.requireNonNull(contentProvider);
    }

    public UUID getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDocumentName() {
        return this.documentName;
    }

    public String getContent() {
        return this.contentProvider.get();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, documentName: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.documentName);
    }
}
