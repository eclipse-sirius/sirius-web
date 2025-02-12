/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.web.papaya.services.library;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.papaya.services.library.api.IStandardLibrarySemanticDataInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * Used to ensure that the Papaya - Java Standard Library is always published in a Sirius Web application.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaJavaStandardLibraryPublisher implements CommandLineRunner {

    private final IStandardLibrarySemanticDataInitializer standardLibrarySemanticDataInitializer;

    public PapayaJavaStandardLibraryPublisher(IStandardLibrarySemanticDataInitializer standardLibrarySemanticDataInitializer) {
        this.standardLibrarySemanticDataInitializer = Objects.requireNonNull(standardLibrarySemanticDataInitializer);
    }

    @Override
    public void run(String... args) throws Exception {
        var initializeJavaStandardLibraryEvent = new InitializeStandardLibraryEvent(UUID.randomUUID(), "java", "Java Standard Library", "17.0.0", "The standard library of the Java programming language");
        this.standardLibrarySemanticDataInitializer.initializeStandardLibrary(initializeJavaStandardLibraryEvent);
    }
}
