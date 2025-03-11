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
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.papaya.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.ApplicationConcern;
import org.eclipse.sirius.components.papaya.Command;
import org.eclipse.sirius.components.papaya.Controller;
import org.eclipse.sirius.components.papaya.Domain;
import org.eclipse.sirius.components.papaya.Event;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Query;
import org.eclipse.sirius.components.papaya.Service;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Application Concern</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ApplicationConcernImpl#getControllers <em>Controllers</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ApplicationConcernImpl#getServices <em>Services</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ApplicationConcernImpl#getEvents <em>Events</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ApplicationConcernImpl#getCommands <em>Commands</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ApplicationConcernImpl#getQueries <em>Queries</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ApplicationConcernImpl#getDomains <em>Domains</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ApplicationConcernImpl extends NamedElementImpl implements ApplicationConcern {
    /**
     * The cached value of the '{@link #getControllers() <em>Controllers</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getControllers()
     * @generated
     * @ordered
     */
    protected EList<Controller> controllers;

    /**
     * The cached value of the '{@link #getServices() <em>Services</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getServices()
     * @generated
     * @ordered
     */
    protected EList<Service> services;

    /**
     * The cached value of the '{@link #getEvents() <em>Events</em>}' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getEvents()
     * @generated
     * @ordered
     */
    protected EList<Event> events;

    /**
     * The cached value of the '{@link #getCommands() <em>Commands</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCommands()
     * @generated
     * @ordered
     */
    protected EList<Command> commands;

    /**
     * The cached value of the '{@link #getQueries() <em>Queries</em>}' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getQueries()
     * @generated
     * @ordered
     */
    protected EList<Query> queries;

    /**
     * The cached value of the '{@link #getDomains() <em>Domains</em>}' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDomains()
     * @generated
     * @ordered
     */
    protected EList<Domain> domains;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ApplicationConcernImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.APPLICATION_CONCERN;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Controller> getControllers() {
        if (this.controllers == null) {
            this.controllers = new EObjectContainmentEList<>(Controller.class, this, PapayaPackage.APPLICATION_CONCERN__CONTROLLERS);
        }
        return this.controllers;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Service> getServices() {
        if (this.services == null) {
            this.services = new EObjectContainmentEList<>(Service.class, this, PapayaPackage.APPLICATION_CONCERN__SERVICES);
        }
        return this.services;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Event> getEvents() {
        if (this.events == null) {
            this.events = new EObjectContainmentEList<>(Event.class, this, PapayaPackage.APPLICATION_CONCERN__EVENTS);
        }
        return this.events;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Command> getCommands() {
        if (this.commands == null) {
            this.commands = new EObjectContainmentEList<>(Command.class, this, PapayaPackage.APPLICATION_CONCERN__COMMANDS);
        }
        return this.commands;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Query> getQueries() {
        if (this.queries == null) {
            this.queries = new EObjectContainmentEList<>(Query.class, this, PapayaPackage.APPLICATION_CONCERN__QUERIES);
        }
        return this.queries;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Domain> getDomains() {
        if (this.domains == null) {
            this.domains = new EObjectResolvingEList<>(Domain.class, this, PapayaPackage.APPLICATION_CONCERN__DOMAINS);
        }
        return this.domains;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.APPLICATION_CONCERN__CONTROLLERS:
                return ((InternalEList<?>) this.getControllers()).basicRemove(otherEnd, msgs);
            case PapayaPackage.APPLICATION_CONCERN__SERVICES:
                return ((InternalEList<?>) this.getServices()).basicRemove(otherEnd, msgs);
            case PapayaPackage.APPLICATION_CONCERN__EVENTS:
                return ((InternalEList<?>) this.getEvents()).basicRemove(otherEnd, msgs);
            case PapayaPackage.APPLICATION_CONCERN__COMMANDS:
                return ((InternalEList<?>) this.getCommands()).basicRemove(otherEnd, msgs);
            case PapayaPackage.APPLICATION_CONCERN__QUERIES:
                return ((InternalEList<?>) this.getQueries()).basicRemove(otherEnd, msgs);
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
            case PapayaPackage.APPLICATION_CONCERN__CONTROLLERS:
                return this.getControllers();
            case PapayaPackage.APPLICATION_CONCERN__SERVICES:
                return this.getServices();
            case PapayaPackage.APPLICATION_CONCERN__EVENTS:
                return this.getEvents();
            case PapayaPackage.APPLICATION_CONCERN__COMMANDS:
                return this.getCommands();
            case PapayaPackage.APPLICATION_CONCERN__QUERIES:
                return this.getQueries();
            case PapayaPackage.APPLICATION_CONCERN__DOMAINS:
                return this.getDomains();
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
            case PapayaPackage.APPLICATION_CONCERN__CONTROLLERS:
                this.getControllers().clear();
                this.getControllers().addAll((Collection<? extends Controller>) newValue);
                return;
            case PapayaPackage.APPLICATION_CONCERN__SERVICES:
                this.getServices().clear();
                this.getServices().addAll((Collection<? extends Service>) newValue);
                return;
            case PapayaPackage.APPLICATION_CONCERN__EVENTS:
                this.getEvents().clear();
                this.getEvents().addAll((Collection<? extends Event>) newValue);
                return;
            case PapayaPackage.APPLICATION_CONCERN__COMMANDS:
                this.getCommands().clear();
                this.getCommands().addAll((Collection<? extends Command>) newValue);
                return;
            case PapayaPackage.APPLICATION_CONCERN__QUERIES:
                this.getQueries().clear();
                this.getQueries().addAll((Collection<? extends Query>) newValue);
                return;
            case PapayaPackage.APPLICATION_CONCERN__DOMAINS:
                this.getDomains().clear();
                this.getDomains().addAll((Collection<? extends Domain>) newValue);
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
            case PapayaPackage.APPLICATION_CONCERN__CONTROLLERS:
                this.getControllers().clear();
                return;
            case PapayaPackage.APPLICATION_CONCERN__SERVICES:
                this.getServices().clear();
                return;
            case PapayaPackage.APPLICATION_CONCERN__EVENTS:
                this.getEvents().clear();
                return;
            case PapayaPackage.APPLICATION_CONCERN__COMMANDS:
                this.getCommands().clear();
                return;
            case PapayaPackage.APPLICATION_CONCERN__QUERIES:
                this.getQueries().clear();
                return;
            case PapayaPackage.APPLICATION_CONCERN__DOMAINS:
                this.getDomains().clear();
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
            case PapayaPackage.APPLICATION_CONCERN__CONTROLLERS:
                return this.controllers != null && !this.controllers.isEmpty();
            case PapayaPackage.APPLICATION_CONCERN__SERVICES:
                return this.services != null && !this.services.isEmpty();
            case PapayaPackage.APPLICATION_CONCERN__EVENTS:
                return this.events != null && !this.events.isEmpty();
            case PapayaPackage.APPLICATION_CONCERN__COMMANDS:
                return this.commands != null && !this.commands.isEmpty();
            case PapayaPackage.APPLICATION_CONCERN__QUERIES:
                return this.queries != null && !this.queries.isEmpty();
            case PapayaPackage.APPLICATION_CONCERN__DOMAINS:
                return this.domains != null && !this.domains.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // ApplicationConcernImpl
