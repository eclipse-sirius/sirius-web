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
package org.eclipse.sirius.components.papaya.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.papaya.AnnotableElement;
import org.eclipse.sirius.components.papaya.Annotation;
import org.eclipse.sirius.components.papaya.AnnotationField;
import org.eclipse.sirius.components.papaya.Attribute;
import org.eclipse.sirius.components.papaya.Classifier;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.ComponentExchange;
import org.eclipse.sirius.components.papaya.ComponentPort;
import org.eclipse.sirius.components.papaya.Constructor;
import org.eclipse.sirius.components.papaya.Contribution;
import org.eclipse.sirius.components.papaya.DataType;
import org.eclipse.sirius.components.papaya.EnumLiteral;
import org.eclipse.sirius.components.papaya.GenericType;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.InterfaceImplementation;
import org.eclipse.sirius.components.papaya.Iteration;
import org.eclipse.sirius.components.papaya.ModelElement;
import org.eclipse.sirius.components.papaya.NamedElement;
import org.eclipse.sirius.components.papaya.Operation;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Parameter;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.papaya.ProvidedService;
import org.eclipse.sirius.components.papaya.RecordComponent;
import org.eclipse.sirius.components.papaya.RequiredService;
import org.eclipse.sirius.components.papaya.Tag;
import org.eclipse.sirius.components.papaya.Task;
import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.components.papaya.TypeParameter;
import org.eclipse.sirius.components.papaya.TypedElement;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage
 * @generated
 */
