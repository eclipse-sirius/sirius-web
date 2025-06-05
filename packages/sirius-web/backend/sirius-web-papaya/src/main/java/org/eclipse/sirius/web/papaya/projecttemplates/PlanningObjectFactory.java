/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.sirius.web.papaya.projecttemplates;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.papaya.Folder;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.web.papaya.factories.services.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.services.api.IObjectFactory;

/**
 * Used to create the objects of the planning.
 *
 * @author sbegaudeau
 */
public class PlanningObjectFactory implements IObjectFactory {

    private final Project project;

    public PlanningObjectFactory(Project project) {
        this.project = Objects.requireNonNull(project);
    }

    @Override
    public void create(IEMFEditingContext editingContext) {
        var iterationsFolder = this.createIterationsFolder();
        project.getFolders().add(iterationsFolder);
    }

    private Folder createIterationsFolder() {
        Folder iterationsFolder = PapayaFactory.eINSTANCE.createFolder();
        iterationsFolder.setName("Iterations");

        var iteration20256 = this.createIteration20256();
        var iteration20258 = this.createIteration20258();

        iterationsFolder.getFolders().addAll(List.of(
                iteration20256,
                iteration20258
        ));

        return iterationsFolder;
    }

    private Folder createIteration20256() {
        Folder iteration20256Folder = PapayaFactory.eINSTANCE.createFolder();
        iteration20256Folder.setName("Version 2025.6.0");

        var v20256 = PapayaFactory.eINSTANCE.createIteration();
        v20256.setName("2025.6.0");
        v20256.setStartDate(Instant.parse("2025-05-28T14:00:00.00z"));
        v20256.setEndDate(Instant.parse("2025-06-20T17:00:00.00z"));

        return iteration20256Folder;
    }

    private Folder createIteration20258() {
        Folder iteration20258Folder = PapayaFactory.eINSTANCE.createFolder();
        iteration20258Folder.setName("Version 2025.8.0");

        var v20258 = PapayaFactory.eINSTANCE.createIteration();
        v20258.setName("2025.8.0");
        v20258.setStartDate(Instant.parse("2025-06-23T14:00:00.00z"));
        v20258.setEndDate(Instant.parse("2025-08-15T17:00:00.00z"));

        return iteration20258Folder;
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer) {

    }
}
