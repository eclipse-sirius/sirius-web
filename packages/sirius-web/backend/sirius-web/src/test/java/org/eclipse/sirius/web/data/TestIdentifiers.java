/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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

    public static final UUID INVALID_PROJECT = UUID.fromString("a2bed581-9661-41bf-9217-2870a9dce67c");
    public static final UUID EMPTY_STUDIO_PROJECT = UUID.fromString("250cabc0-a211-438c-8015-2d2aa136eb81");
    public static final UUID SAMPLE_STUDIO_PROJECT = UUID.fromString("01234836-0902-418a-900a-4c0afd20323e");
    public static final UUID FORM_DESCRIPTION_OBJECT = UUID.fromString("ed20cb85-a58a-47ad-bc0d-749ec8b2ea03");
    public static final UUID UML_SAMPLE_PROJECT = UUID.fromString("7ba7bda7-13b9-422a-838b-e45a3597e952");
    public static final UUID SYSML_SAMPLE_PROJECT = UUID.fromString("4164c661-e0cb-4071-b25d-8516440bb8e8");
    public static final UUID ECORE_SAMPLE_PROJECT = UUID.fromString("99d336a2-3049-439a-8853-b104ffb22653");
    public static final UUID ECORE_SAMPLE_DOCUMENT = UUID.fromString("48dc942a-6b76-4133-bca5-5b29ebee133d");
    public static final UUID EPACKAGE_OBJECT = UUID.fromString("3237b215-ae23-48d7-861e-f542a4b9a4b8");
    public static final UUID EPACKAGE_PORTAL_REPRESENTATION = UUID.fromString("e81eec5c-42d6-491c-8bcc-9beb951356f8");
    public static final UUID DOMAIN_OBJECT = UUID.fromString("f8204cb6-3705-48a5-bee3-ad7e7d6cbdaf");
    public static final UUID HUMAN_ENTITY_OBJECT = UUID.fromString("1731ffb5-bfb0-46f3-a23d-0c0650300005");
    public static final UUID PORTAL_REPRESENTATION_DESCRIPTION = UUID.fromString("69030a1b-0b5f-3c1d-8399-8ca260e4a672");

    private TestIdentifiers() {
        // Prevent instantiation
    }
}
