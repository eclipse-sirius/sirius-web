/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.core.api;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Used to represent a domain in the GraphQL API.
 *
 * @author lfasani
 */
public class Domain implements Comparable<Domain> {
    private final String id;

    private final String label;

    public Domain(String id, String label) {
        this.id = Objects.requireNonNull(id);
        this.label = Objects.requireNonNull(label);
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.label);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Domain && Objects.equals(this.id, ((Domain) obj).id) && Objects.equals(this.label, ((Domain) obj).label);
    }

    @Override
    public int compareTo(Domain other) {
        String customDomainPrefix = "domain://";
        // Make sure custom-defined domains are before non-custom ones
        if (this.id.startsWith(customDomainPrefix) && !other.id.startsWith(customDomainPrefix)) {
            return -1;
        } else {
            return this.id.compareTo(other.id);
        }
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

}
