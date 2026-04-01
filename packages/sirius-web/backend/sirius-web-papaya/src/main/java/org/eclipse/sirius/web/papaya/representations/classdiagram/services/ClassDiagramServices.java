/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.classdiagram.services;

import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.Type;

/**
 * Java services for the class diagram.
 *
 * @author sbegaudeau
 */
public class ClassDiagramServices {

    public boolean hasTypeContainer(EObject self) {
        Iterable<EObject> content = self::eAllContents;
        var stream = StreamSupport.stream(content.spliterator(), false);
        return stream.anyMatch(child -> child instanceof Package || child instanceof Type);
    }

    public EObject getFirstTypeContainer(EObject self) {
        Iterable<EObject> content = self::eAllContents;
        var stream = StreamSupport.stream(content.spliterator(), false);
        return stream.filter(child -> child instanceof Package || child instanceof Type)
                .findFirst()
                .orElse(null);
    }
}