public class PapayaSwitch<T> extends Switch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static PapayaPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public PapayaSwitch() {
        if (modelPackage == null) {
            modelPackage = PapayaPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param ePackage
     *            the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
     * result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case PapayaPackage.MODEL_ELEMENT: {
                ModelElement modelElement = (ModelElement) theEObject;
                T result = this.caseModelElement(modelElement);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.TAG: {
                Tag tag = (Tag) theEObject;
                T result = this.caseTag(tag);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.NAMED_ELEMENT: {
                NamedElement namedElement = (NamedElement) theEObject;
                T result = this.caseNamedElement(namedElement);
                if (result == null)
                    result = this.caseModelElement(namedElement);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.PROJECT: {
                Project project = (Project) theEObject;
                T result = this.caseProject(project);
                if (result == null)
                    result = this.caseNamedElement(project);
                if (result == null)
                    result = this.caseModelElement(project);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.ITERATION: {
                Iteration iteration = (Iteration) theEObject;
                T result = this.caseIteration(iteration);
                if (result == null)
                    result = this.caseNamedElement(iteration);
                if (result == null)
                    result = this.caseModelElement(iteration);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.TASK: {
                Task task = (Task) theEObject;
                T result = this.caseTask(task);
                if (result == null)
                    result = this.caseNamedElement(task);
                if (result == null)
                    result = this.caseModelElement(task);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.CONTRIBUTION: {
                Contribution contribution = (Contribution) theEObject;
                T result = this.caseContribution(contribution);
                if (result == null)
                    result = this.caseNamedElement(contribution);
                if (result == null)
                    result = this.caseModelElement(contribution);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.COMPONENT: {
                Component component = (Component) theEObject;
                T result = this.caseComponent(component);
                if (result == null)
                    result = this.caseNamedElement(component);
                if (result == null)
                    result = this.caseModelElement(component);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.COMPONENT_PORT: {
                ComponentPort componentPort = (ComponentPort) theEObject;
                T result = this.caseComponentPort(componentPort);
                if (result == null)
                    result = this.caseNamedElement(componentPort);
                if (result == null)
                    result = this.caseModelElement(componentPort);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.COMPONENT_EXCHANGE: {
                ComponentExchange componentExchange = (ComponentExchange) theEObject;
                T result = this.caseComponentExchange(componentExchange);
                if (result == null)
                    result = this.caseNamedElement(componentExchange);
                if (result == null)
                    result = this.caseModelElement(componentExchange);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.PROVIDED_SERVICE: {
                ProvidedService providedService = (ProvidedService) theEObject;
                T result = this.caseProvidedService(providedService);
                if (result == null)
                    result = this.caseNamedElement(providedService);
                if (result == null)
                    result = this.caseModelElement(providedService);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.REQUIRED_SERVICE: {
                RequiredService requiredService = (RequiredService) theEObject;
                T result = this.caseRequiredService(requiredService);
                if (result == null)
                    result = this.caseNamedElement(requiredService);
                if (result == null)
                    result = this.caseModelElement(requiredService);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.ANNOTABLE_ELEMENT: {
                AnnotableElement annotableElement = (AnnotableElement) theEObject;
                T result = this.caseAnnotableElement(annotableElement);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.PACKAGE: {
                org.eclipse.sirius.components.papaya.Package package_ = (org.eclipse.sirius.components.papaya.Package) theEObject;
                T result = this.casePackage(package_);
                if (result == null)
                    result = this.caseNamedElement(package_);
                if (result == null)
                    result = this.caseAnnotableElement(package_);
                if (result == null)
                    result = this.caseModelElement(package_);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.TYPE: {
                Type type = (Type) theEObject;
                T result = this.caseType(type);
                if (result == null)
                    result = this.caseNamedElement(type);
                if (result == null)
                    result = this.caseAnnotableElement(type);
                if (result == null)
                    result = this.caseModelElement(type);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.TYPED_ELEMENT: {
                TypedElement typedElement = (TypedElement) theEObject;
                T result = this.caseTypedElement(typedElement);
                if (result == null)
                    result = this.caseNamedElement(typedElement);
                if (result == null)
                    result = this.caseAnnotableElement(typedElement);
                if (result == null)
                    result = this.caseModelElement(typedElement);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.GENERIC_TYPE: {
                GenericType genericType = (GenericType) theEObject;
                T result = this.caseGenericType(genericType);
                if (result == null)
                    result = this.caseModelElement(genericType);
                if (result == null)
                    result = this.caseAnnotableElement(genericType);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.ANNOTATION: {
                Annotation annotation = (Annotation) theEObject;
                T result = this.caseAnnotation(annotation);
                if (result == null)
                    result = this.caseType(annotation);
                if (result == null)
                    result = this.caseNamedElement(annotation);
                if (result == null)
                    result = this.caseAnnotableElement(annotation);
                if (result == null)
                    result = this.caseModelElement(annotation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.ANNOTATION_FIELD: {
                AnnotationField annotationField = (AnnotationField) theEObject;
                T result = this.caseAnnotationField(annotationField);
                if (result == null)
                    result = this.caseTypedElement(annotationField);
                if (result == null)
                    result = this.caseNamedElement(annotationField);
                if (result == null)
                    result = this.caseAnnotableElement(annotationField);
                if (result == null)
                    result = this.caseModelElement(annotationField);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.CLASSIFIER: {
                Classifier classifier = (Classifier) theEObject;
                T result = this.caseClassifier(classifier);
                if (result == null)
                    result = this.caseType(classifier);
                if (result == null)
                    result = this.caseNamedElement(classifier);
                if (result == null)
                    result = this.caseAnnotableElement(classifier);
                if (result == null)
                    result = this.caseModelElement(classifier);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.TYPE_PARAMETER: {
                TypeParameter typeParameter = (TypeParameter) theEObject;
                T result = this.caseTypeParameter(typeParameter);
                if (result == null)
                    result = this.caseNamedElement(typeParameter);
                if (result == null)
                    result = this.caseModelElement(typeParameter);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.INTERFACE: {
                Interface interface_ = (Interface) theEObject;
                T result = this.caseInterface(interface_);
                if (result == null)
                    result = this.caseClassifier(interface_);
                if (result == null)
                    result = this.caseType(interface_);
                if (result == null)
                    result = this.caseNamedElement(interface_);
                if (result == null)
                    result = this.caseAnnotableElement(interface_);
                if (result == null)
                    result = this.caseModelElement(interface_);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.INTERFACE_IMPLEMENTATION: {
                InterfaceImplementation interfaceImplementation = (InterfaceImplementation) theEObject;
                T result = this.caseInterfaceImplementation(interfaceImplementation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.CLASS: {
                org.eclipse.sirius.components.papaya.Class class_ = (org.eclipse.sirius.components.papaya.Class) theEObject;
                T result = this.caseClass(class_);
                if (result == null)
                    result = this.caseClassifier(class_);
                if (result == null)
                    result = this.caseInterfaceImplementation(class_);
                if (result == null)
                    result = this.caseType(class_);
                if (result == null)
                    result = this.caseNamedElement(class_);
                if (result == null)
                    result = this.caseAnnotableElement(class_);
                if (result == null)
                    result = this.caseModelElement(class_);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.CONSTRUCTOR: {
                Constructor constructor = (Constructor) theEObject;
                T result = this.caseConstructor(constructor);
                if (result == null)
                    result = this.caseAnnotableElement(constructor);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.ATTRIBUTE: {
                Attribute attribute = (Attribute) theEObject;
                T result = this.caseAttribute(attribute);
                if (result == null)
                    result = this.caseTypedElement(attribute);
                if (result == null)
                    result = this.caseNamedElement(attribute);
                if (result == null)
                    result = this.caseAnnotableElement(attribute);
                if (result == null)
                    result = this.caseModelElement(attribute);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.OPERATION: {
                Operation operation = (Operation) theEObject;
                T result = this.caseOperation(operation);
                if (result == null)
                    result = this.caseTypedElement(operation);
                if (result == null)
                    result = this.caseNamedElement(operation);
                if (result == null)
                    result = this.caseAnnotableElement(operation);
                if (result == null)
                    result = this.caseModelElement(operation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.PARAMETER: {
                Parameter parameter = (Parameter) theEObject;
                T result = this.caseParameter(parameter);
                if (result == null)
                    result = this.caseTypedElement(parameter);
                if (result == null)
                    result = this.caseNamedElement(parameter);
                if (result == null)
                    result = this.caseAnnotableElement(parameter);
                if (result == null)
                    result = this.caseModelElement(parameter);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.RECORD: {
                org.eclipse.sirius.components.papaya.Record record = (org.eclipse.sirius.components.papaya.Record) theEObject;
                T result = this.caseRecord(record);
                if (result == null)
                    result = this.caseClassifier(record);
                if (result == null)
                    result = this.caseInterfaceImplementation(record);
                if (result == null)
                    result = this.caseType(record);
                if (result == null)
                    result = this.caseNamedElement(record);
                if (result == null)
                    result = this.caseAnnotableElement(record);
                if (result == null)
                    result = this.caseModelElement(record);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.RECORD_COMPONENT: {
                RecordComponent recordComponent = (RecordComponent) theEObject;
                T result = this.caseRecordComponent(recordComponent);
                if (result == null)
                    result = this.caseTypedElement(recordComponent);
                if (result == null)
                    result = this.caseNamedElement(recordComponent);
                if (result == null)
                    result = this.caseAnnotableElement(recordComponent);
                if (result == null)
                    result = this.caseModelElement(recordComponent);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.DATA_TYPE: {
                DataType dataType = (DataType) theEObject;
                T result = this.caseDataType(dataType);
                if (result == null)
                    result = this.caseType(dataType);
                if (result == null)
                    result = this.caseNamedElement(dataType);
                if (result == null)
                    result = this.caseAnnotableElement(dataType);
                if (result == null)
                    result = this.caseModelElement(dataType);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.ENUM: {
                org.eclipse.sirius.components.papaya.Enum enum_ = (org.eclipse.sirius.components.papaya.Enum) theEObject;
                T result = this.caseEnum(enum_);
                if (result == null)
                    result = this.caseType(enum_);
                if (result == null)
                    result = this.caseNamedElement(enum_);
                if (result == null)
                    result = this.caseAnnotableElement(enum_);
                if (result == null)
                    result = this.caseModelElement(enum_);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapayaPackage.ENUM_LITERAL: {
                EnumLiteral enumLiteral = (EnumLiteral) theEObject;
                T result = this.caseEnumLiteral(enumLiteral);
                if (result == null)
                    result = this.caseNamedElement(enumLiteral);
                if (result == null)
                    result = this.caseAnnotableElement(enumLiteral);
                if (result == null)
                    result = this.caseModelElement(enumLiteral);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            default:
                return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Model Element</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Model Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseModelElement(ModelElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Tag</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Tag</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTag(Tag object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedElement(NamedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Project</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Project</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProject(Project object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Iteration</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Iteration</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIteration(Iteration object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTask(Task object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Contribution</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Contribution</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseContribution(Contribution object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Component</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Component</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseComponent(Component object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Component Port</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Component Port</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseComponentPort(ComponentPort object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Component Exchange</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Component Exchange</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseComponentExchange(ComponentExchange object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Provided Service</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Provided Service</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProvidedService(ProvidedService object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Required Service</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Required Service</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRequiredService(RequiredService object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Annotable Element</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Annotable Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAnnotableElement(AnnotableElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Package</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Package</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePackage(org.eclipse.sirius.components.papaya.Package object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Type</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseType(Type object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Typed Element</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Typed Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTypedElement(TypedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Generic Type</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Generic Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGenericType(GenericType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Annotation</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Annotation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAnnotation(Annotation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Annotation Field</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Annotation Field</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAnnotationField(AnnotationField object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Classifier</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Classifier</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseClassifier(Classifier object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Type Parameter</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Type Parameter</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTypeParameter(TypeParameter object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Interface</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Interface</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInterface(Interface object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Interface Implementation</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Interface Implementation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInterfaceImplementation(InterfaceImplementation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Class</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Class</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseClass(org.eclipse.sirius.components.papaya.Class object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Constructor</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Constructor</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConstructor(Constructor object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Attribute</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Attribute</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAttribute(Attribute object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operation</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperation(Operation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParameter(Parameter object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Record</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Record</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRecord(org.eclipse.sirius.components.papaya.Record object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Record Component</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Record Component</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRecordComponent(RecordComponent object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Data Type</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Data Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDataType(DataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Enum</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Enum</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEnum(org.eclipse.sirius.components.papaya.Enum object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Enum Literal</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Enum Literal</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEnumLiteral(EnumLiteral object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
     * anyway. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} // PapayaSwitch
