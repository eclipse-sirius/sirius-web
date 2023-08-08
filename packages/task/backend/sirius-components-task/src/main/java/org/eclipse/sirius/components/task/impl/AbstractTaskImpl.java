/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.task.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.task.AbstractTask;
import org.eclipse.sirius.components.task.Person;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskPackage;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.components.task.Team;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Abstract Task</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getDescription <em>Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getStartDate <em>Start Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getEndDate <em>End Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getProgress <em>Progress</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#isComputeDateDynamically <em>Compute Date
 * Dynamically</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getTags <em>Tags</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getDependencies <em>Dependencies</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getAssignedPersons <em>Assigned
 * Persons</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getAssignedTeams <em>Assigned Teams</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl#getSubTasks <em>Sub Tasks</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractTaskImpl extends MinimalEObjectImpl.Container implements AbstractTask {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected static final int START_DATE_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected int startDate = START_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected static final int END_DATE_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected int endDate = END_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getProgress() <em>Progress</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getProgress()
     * @generated
     * @ordered
     */
    protected static final int PROGRESS_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getProgress() <em>Progress</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getProgress()
     * @generated
     * @ordered
     */
    protected int progress = PROGRESS_EDEFAULT;

    /**
     * The default value of the '{@link #isComputeDateDynamically() <em>Compute Date Dynamically</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isComputeDateDynamically()
     * @generated
     * @ordered
     */
    protected static final boolean COMPUTE_DATE_DYNAMICALLY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isComputeDateDynamically() <em>Compute Date Dynamically</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isComputeDateDynamically()
     * @generated
     * @ordered
     */
    protected boolean computeDateDynamically = COMPUTE_DATE_DYNAMICALLY_EDEFAULT;

    /**
     * The cached value of the '{@link #getTags() <em>Tags</em>}' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getTags()
     * @generated
     * @ordered
     */
    protected EList<TaskTag> tags;

    /**
     * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getDependencies()
     * @generated
     * @ordered
     */
    protected EList<Task> dependencies;

    /**
     * The cached value of the '{@link #getAssignedPersons() <em>Assigned Persons</em>}' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAssignedPersons()
     * @generated
     * @ordered
     */
    protected EList<Person> assignedPersons;

    /**
     * The cached value of the '{@link #getAssignedTeams() <em>Assigned Teams</em>}' reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getAssignedTeams()
     * @generated
     * @ordered
     */
    protected EList<Team> assignedTeams;

    /**
     * The cached value of the '{@link #getSubTasks() <em>Sub Tasks</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSubTasks()
     * @generated
     * @ordered
     */
    protected EList<Task> subTasks;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected AbstractTaskImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TaskPackage.Literals.ABSTRACT_TASK;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setName(String newName) {
        String oldName = this.name;
        this.name = newName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDescription(String newDescription) {
        String oldDescription = this.description;
        this.description = newDescription;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__DESCRIPTION, oldDescription, this.description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getStartDate() {
        return this.startDate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStartDate(int newStartDate) {
        int oldStartDate = this.startDate;
        this.startDate = newStartDate;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__START_DATE, oldStartDate, this.startDate));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getEndDate() {
        return this.endDate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setEndDate(int newEndDate) {
        int oldEndDate = this.endDate;
        this.endDate = newEndDate;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__END_DATE, oldEndDate, this.endDate));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getProgress() {
        return this.progress;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setProgress(int newProgress) {
        int oldProgress = this.progress;
        this.progress = newProgress;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__PROGRESS, oldProgress, this.progress));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isComputeDateDynamically() {
        return this.computeDateDynamically;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setComputeDateDynamically(boolean newComputeDateDynamically) {
        boolean oldComputeDateDynamically = this.computeDateDynamically;
        this.computeDateDynamically = newComputeDateDynamically;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.ABSTRACT_TASK__COMPUTE_DATE_DYNAMICALLY, oldComputeDateDynamically, this.computeDateDynamically));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TaskTag> getTags() {
        if (this.tags == null) {
            this.tags = new EObjectResolvingEList<>(TaskTag.class, this, TaskPackage.ABSTRACT_TASK__TAGS);
        }
        return this.tags;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Task> getDependencies() {
        if (this.dependencies == null) {
            this.dependencies = new EObjectResolvingEList<>(Task.class, this, TaskPackage.ABSTRACT_TASK__DEPENDENCIES);
        }
        return this.dependencies;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Person> getAssignedPersons() {
        if (this.assignedPersons == null) {
            this.assignedPersons = new EObjectResolvingEList<>(Person.class, this, TaskPackage.ABSTRACT_TASK__ASSIGNED_PERSONS);
        }
        return this.assignedPersons;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Team> getAssignedTeams() {
        if (this.assignedTeams == null) {
            this.assignedTeams = new EObjectResolvingEList<>(Team.class, this, TaskPackage.ABSTRACT_TASK__ASSIGNED_TEAMS);
        }
        return this.assignedTeams;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Task> getSubTasks() {
        if (this.subTasks == null) {
            this.subTasks = new EObjectContainmentEList<>(Task.class, this, TaskPackage.ABSTRACT_TASK__SUB_TASKS);
        }
        return this.subTasks;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case TaskPackage.ABSTRACT_TASK__SUB_TASKS:
                return ((InternalEList<?>) this.getSubTasks()).basicRemove(otherEnd, msgs);
            default:
                return super.eInverseRemove(otherEnd, featureID, msgs);
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TaskPackage.ABSTRACT_TASK__NAME:
                return this.getName();
            case TaskPackage.ABSTRACT_TASK__DESCRIPTION:
                return this.getDescription();
            case TaskPackage.ABSTRACT_TASK__START_DATE:
                return this.getStartDate();
            case TaskPackage.ABSTRACT_TASK__END_DATE:
                return this.getEndDate();
            case TaskPackage.ABSTRACT_TASK__PROGRESS:
                return this.getProgress();
            case TaskPackage.ABSTRACT_TASK__COMPUTE_DATE_DYNAMICALLY:
                return this.isComputeDateDynamically();
            case TaskPackage.ABSTRACT_TASK__TAGS:
                return this.getTags();
            case TaskPackage.ABSTRACT_TASK__DEPENDENCIES:
                return this.getDependencies();
            case TaskPackage.ABSTRACT_TASK__ASSIGNED_PERSONS:
                return this.getAssignedPersons();
            case TaskPackage.ABSTRACT_TASK__ASSIGNED_TEAMS:
                return this.getAssignedTeams();
            case TaskPackage.ABSTRACT_TASK__SUB_TASKS:
                return this.getSubTasks();
            default:
                return super.eGet(featureID, resolve, coreType);
        }
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
            case TaskPackage.ABSTRACT_TASK__NAME:
                this.setName((String) newValue);
                return;
            case TaskPackage.ABSTRACT_TASK__DESCRIPTION:
                this.setDescription((String) newValue);
                return;
            case TaskPackage.ABSTRACT_TASK__START_DATE:
                this.setStartDate((Integer) newValue);
                return;
            case TaskPackage.ABSTRACT_TASK__END_DATE:
                this.setEndDate((Integer) newValue);
                return;
            case TaskPackage.ABSTRACT_TASK__PROGRESS:
                this.setProgress((Integer) newValue);
                return;
            case TaskPackage.ABSTRACT_TASK__COMPUTE_DATE_DYNAMICALLY:
                this.setComputeDateDynamically((Boolean) newValue);
                return;
            case TaskPackage.ABSTRACT_TASK__TAGS:
                this.getTags().clear();
                this.getTags().addAll((Collection<? extends TaskTag>) newValue);
                return;
            case TaskPackage.ABSTRACT_TASK__DEPENDENCIES:
                this.getDependencies().clear();
                this.getDependencies().addAll((Collection<? extends Task>) newValue);
                return;
            case TaskPackage.ABSTRACT_TASK__ASSIGNED_PERSONS:
                this.getAssignedPersons().clear();
                this.getAssignedPersons().addAll((Collection<? extends Person>) newValue);
                return;
            case TaskPackage.ABSTRACT_TASK__ASSIGNED_TEAMS:
                this.getAssignedTeams().clear();
                this.getAssignedTeams().addAll((Collection<? extends Team>) newValue);
                return;
            case TaskPackage.ABSTRACT_TASK__SUB_TASKS:
                this.getSubTasks().clear();
                this.getSubTasks().addAll((Collection<? extends Task>) newValue);
                return;
            default:
                super.eSet(featureID, newValue);
                return;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case TaskPackage.ABSTRACT_TASK__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case TaskPackage.ABSTRACT_TASK__DESCRIPTION:
                this.setDescription(DESCRIPTION_EDEFAULT);
                return;
            case TaskPackage.ABSTRACT_TASK__START_DATE:
                this.setStartDate(START_DATE_EDEFAULT);
                return;
            case TaskPackage.ABSTRACT_TASK__END_DATE:
                this.setEndDate(END_DATE_EDEFAULT);
                return;
            case TaskPackage.ABSTRACT_TASK__PROGRESS:
                this.setProgress(PROGRESS_EDEFAULT);
                return;
            case TaskPackage.ABSTRACT_TASK__COMPUTE_DATE_DYNAMICALLY:
                this.setComputeDateDynamically(COMPUTE_DATE_DYNAMICALLY_EDEFAULT);
                return;
            case TaskPackage.ABSTRACT_TASK__TAGS:
                this.getTags().clear();
                return;
            case TaskPackage.ABSTRACT_TASK__DEPENDENCIES:
                this.getDependencies().clear();
                return;
            case TaskPackage.ABSTRACT_TASK__ASSIGNED_PERSONS:
                this.getAssignedPersons().clear();
                return;
            case TaskPackage.ABSTRACT_TASK__ASSIGNED_TEAMS:
                this.getAssignedTeams().clear();
                return;
            case TaskPackage.ABSTRACT_TASK__SUB_TASKS:
                this.getSubTasks().clear();
                return;
            default:
                super.eUnset(featureID);
                return;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case TaskPackage.ABSTRACT_TASK__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case TaskPackage.ABSTRACT_TASK__DESCRIPTION:
                return DESCRIPTION_EDEFAULT == null ? this.description != null : !DESCRIPTION_EDEFAULT.equals(this.description);
            case TaskPackage.ABSTRACT_TASK__START_DATE:
                return this.startDate != START_DATE_EDEFAULT;
            case TaskPackage.ABSTRACT_TASK__END_DATE:
                return this.endDate != END_DATE_EDEFAULT;
            case TaskPackage.ABSTRACT_TASK__PROGRESS:
                return this.progress != PROGRESS_EDEFAULT;
            case TaskPackage.ABSTRACT_TASK__COMPUTE_DATE_DYNAMICALLY:
                return this.computeDateDynamically != COMPUTE_DATE_DYNAMICALLY_EDEFAULT;
            case TaskPackage.ABSTRACT_TASK__TAGS:
                return this.tags != null && !this.tags.isEmpty();
            case TaskPackage.ABSTRACT_TASK__DEPENDENCIES:
                return this.dependencies != null && !this.dependencies.isEmpty();
            case TaskPackage.ABSTRACT_TASK__ASSIGNED_PERSONS:
                return this.assignedPersons != null && !this.assignedPersons.isEmpty();
            case TaskPackage.ABSTRACT_TASK__ASSIGNED_TEAMS:
                return this.assignedTeams != null && !this.assignedTeams.isEmpty();
            case TaskPackage.ABSTRACT_TASK__SUB_TASKS:
                return this.subTasks != null && !this.subTasks.isEmpty();
            default:
                return super.eIsSet(featureID);
        }
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
        result.append(" (name: ");
        result.append(this.name);
        result.append(", description: ");
        result.append(this.description);
        result.append(", startDate: ");
        result.append(this.startDate);
        result.append(", endDate: ");
        result.append(this.endDate);
        result.append(", progress: ");
        result.append(this.progress);
        result.append(", computeDateDynamically: ");
        result.append(this.computeDateDynamically);
        result.append(')');
        return result.toString();
    }

} // AbstractTaskImpl
