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
package org.eclipse.sirius.components.trees.tests.graphql;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.KeyBinding;

import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Custom assertion class used to perform tests on a palette query result.
 *
 * @author mcharfadi
 */
public class PaletteAssert {

    private final String result;

    public PaletteAssert(String result) {
        this.result = Objects.requireNonNull(result);
    }

    public PaletteAssert hasQuickAccessTools(Consumer<List<String>> consumer) {
        List<String> quickAccessToolLabels = JsonPath.read(this.result, "$.data.viewer.editingContext.representation.description.palette.quickAccessTools[*].id");
        consumer.accept(quickAccessToolLabels);
        return this;
    }

    public PaletteAssert hasPaletteEntriesIds(Consumer<List<String>> consumer) {
        List<String> paletteEntryLabels = JsonPath.read(this.result, "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].id");
        consumer.accept(paletteEntryLabels);
        return this;
    }

    public PaletteAssert hasPaletteEntriesLabels(Consumer<List<String>> consumer) {
        List<String> paletteEntryLabels = JsonPath.read(this.result, "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].label");
        consumer.accept(paletteEntryLabels);
        return this;
    }

    public PaletteAssert hasFetchTreeItemTool(String label, BiConsumer<String, List<KeyBinding>> consumer) {
        return this.hasPaletteEntry(label, "FetchTreeItemTool", consumer);
    }

    public PaletteAssert hasSingleClickTreeItemTool(String label, BiConsumer<String, List<KeyBinding>> consumer) {
        return this.hasPaletteEntry(label, "SingleClickTreeItemTool", consumer);
    }

    private PaletteAssert hasPaletteEntry(String label, String expectedType, BiConsumer<String, List<KeyBinding>> consumer) {
        List<Map<String, Object>> paletteEntries = JsonPath.read(this.result, "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*]");
        var optionalPaletteEntry = paletteEntries.stream()
                .filter(paletteEntry -> label.equals(paletteEntry.get("label")))
                .findFirst();
        assertThat(optionalPaletteEntry).as("Palette entry with label '%s'", label).isPresent();

        var paletteEntry = optionalPaletteEntry.orElseThrow();
        assertThat(paletteEntry).containsEntry("__typename", expectedType);
        String id = JsonPath.read(paletteEntry, "$.id");
        List<Map<String, Object>> keyBindingValues = JsonPath.read(paletteEntry, "$.keyBindings[*]");
        var keyBindings = keyBindingValues.stream()
                .map(keyBindingValue -> new KeyBinding(
                        Boolean.TRUE.equals(keyBindingValue.get("isCtrl")),
                        Boolean.TRUE.equals(keyBindingValue.get("isMeta")),
                        Boolean.TRUE.equals(keyBindingValue.get("isAlt")),
                        (String) keyBindingValue.get("key")))
                .toList();
        consumer.accept(id, keyBindings);
        return this;
    }
}
