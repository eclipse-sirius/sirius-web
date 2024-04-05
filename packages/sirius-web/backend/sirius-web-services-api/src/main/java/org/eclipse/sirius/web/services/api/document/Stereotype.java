/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.services.api.document;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * The description of a stereotype used to create a document.
 *
 * @author hmarchadour
 */
public class Stereotype {

    private final UUID id;

    private final String label;

    private final Supplier<String> contentProvider;

    public Stereotype(UUID id, String label, Supplier<String> contentProvider) {
        this.id = Objects.requireNonNull(id);
        this.label = Objects.requireNonNull(label);
        this.contentProvider = Objects.requireNonNull(contentProvider);
    }

    public UUID getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getContent() {
        return this.contentProvider.get();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }
}
