/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Lightweight entity which avoids loading the actual contents when not needed.
 *
 * @author pcdavid
 */
@Entity
@Table(name = "CustomImage")
public class CustomImageMetadataEntity {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    private String label;

    @Column(name = "content_type")
    private String contentType;

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

    @Override
    public String toString() {
        String projectId = Optional.ofNullable(this.project).map(ProjectEntity::getId).map(UUID::toString).orElse("null");
        String pattern = "{0} '{' id: {1}, projectId: {2}, label: {3}, contentType: {4} '}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, projectId, this.label, this.contentType);
    }
}
