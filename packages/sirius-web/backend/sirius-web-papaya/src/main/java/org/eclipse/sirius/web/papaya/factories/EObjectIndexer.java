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
package org.eclipse.sirius.web.papaya.factories;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.papaya.Class;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.Enum;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.papaya.Record;
import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.web.papaya.factories.api.IEObjectIndexer;

/**
 * Used to index EObjects.
 *
 * @author sbegaudeau
 */
public class EObjectIndexer implements IEObjectIndexer {

    private final Map<String, Component> nameToComponent = new LinkedHashMap<>();

    private final Map<String, Type> qualifiedNameToType = new LinkedHashMap<>();

    @Override
    public void index(EObject eObject) {
        if (eObject instanceof Project project) {
            this.index(project);
        }
    }

    private void index(Project project) {
        project.getComponents().forEach(this::index);
        project.getProjects().forEach(this::index);
    }

    private void index(Component component) {
        this.nameToComponent.put(component.getName(), component);
        component.getPackages().forEach(this::index);
        component.getComponents().forEach(this::index);
    }

    private void index(Package aPackage) {
        aPackage.getTypes().forEach(this::index);
        aPackage.getPackages().forEach(this::index);
    }

    private void index(Type type) {
        type.getTypes().forEach(this::index);
        this.qualifiedNameToType.put(type.getQualifiedName(), type);
    }

    @Override
    public Type getType(String qualifiedName) {
        return this.qualifiedNameToType.get(qualifiedName);
    }

    @Override
    public Class getClass(String qualifiedName) {
        return Optional.ofNullable(this.getType(qualifiedName))
                .filter(Class.class::isInstance)
                .map(Class.class::cast)
                .orElse(null);
    }

    @Override
    public Interface getInterface(String qualifiedName) {
        return Optional.ofNullable(this.getType(qualifiedName))
                .filter(Interface.class::isInstance)
                .map(Interface.class::cast)
                .orElse(null);
    }

    @Override
    public Record getRecord(String qualifiedName) {
        return Optional.ofNullable(this.getType(qualifiedName))
                .filter(Record.class::isInstance)
                .map(Record.class::cast)
                .orElse(null);
    }

    @Override
    public Enum getEnum(String qualifiedName) {
        return Optional.ofNullable(this.getType(qualifiedName))
                .filter(Enum.class::isInstance)
                .map(Enum.class::cast)
                .orElse(null);
    }

    @Override
    public Component getComponent(String name) {
        return this.nameToComponent.get(name);
    }
}
