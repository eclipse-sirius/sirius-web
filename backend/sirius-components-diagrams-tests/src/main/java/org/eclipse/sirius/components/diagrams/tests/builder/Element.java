/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.builder;

import java.util.List;
import java.util.Objects;

/**
 * An element in the json based editing context.
 *
 * @author gcoutable
 */
public class Element {
    private String name;

    private List<Element> children;

    public Element() {
        // For jackson
    }

    public Element(String name, List<Element> children) {
        this.name = Objects.requireNonNull(name);
        this.children = Objects.requireNonNull(children);
    }

    public String getName() {
        return this.name;
    }

    public List<Element> getChildren() {
        return this.children;
    }
}
