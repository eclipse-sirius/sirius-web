/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.data;

import java.util.UUID;

/**
 * Interface used to store some test identifiers.
 *
 * @author sbegaudeau
 */
public final class TestIdentifiers {

    public static final String INVALID_PROJECT = "a2bed581-9661-41bf-9217-2870a9dce67c";
    public static final String UML_SAMPLE_PROJECT = "7ba7bda7-13b9-422a-838b-e45a3597e952";
    public static final String SYSML_SAMPLE_EDITING_CONTEXT_ID = "86fa5d90-a602-4083-b3c1-65912b93b673";
    public static final String SYSML_SAMPLE_PROJECT = "4164c661-e0cb-4071-b25d-8516440bb8e8";
    public static final UUID SYSML_IMAGE = UUID.fromString("ff37f0eb-effb-4c57-b17f-76bc7ea64f5b");
    public static final String ECORE_SAMPLE_EDITING_CONTEXT_ID = "cb133bf0-d7aa-4a83-a277-0972919dd46a";
    public static final String ECORE_SAMPLE_PROJECT = "99d336a2-3049-439a-8853-b104ffb22653";
    public static final UUID ECORE_SAMPLE_DOCUMENT = UUID.fromString("48dc942a-6b76-4133-bca5-5b29ebee133d");
    public static final UUID EPACKAGE_OBJECT = UUID.fromString("3237b215-ae23-48d7-861e-f542a4b9a4b8");
    public static final UUID ECLASS_OBJECT = UUID.fromString("f0eecd16-d9da-4c98-a422-c73897bc48f5");
    public static final UUID EPACKAGE_PORTAL_REPRESENTATION = UUID.fromString("e81eec5c-42d6-491c-8bcc-9beb951356f8");
    public static final UUID EPACKAGE_EMPTY_PORTAL_REPRESENTATION = UUID.fromString("05e44ccc-9363-443f-a816-25fc73e3e7f7");

    private TestIdentifiers() {
        // Prevent instantiation
    }
}
