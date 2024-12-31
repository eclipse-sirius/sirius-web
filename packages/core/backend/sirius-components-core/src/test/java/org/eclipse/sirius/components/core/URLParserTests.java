/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
package org.eclipse.sirius.components.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests of the URLParser.
 *
 * @author frouene
 */
public class URLParserTests {

    @Test
    public void testGetParameterEntriesWithNestedLists() {
        URLParser parser = new URLParser();
        String value = "[467ec1c3-8ba7-32dc-9d72-71a2ccad161b:[\"1\",\"\"],4752c1c3-8ba7-32dc-9d72-71a2ccad161b:[\"1\",\"2\"]]";
        List<String> expected = List.of(
                "467ec1c3-8ba7-32dc-9d72-71a2ccad161b:[\"1\",\"\"]",
                "4752c1c3-8ba7-32dc-9d72-71a2ccad161b:[\"1\",\"2\"]"
        );
        List<String> result = parser.getParameterEntries(value);
        assertThat(expected).isEqualTo(result);
    }

    @Test
    public void testGetParameterEntriesWithEmptyList() {
        URLParser parser = new URLParser();
        String value = "[]";
        List<String> expected = List.of();
        List<String> result = parser.getParameterEntries(value);
        assertThat(expected).isEqualTo(result);
    }

    @Test
    public void testGetParameterEntriesWithSingleElement() {
        URLParser parser = new URLParser();
        String value = "[467ec1c3-8ba7-32dc-9d72-71a2ccad161b:[\"1\",\"\"]]";
        List<String> expected = List.of("467ec1c3-8ba7-32dc-9d72-71a2ccad161b:[\"1\",\"\"]");
        List<String> result = parser.getParameterEntries(value);
        assertThat(expected).isEqualTo(result);
    }

}
