/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.emf.migration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests of the MigrationVersionComparator.
 *
 * @author frouene
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class MigrationVersionComparatorTests {

    private final MigrationVersionComparator comparator = new MigrationVersionComparator();

    @Test
    void testInvalidVersion() {
        assertEquals(1, this.comparator.compare(
                "2025.10.0-202509251100",
                "0"
        ));
    }

    @Test
    void testInvalidVersions() {
        assertEquals(0, this.comparator.compare(
                "2025.aa.0-202509251100",
                "0"
        ));
    }

    @Test
    void testEqualVersions() {
        assertEquals(0, this.comparator.compare(
                "2025.10.0-202509251100",
                "2025.10.0-202509251100"
        ));
    }

    @Test
    void testYearComparison() {
        assertTrue(this.comparator.compare(
                "2024.10.0-202409251100",
                "2025.10.0-202509251100"
        ) < 0);
        assertTrue(this.comparator.compare(
                "2026.10.0-202609251100",
                "2025.10.0-202509251100"
        ) > 0);
    }

    @Test
    void testMonthComparison() {
        assertTrue(this.comparator.compare(
                "2025.6.0-202506011650",
                "2025.10.0-202509251100"
        ) < 0);
        assertTrue(this.comparator.compare(
                "2025.12.0-202512011650",
                "2025.10.0-202509251100"
        ) > 0);
    }

    @Test
    void testBuildComparison() {
        assertTrue(this.comparator.compare(
                "2025.10.1-202509251100",
                "2025.10.2-202509251100"
        ) < 0);
        assertTrue(this.comparator.compare(
                "2025.10.3-202509251100",
                "2025.10.2-202509251100"
        ) > 0);
    }

    @Test
    void testDateComparison() {
        assertTrue(this.comparator.compare(
                "2025.10.0-202509251100",
                "2025.10.0-202509251200"
        ) < 0);
        assertTrue(this.comparator.compare(
                "2025.10.0-202509261100",
                "2025.10.0-202509251200"
        ) > 0);
    }

    @Test
    void testSorting() {
        List<String> versions = Arrays.asList(
                "2025.6.0-202506011650",
                "2025.10.0-202509251100",
                "2024.12.0-202412011650",
                "2025.10.1-202509251200"
        );
        versions.sort(this.comparator);
        assertEquals(
                Arrays.asList(
                        "2024.12.0-202412011650",
                        "2025.6.0-202506011650",
                        "2025.10.0-202509251100",
                        "2025.10.1-202509251200"
                ),
                versions
        );
    }
}
