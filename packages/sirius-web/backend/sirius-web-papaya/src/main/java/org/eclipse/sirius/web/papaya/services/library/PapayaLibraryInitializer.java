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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.papaya.services.library.api.IPapayaLibraryPublisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * Used to ensure that the Papaya - Java Standard Library is always published in a Sirius Web application.
 *
 * @author sbegaudeau
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class PapayaLibraryInitializer implements CommandLineRunner {

    private final List<IPapayaLibraryPublisher> papayaLibraryPublishers;

    public PapayaLibraryInitializer(List<IPapayaLibraryPublisher> papayaLibraryPublishers) {
        this.papayaLibraryPublishers = Objects.requireNonNull(papayaLibraryPublishers);
    }

    @Override
    public void run(String... args) throws Exception {
        List.of(
                new PublishPapayaLibraryCommand(UUID.randomUUID(), "papaya", "java", "0.0.1", "The standard library of the Java programming language"),
                new PublishPapayaLibraryCommand(UUID.randomUUID(), "papaya", "java", "0.0.2", "The standard library of the Java programming language"),
                new PublishPapayaLibraryCommand(UUID.randomUUID(), "papaya", "java", "0.0.3", "The standard library of the Java programming language"),
                new PublishPapayaLibraryCommand(UUID.randomUUID(), "papaya", "reactivestreams", "0.0.1", "The reactive stream library"),
                new PublishPapayaLibraryCommand(UUID.randomUUID(), "papaya", "reactivestreams", "0.0.2", "The reactive stream library"),
                new PublishPapayaLibraryCommand(UUID.randomUUID(), "papaya", "reactivestreams", "0.0.3", "The reactive stream library"),
                new PublishPapayaLibraryCommand(UUID.randomUUID(), "papaya", "reactor", "0.0.1", "The reactor library"),
                new PublishPapayaLibraryCommand(UUID.randomUUID(), "papaya", "reactor", "0.0.2", "The reactor library"),
                new PublishPapayaLibraryCommand(UUID.randomUUID(), "papaya", "reactor", "0.0.3", "The reactor library")
        ).forEach(command -> {
            this.papayaLibraryPublishers.stream()
                    .filter(publisher -> publisher.canPublish(command))
                    .findFirst()
                    .ifPresent(publisher -> publisher.publish(command));
        });
    }
}
