/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.web.persistence.entities;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a custom (user-supplied) image that can be used on representations. This includes the actual image
 * contents and should only be used when this (possible very large) contents is needed: on initial upload or for serving
 * the image to the client. See {@link CustomImageMetadataEntity} when only the images metadata are needed.
 *
 * @author pcdavid
 */
@Entity
@Table(name = "CustomImage")
public class CustomImageEntity {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    private String label;

    @Column(name = "content_type")
    private String contentType;

    private byte[] content;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ProjectEntity getProject() {
        return this.project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return this.content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        String projectId = Optional.ofNullable(this.project).map(ProjectEntity::getId).map(UUID::toString).orElse("null");
        String pattern = "{0} '{' id: {1}, projectId: {2}, label: {3}, contentType: {4} '}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, projectId, this.label, this.contentType);
    }
}
