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
package org.eclipse.sirius.components.papaya.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.ComponentExchange;
import org.eclipse.sirius.components.papaya.Contribution;
import org.eclipse.sirius.components.papaya.Iteration;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.papaya.Task;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Project</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getProjects <em>Projects</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getComponents <em>Components</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getComponentExchanges <em>Component
 * Exchanges</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getIterations <em>Iterations</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getTasks <em>Tasks</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getContributions <em>Contributions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectImpl extends NamedElementImpl implements Project {
    /**
     * The cached value of the '{@link #getProjects() <em>Projects</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getProjects()
     * @generated
     * @ordered
     */
    protected EList<Project> projects;

    /**
     * The cached value of the '{@link #getComponents() <em>Components</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getComponents()
     * @generated
     * @ordered
     */
    protected EList<Component> components;

    /**
     * The cached value of the '{@link #getComponentExchanges() <em>Component Exchanges</em>}' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getComponentExchanges()
     * @generated
     * @ordered
     */
    protected EList<ComponentExchange> componentExchanges;

    /**
     * The cached value of the '{@link #getIterations() <em>Iterations</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIterations()
     * @generated
     * @ordered
     */
    protected EList<Iteration> iterations;

    /**
     * The cached value of the '{@link #getTasks() <em>Tasks</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getTasks()
     * @generated
     * @ordered
     */
    protected EList<Task> tasks;

    /**
     * The cached value of the '{@link #getContributions() <em>Contributions</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getContributions()
     * @generated
     * @ordered
     */
    protected EList<Contribution> contributions;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ProjectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.PROJECT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Project> getProjects() {
        if (this.projects == null) {
            this.projects = new EObjectContainmentEList<>(Project.class, this, PapayaPackage.PROJECT__PROJECTS);
        }
        return this.projects;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Component> getComponents() {
        if (this.components == null) {
            this.components = new EObjectContainmentEList<>(Component.class, this, PapayaPackage.PROJECT__COMPONENTS);
        }
        return this.components;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ComponentExchange> getComponentExchanges() {
        if (this.componentExchanges == null) {
            this.componentExchanges = new EObjectContainmentEList<>(ComponentExchange.class, this, PapayaPackage.PROJECT__COMPONENT_EXCHANGES);
        }
        return this.componentExchanges;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Iteration> getIterations() {
        if (this.iterations == null) {
            this.iterations = new EObjectContainmentEList<>(Iteration.class, this, PapayaPackage.PROJECT__ITERATIONS);
        }
        return this.iterations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Task> getTasks() {
        if (this.tasks == null) {
            this.tasks = new EObjectContainmentEList<>(Task.class, this, PapayaPackage.PROJECT__TASKS);
        }
        return this.tasks;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Contribution> getContributions() {
        if (this.contributions == null) {
            this.contributions = new EObjectContainmentEList<>(Contribution.class, this, PapayaPackage.PROJECT__CONTRIBUTIONS);
        }
        return this.contributions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.PROJECT__PROJECTS:
                return ((InternalEList<?>) this.getProjects()).basicRemove(otherEnd, msgs);
            case PapayaPackage.PROJECT__COMPONENTS:
                return ((InternalEList<?>) this.getComponents()).basicRemove(otherEnd, msgs);
            case PapayaPackage.PROJECT__COMPONENT_EXCHANGES:
                return ((InternalEList<?>) this.getComponentExchanges()).basicRemove(otherEnd, msgs);
            case PapayaPackage.PROJECT__ITERATIONS:
                return ((InternalEList<?>) this.getIterations()).basicRemove(otherEnd, msgs);
            case PapayaPackage.PROJECT__TASKS:
                return ((InternalEList<?>) this.getTasks()).basicRemove(otherEnd, msgs);
            case PapayaPackage.PROJECT__CONTRIBUTIONS:
                return ((InternalEList<?>) this.getContributions()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapayaPackage.PROJECT__PROJECTS:
                return this.getProjects();
            case PapayaPackage.PROJECT__COMPONENTS:
                return this.getComponents();
            case PapayaPackage.PROJECT__COMPONENT_EXCHANGES:
                return this.getComponentExchanges();
            case PapayaPackage.PROJECT__ITERATIONS:
                return this.getIterations();
            case PapayaPackage.PROJECT__TASKS:
                return this.getTasks();
            case PapayaPackage.PROJECT__CONTRIBUTIONS:
                return this.getContributions();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case PapayaPackage.PROJECT__PROJECTS:
                this.getProjects().clear();
                this.getProjects().addAll((Collection<? extends Project>) newValue);
                return;
            case PapayaPackage.PROJECT__COMPONENTS:
                this.getComponents().clear();
                this.getComponents().addAll((Collection<? extends Component>) newValue);
                return;
            case PapayaPackage.PROJECT__COMPONENT_EXCHANGES:
                this.getComponentExchanges().clear();
                this.getComponentExchanges().addAll((Collection<? extends ComponentExchange>) newValue);
                return;
            case PapayaPackage.PROJECT__ITERATIONS:
                this.getIterations().clear();
                this.getIterations().addAll((Collection<? extends Iteration>) newValue);
                return;
            case PapayaPackage.PROJECT__TASKS:
                this.getTasks().clear();
                this.getTasks().addAll((Collection<? extends Task>) newValue);
                return;
            case PapayaPackage.PROJECT__CONTRIBUTIONS:
                this.getContributions().clear();
                this.getContributions().addAll((Collection<? extends Contribution>) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case PapayaPackage.PROJECT__PROJECTS:
                this.getProjects().clear();
                return;
            case PapayaPackage.PROJECT__COMPONENTS:
                this.getComponents().clear();
                return;
            case PapayaPackage.PROJECT__COMPONENT_EXCHANGES:
                this.getComponentExchanges().clear();
                return;
            case PapayaPackage.PROJECT__ITERATIONS:
                this.getIterations().clear();
                return;
            case PapayaPackage.PROJECT__TASKS:
                this.getTasks().clear();
                return;
            case PapayaPackage.PROJECT__CONTRIBUTIONS:
                this.getContributions().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case PapayaPackage.PROJECT__PROJECTS:
                return this.projects != null && !this.projects.isEmpty();
            case PapayaPackage.PROJECT__COMPONENTS:
                return this.components != null && !this.components.isEmpty();
            case PapayaPackage.PROJECT__COMPONENT_EXCHANGES:
                return this.componentExchanges != null && !this.componentExchanges.isEmpty();
            case PapayaPackage.PROJECT__ITERATIONS:
                return this.iterations != null && !this.iterations.isEmpty();
            case PapayaPackage.PROJECT__TASKS:
                return this.tasks != null && !this.tasks.isEmpty();
            case PapayaPackage.PROJECT__CONTRIBUTIONS:
                return this.contributions != null && !this.contributions.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // ProjectImpl
