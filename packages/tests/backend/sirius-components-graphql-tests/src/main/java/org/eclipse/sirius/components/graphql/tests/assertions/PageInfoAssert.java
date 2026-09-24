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
package org.eclipse.sirius.components.graphql.tests.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.Objects;

/**
 * Custom assertion class used to perform tests on GraphQL page information.
 *
 * @author gcoutable
 */
public class PageInfoAssert {

    private final String data;

    private final String pageInfoPath;

    public PageInfoAssert(String data, String pageInfoPath) {
        this.data = Objects.requireNonNull(data);
        this.pageInfoPath = Objects.requireNonNull(pageInfoPath);
    }

    public PageInfoAssert hasPreviousPage() {
        boolean hasPreviousPage = JsonPath.read(this.data, this.pageInfoPath + ".hasPreviousPage");
        assertThat(hasPreviousPage).isTrue();
        return this;
    }

    public PageInfoAssert hasNoPreviousPage() {
        boolean hasPreviousPage = JsonPath.read(this.data, this.pageInfoPath + ".hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();
        return this;
    }

    public PageInfoAssert hasNextPage() {
        boolean hasNextPage = JsonPath.read(this.data, this.pageInfoPath + ".hasNextPage");
        assertThat(hasNextPage).isTrue();
        return this;
    }

    public PageInfoAssert hasNoNextPage() {
        boolean hasNextPage = JsonPath.read(this.data, this.pageInfoPath + ".hasNextPage");
        assertThat(hasNextPage).isFalse();
        return this;
    }

    public PageInfoAssert hasNonBlankStartCursor() {
        String startCursor = JsonPath.read(this.data, this.pageInfoPath + ".startCursor");
        assertThat(startCursor).isNotBlank();
        return this;
    }

    public PageInfoAssert hasBlankStartCursor() {
        String startCursor = JsonPath.read(this.data, this.pageInfoPath + ".startCursor");
        assertThat(startCursor).isBlank();
        return this;
    }

    public PageInfoAssert hasNonBlankEndCursor() {
        String endCursor = JsonPath.read(this.data, this.pageInfoPath + ".endCursor");
        assertThat(endCursor).isNotBlank();
        return this;
    }

    public PageInfoAssert hasBlankEndCursor() {
        String endCursor = JsonPath.read(this.data, this.pageInfoPath + ".endCursor");
        assertThat(endCursor).isBlank();
        return this;
    }

    public PageInfoAssert hasCount(int expectedCount) {
        int count = JsonPath.read(this.data, this.pageInfoPath + ".count");
        assertThat(count).isEqualTo(expectedCount);
        return this;
    }
}
