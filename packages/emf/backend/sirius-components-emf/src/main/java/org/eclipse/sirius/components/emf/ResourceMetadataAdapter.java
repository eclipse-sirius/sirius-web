/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.emf;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.sirius.components.emf.migration.api.MigrationData;

/**
 * An EMF adapter used to store some metadata related to EMF Resources.
 *
 * <p>
 * This adapter is currently used to store the following metadata:
 * </p>
 * <ul>
 * <li>name: The user-friendly name of the resource if presented in the UI</li>
 * </ul>
 *
 * @author sbegaudeau
 */
public class ResourceMetadataAdapter implements Adapter {
    private String name;

    private boolean isReadOnly;

    private List<MigrationData> migrationData = new ArrayList<>();

    private Notifier notifier;

    public ResourceMetadataAdapter(String name) {
        this(name, false);
    }

    public ResourceMetadataAdapter(String name, boolean isReadOnly) {
        this.name = Objects.requireNonNull(name);
        this.isReadOnly = isReadOnly;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReadOnly() {
        return this.isReadOnly;
    }

    public void setIsReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    public List<MigrationData> getAllMigrationData() {
        return this.migrationData;
    }

    public MigrationData getLastMigrationData() {
        if (this.migrationData.isEmpty()) {
            return null;
        }
        return this.migrationData.get(this.migrationData.size() - 1);
    }

    public boolean addMigrationData(MigrationData newMigrationData) {
        return this.migrationData.add(newMigrationData);
    }

    @Override
    public void notifyChanged(Notification notification) {
        // do nothing
    }

    @Override
    public Notifier getTarget() {
        return this.notifier;
    }

    @Override
    public void setTarget(Notifier newTarget) {
        this.notifier = newTarget;
    }

    @Override
    public boolean isAdapterForType(Object type) {
        return false;
    }
}
