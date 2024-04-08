/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
 * Used to store some test identifiers related to the studio projects.
 *
 * @author sbegaudeau
 */
public final class StudioIdentifiers {

    public static final UUID EMPTY_STUDIO_PROJECT = UUID.fromString("250cabc0-a211-438c-8015-2d2aa136eb81");

    public static final UUID SAMPLE_STUDIO_PROJECT = UUID.fromString("01234836-0902-418a-900a-4c0afd20323e");

    public static final UUID DOMAIN_DOCUMENT = UUID.fromString("f0e490c1-79f1-49a0-b1f2-3637f2958148");

    public static final UUID DOMAIN_OBJECT = UUID.fromString("f8204cb6-3705-48a5-bee3-ad7e7d6cbdaf");

    public static final UUID HUMAN_ENTITY_OBJECT = UUID.fromString("1731ffb5-bfb0-46f3-a23d-0c0650300005");

    public static final UUID FORM_DESCRIPTION_OBJECT = UUID.fromString("ed20cb85-a58a-47ad-bc0d-749ec8b2ea03");

    private StudioIdentifiers() {
        // Prevent instantiation
    }
}
