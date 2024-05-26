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
package org.eclipse.sirius.web.papaya.factories.services;

import java.util.Objects;

import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.components.papaya.util.PapayaSwitch;

/**
 * Used to navigate a papaya model and index its content.
 *
 * @author sbegaudeau
 */
public class PapayaSwitchIndexer extends PapayaSwitch<Void> {

    private final EObjectIndexer eObjectIndexer;

    public PapayaSwitchIndexer(EObjectIndexer eObjectIndexer) {
        this.eObjectIndexer = Objects.requireNonNull(eObjectIndexer);
    }

    @Override
    public Void caseProject(Project project) {
        project.getComponents().forEach(this::caseComponent);
        project.getProjects().forEach(this::caseProject);
        return super.caseProject(project);
    }

    @Override
    public Void caseComponent(Component component) {
        this.eObjectIndexer.getNameToComponent().put(component.getName(), component);

        component.getPackages().forEach(this::casePackage);
        component.getComponents().forEach(this::caseComponent);
        return super.caseComponent(component);
    }

    @Override
    public Void casePackage(Package aPackage) {
        aPackage.getTypes().forEach(this::caseType);
        aPackage.getPackages().forEach(this::casePackage);
        return super.casePackage(aPackage);
    }

    @Override
    public Void caseType(Type type) {
        this.eObjectIndexer.getQualifiedNameToType().put(type.getQualifiedName(), type);

        type.getTypes().forEach(this::caseType);
        return super.caseType(type);
    }
}
