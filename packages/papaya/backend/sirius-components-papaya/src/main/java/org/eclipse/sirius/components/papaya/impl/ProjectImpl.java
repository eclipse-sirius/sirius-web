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

import java.time.LocalDate;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
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
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getContractualStartDate <em>Contractual Start
 * Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getDayDuration <em>Day Duration</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getContractualEndDate <em>Contractual End
 * Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getIsSensitive <em>Is Sensitive</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getComponents <em>Components</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getAllComponents <em>All Components</em>}</li>
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
     * The default value of the '{@link #getContractualStartDate() <em>Contractual Start Date</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getContractualStartDate()
     * @generated
     * @ordered
     */
    protected static final LocalDate CONTRACTUAL_START_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getContractualStartDate() <em>Contractual Start Date</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getContractualStartDate()
     * @generated
     * @ordered
     */
    protected LocalDate contractualStartDate = CONTRACTUAL_START_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getDayDuration() <em>Day Duration</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getDayDuration()
     * @generated
     * @ordered
     */
    protected static final Integer DAY_DURATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDayDuration() <em>Day Duration</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDayDuration()
     * @generated
     * @ordered
     */
    protected Integer dayDuration = DAY_DURATION_EDEFAULT;

    /**
     * The default value of the '{@link #getContractualEndDate() <em>Contractual End Date</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getContractualEndDate()
     * @generated
     * @ordered
     */
    protected static final LocalDate CONTRACTUAL_END_DATE_EDEFAULT = null;

    /**
     * The default value of the '{@link #getIsSensitive() <em>Is Sensitive</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getIsSensitive()
     * @generated
     * @ordered
     */
    protected static final Boolean IS_SENSITIVE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIsSensitive() <em>Is Sensitive</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getIsSensitive()
     * @generated
     * @ordered
     */
    protected Boolean isSensitive = IS_SENSITIVE_EDEFAULT;

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
    public LocalDate getContractualStartDate() {
        return this.contractualStartDate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setContractualStartDate(LocalDate newContractualStartDate) {
        LocalDate oldContractualStartDate = this.contractualStartDate;
        this.contractualStartDate = newContractualStartDate;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.PROJECT__CONTRACTUAL_START_DATE, oldContractualStartDate, this.contractualStartDate));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Integer getDayDuration() {
        return this.dayDuration;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDayDuration(Integer newDayDuration) {
        Integer oldDayDuration = this.dayDuration;
        this.dayDuration = newDayDuration;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.PROJECT__DAY_DURATION, oldDayDuration, this.dayDuration));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public LocalDate getContractualEndDate() {
        if (this.contractualStartDate != null && this.dayDuration != null) {
            return this.contractualStartDate.plusDays(this.dayDuration);
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Boolean getIsSensitive() {
        return this.isSensitive;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsSensitive(Boolean newIsSensitive) {
        Boolean oldIsSensitive = this.isSensitive;
        this.isSensitive = newIsSensitive;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.PROJECT__IS_SENSITIVE, oldIsSensitive, this.isSensitive));
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
    public EList<Component> getAllComponents() {
        // TODO: implement this method to return the 'All Components' reference list
        // Ensure that you remove @generated or mark it @generated NOT
        // The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and
        // org.eclipse.emf.ecore.EStructuralFeature.Setting
        // so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
        throw new UnsupportedOperationException();
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
            case PapayaPackage.PROJECT__CONTRACTUAL_START_DATE:
                return this.getContractualStartDate();
            case PapayaPackage.PROJECT__DAY_DURATION:
                return this.getDayDuration();
            case PapayaPackage.PROJECT__CONTRACTUAL_END_DATE:
                return this.getContractualEndDate();
            case PapayaPackage.PROJECT__IS_SENSITIVE:
                return this.getIsSensitive();
            case PapayaPackage.PROJECT__COMPONENTS:
                return this.getComponents();
            case PapayaPackage.PROJECT__ALL_COMPONENTS:
                return this.getAllComponents();
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
            case PapayaPackage.PROJECT__CONTRACTUAL_START_DATE:
                this.setContractualStartDate((LocalDate) newValue);
                return;
            case PapayaPackage.PROJECT__DAY_DURATION:
                this.setDayDuration((Integer) newValue);
                return;
            case PapayaPackage.PROJECT__IS_SENSITIVE:
                this.setIsSensitive((Boolean) newValue);
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
            case PapayaPackage.PROJECT__CONTRACTUAL_START_DATE:
                this.setContractualStartDate(CONTRACTUAL_START_DATE_EDEFAULT);
                return;
            case PapayaPackage.PROJECT__DAY_DURATION:
                this.setDayDuration(DAY_DURATION_EDEFAULT);
                return;
            case PapayaPackage.PROJECT__IS_SENSITIVE:
                this.setIsSensitive(IS_SENSITIVE_EDEFAULT);
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
            case PapayaPackage.PROJECT__CONTRACTUAL_START_DATE:
                return CONTRACTUAL_START_DATE_EDEFAULT == null ? this.contractualStartDate != null : !CONTRACTUAL_START_DATE_EDEFAULT.equals(this.contractualStartDate);
            case PapayaPackage.PROJECT__DAY_DURATION:
                return DAY_DURATION_EDEFAULT == null ? this.dayDuration != null : !DAY_DURATION_EDEFAULT.equals(this.dayDuration);
            case PapayaPackage.PROJECT__CONTRACTUAL_END_DATE:
                return CONTRACTUAL_END_DATE_EDEFAULT == null ? this.getContractualEndDate() != null : !CONTRACTUAL_END_DATE_EDEFAULT.equals(this.getContractualEndDate());
            case PapayaPackage.PROJECT__IS_SENSITIVE:
                return IS_SENSITIVE_EDEFAULT == null ? this.isSensitive != null : !IS_SENSITIVE_EDEFAULT.equals(this.isSensitive);
            case PapayaPackage.PROJECT__COMPONENTS:
                return this.components != null && !this.components.isEmpty();
            case PapayaPackage.PROJECT__ALL_COMPONENTS:
                return !this.getAllComponents().isEmpty();
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

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (contractualStartDate: ");
        result.append(this.contractualStartDate);
        result.append(", dayDuration: ");
        result.append(this.dayDuration);
        result.append(", isSensitive: ");
        result.append(this.isSensitive);
        result.append(')');
        return result.toString();
    }

} // ProjectImpl
