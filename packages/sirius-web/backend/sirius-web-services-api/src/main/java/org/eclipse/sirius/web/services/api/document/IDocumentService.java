/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.services.api.document;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface of the service interacting with documents.
 *
 * @author hmarchadour
 */
public interface IDocumentService {

    String RESOURCE_KIND_JSON = "json"; //$NON-NLS-1$

    String RESOURCE_KIND_XMI = "xmi"; //$NON-NLS-1$

    Optional<Document> createDocument(String projectId, String name, String content);

    Optional<Document> getDocument(UUID documentId);

    Optional<Document> getDocument(String projectId, UUID documentId);

    List<Document> getDocuments(String projectId);

    void delete(UUID documentId);

    Optional<byte[]> getBytes(Document document, String resourceKind);

    Optional<Document> rename(UUID documentId, String newName);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IDocumentService {

        @Override
        public Optional<Document> createDocument(String projectId, String name, String content) {
            return Optional.empty();
        }

        @Override
        public Optional<Document> getDocument(UUID documentId) {
            return Optional.empty();
        }

        @Override
        public Optional<Document> getDocument(String projectId, UUID documentId) {
            return Optional.empty();
        }

        @Override
        public List<Document> getDocuments(String projectId) {
            return List.of();
        }

        @Override
        public void delete(UUID documentId) {
        }

        @Override
        public Optional<byte[]> getBytes(Document document, String resourceKind) {
            return Optional.empty();
        }

        @Override
        public Optional<Document> rename(UUID documentId, String newName) {
            return Optional.empty();
        }

    }
}
