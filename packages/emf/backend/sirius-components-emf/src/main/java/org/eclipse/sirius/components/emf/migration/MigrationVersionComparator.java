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

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * Comparator used to compare migration participant versions.
 *
 * @author frouene
 */
public class MigrationVersionComparator implements Comparator<String> {

    private static final Pattern MIGRATION_VERSION_PATTERN = Pattern.compile("^\\d{4}\\.\\d{1,2}\\.\\d+-\\d{12}$");

    @Override
    public int compare(String v1, String v2) {
        boolean isV1Valid = this.isValidVersion(v1);
        boolean isV2Valid = this.isValidVersion(v2);
        if (!isV1Valid || !isV2Valid) {
            int validityResult = 0;
            if (isV1Valid) {
                validityResult = 1;
            }
            return validityResult;
        }
        String[] parts1 = v1.split("\\.");
        String[] build1 = parts1[2].split("-");
        String[] parts2 = v2.split("\\.");
        String[] build2 = parts2[2].split("-");

        int resultCompare = Integer.compare(Integer.parseInt(parts1[0]), Integer.parseInt(parts2[0]));
        if (resultCompare == 0) {
            resultCompare = Integer.compare(Integer.parseInt(parts1[1]), Integer.parseInt(parts2[1]));
        }
        if (resultCompare == 0) {
            resultCompare = Integer.compare(Integer.parseInt(build1[0]), Integer.parseInt(build2[0]));
        }
        if (resultCompare == 0) {
            resultCompare = build1[1].compareTo(build2[1]);
        }

        return resultCompare;
    }

    private boolean isValidVersion(String version) {
        if (version == null) {
            return false;
        }
        return MIGRATION_VERSION_PATTERN.matcher(version).matches();
    }
}
