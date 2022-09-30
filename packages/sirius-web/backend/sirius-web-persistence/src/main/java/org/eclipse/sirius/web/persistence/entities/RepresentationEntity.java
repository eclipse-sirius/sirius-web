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
package org.eclipse.sirius.web.persistence.entities;

import java.text.MessageFormat;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Representation entity used by the persistence layer.
 *
 * @author gcoutable
 */
@Entity
@Table(name = "Representation")
public class RepresentationEntity {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    private String targetObjectId;

    private String descriptionId;

    private String label;

    private String kind;

    private String content;

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

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public void setDescriptionId(String descriptionId) {
        this.descriptionId = descriptionId;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public void setTargetObjectId(String targetObjectId) {
        this.targetObjectId = targetObjectId;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, label: {3}, kind: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.label, this.kind);
    }
}
