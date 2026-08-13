/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Custom assertion class used to perform tests on a palette query result.
 *
 * @author tgiraudet
 */
public class PaletteAssert {

    private final String result;

    public PaletteAssert(String result) {
        this.result = Objects.requireNonNull(result);
    }

    public PaletteAssert hasQuickAccessTools(Consumer<List<String>> consumer) {
        List<String> quickAccessToolLabels = JsonPath.read(this.result, "$.data.viewer.editingContext.representation.description.palette.quickAccessTools[*].label");
        consumer.accept(quickAccessToolLabels);
        return this;
    }

    public PaletteAssert hasPaletteEntries(Consumer<List<String>> consumer) {
        List<String> paletteEntryLabels = JsonPath.read(this.result, "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].label");
        consumer.accept(paletteEntryLabels);
        return this;
    }
}
