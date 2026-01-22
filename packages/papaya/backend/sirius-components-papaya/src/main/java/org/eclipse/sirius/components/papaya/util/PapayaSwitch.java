/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.sirius.components.papaya.ApplicationConcern;
import org.eclipse.sirius.components.papaya.Attribute;
import org.eclipse.sirius.components.papaya.Channel;
import org.eclipse.sirius.components.papaya.Classifier;
import org.eclipse.sirius.components.papaya.Command;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.ComponentExchange;
import org.eclipse.sirius.components.papaya.ComponentPort;
import org.eclipse.sirius.components.papaya.Constructor;
import org.eclipse.sirius.components.papaya.Container;
import org.eclipse.sirius.components.papaya.ContainingLink;
import org.eclipse.sirius.components.papaya.Contribution;
import org.eclipse.sirius.components.papaya.Controller;
import org.eclipse.sirius.components.papaya.DataType;
import org.eclipse.sirius.components.papaya.Domain;
import org.eclipse.sirius.components.papaya.EnumLiteral;
import org.eclipse.sirius.components.papaya.Event;
import org.eclipse.sirius.components.papaya.Folder;
import org.eclipse.sirius.components.papaya.FolderElement;
import org.eclipse.sirius.components.papaya.GenericType;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.InterfaceImplementation;
import org.eclipse.sirius.components.papaya.Iteration;
import org.eclipse.sirius.components.papaya.Link;
import org.eclipse.sirius.components.papaya.Message;
import org.eclipse.sirius.components.papaya.MessageEmitter;
import org.eclipse.sirius.components.papaya.MessageListener;
import org.eclipse.sirius.components.papaya.ModelElement;
import org.eclipse.sirius.components.papaya.NamedElement;
import org.eclipse.sirius.components.papaya.Operation;
import org.eclipse.sirius.components.papaya.OperationalActivity;
import org.eclipse.sirius.components.papaya.OperationalActor;
import org.eclipse.sirius.components.papaya.OperationalCapability;
import org.eclipse.sirius.components.papaya.OperationalEntity;
import org.eclipse.sirius.components.papaya.OperationalInteraction;
import org.eclipse.sirius.components.papaya.OperationalProcess;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Parameter;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.papaya.ProvidedService;
import org.eclipse.sirius.components.papaya.Publication;
import org.eclipse.sirius.components.papaya.Query;
import org.eclipse.sirius.components.papaya.RecordComponent;
import org.eclipse.sirius.components.papaya.ReferencingLink;
import org.eclipse.sirius.components.papaya.Repository;
import org.eclipse.sirius.components.papaya.RequiredService;
import org.eclipse.sirius.components.papaya.Service;
import org.eclipse.sirius.components.papaya.Subscription;
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
 * @see org.eclipse.sirius.components.papaya.PapayaPackage
 * @generated
 */
public class PapayaSwitch<T> extends Switch<T> {
    /**
	 * The cached model package
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected static PapayaPackage modelPackage;

    /**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public PapayaSwitch() {
		if (modelPackage == null)
		{
			modelPackage = PapayaPackage.eINSTANCE;
		}
	}

    /**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

    /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID)
		{
			case PapayaPackage.MODEL_ELEMENT:
			{
				ModelElement modelElement = (ModelElement)theEObject;
				T result = caseModelElement(modelElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.TAG:
			{
				Tag tag = (Tag)theEObject;
				T result = caseTag(tag);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.LINK:
			{
				Link link = (Link)theEObject;
				T result = caseLink(link);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.REFERENCING_LINK:
			{
				ReferencingLink referencingLink = (ReferencingLink)theEObject;
				T result = caseReferencingLink(referencingLink);
				if (result == null) result = caseLink(referencingLink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.CONTAINING_LINK:
			{
				ContainingLink containingLink = (ContainingLink)theEObject;
				T result = caseContainingLink(containingLink);
				if (result == null) result = caseLink(containingLink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.NAMED_ELEMENT:
			{
				NamedElement namedElement = (NamedElement)theEObject;
				T result = caseNamedElement(namedElement);
				if (result == null) result = caseModelElement(namedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.CONTAINER:
			{
				Container container = (Container)theEObject;
				T result = caseContainer(container);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.FOLDER:
			{
				Folder folder = (Folder)theEObject;
				T result = caseFolder(folder);
				if (result == null) result = caseNamedElement(folder);
				if (result == null) result = caseContainer(folder);
				if (result == null) result = caseModelElement(folder);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.FOLDER_ELEMENT:
			{
				FolderElement folderElement = (FolderElement)theEObject;
				T result = caseFolderElement(folderElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.PROJECT:
			{
				Project project = (Project)theEObject;
				T result = caseProject(project);
				if (result == null) result = caseNamedElement(project);
				if (result == null) result = caseContainer(project);
				if (result == null) result = caseModelElement(project);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.OPERATIONAL_CAPABILITY:
			{
				OperationalCapability operationalCapability = (OperationalCapability)theEObject;
				T result = caseOperationalCapability(operationalCapability);
				if (result == null) result = caseNamedElement(operationalCapability);
				if (result == null) result = caseFolderElement(operationalCapability);
				if (result == null) result = caseModelElement(operationalCapability);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.OPERATIONAL_ENTITY:
			{
				OperationalEntity operationalEntity = (OperationalEntity)theEObject;
				T result = caseOperationalEntity(operationalEntity);
				if (result == null) result = caseNamedElement(operationalEntity);
				if (result == null) result = caseFolderElement(operationalEntity);
				if (result == null) result = caseModelElement(operationalEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.OPERATIONAL_ACTOR:
			{
				OperationalActor operationalActor = (OperationalActor)theEObject;
				T result = caseOperationalActor(operationalActor);
				if (result == null) result = caseNamedElement(operationalActor);
				if (result == null) result = caseFolderElement(operationalActor);
				if (result == null) result = caseModelElement(operationalActor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.OPERATIONAL_PROCESS:
			{
				OperationalProcess operationalProcess = (OperationalProcess)theEObject;
				T result = caseOperationalProcess(operationalProcess);
				if (result == null) result = caseNamedElement(operationalProcess);
				if (result == null) result = caseFolderElement(operationalProcess);
				if (result == null) result = caseModelElement(operationalProcess);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.OPERATIONAL_ACTIVITY:
			{
				OperationalActivity operationalActivity = (OperationalActivity)theEObject;
				T result = caseOperationalActivity(operationalActivity);
				if (result == null) result = caseNamedElement(operationalActivity);
				if (result == null) result = caseFolderElement(operationalActivity);
				if (result == null) result = caseModelElement(operationalActivity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.OPERATIONAL_INTERACTION:
			{
				OperationalInteraction operationalInteraction = (OperationalInteraction)theEObject;
				T result = caseOperationalInteraction(operationalInteraction);
				if (result == null) result = caseNamedElement(operationalInteraction);
				if (result == null) result = caseModelElement(operationalInteraction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.ITERATION:
			{
				Iteration iteration = (Iteration)theEObject;
				T result = caseIteration(iteration);
				if (result == null) result = caseNamedElement(iteration);
				if (result == null) result = caseFolderElement(iteration);
				if (result == null) result = caseModelElement(iteration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.TASK:
			{
				Task task = (Task)theEObject;
				T result = caseTask(task);
				if (result == null) result = caseNamedElement(task);
				if (result == null) result = caseFolderElement(task);
				if (result == null) result = caseModelElement(task);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.CONTRIBUTION:
			{
				Contribution contribution = (Contribution)theEObject;
				T result = caseContribution(contribution);
				if (result == null) result = caseNamedElement(contribution);
				if (result == null) result = caseFolderElement(contribution);
				if (result == null) result = caseModelElement(contribution);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.COMPONENT:
			{
				Component component = (Component)theEObject;
				T result = caseComponent(component);
				if (result == null) result = caseNamedElement(component);
				if (result == null) result = caseFolderElement(component);
				if (result == null) result = caseModelElement(component);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.COMPONENT_PORT:
			{
				ComponentPort componentPort = (ComponentPort)theEObject;
				T result = caseComponentPort(componentPort);
				if (result == null) result = caseNamedElement(componentPort);
				if (result == null) result = caseModelElement(componentPort);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.COMPONENT_EXCHANGE:
			{
				ComponentExchange componentExchange = (ComponentExchange)theEObject;
				T result = caseComponentExchange(componentExchange);
				if (result == null) result = caseNamedElement(componentExchange);
				if (result == null) result = caseModelElement(componentExchange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.PROVIDED_SERVICE:
			{
				ProvidedService providedService = (ProvidedService)theEObject;
				T result = caseProvidedService(providedService);
				if (result == null) result = caseNamedElement(providedService);
				if (result == null) result = caseModelElement(providedService);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.REQUIRED_SERVICE:
			{
				RequiredService requiredService = (RequiredService)theEObject;
				T result = caseRequiredService(requiredService);
				if (result == null) result = caseNamedElement(requiredService);
				if (result == null) result = caseModelElement(requiredService);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.ANNOTABLE_ELEMENT:
			{
				AnnotableElement annotableElement = (AnnotableElement)theEObject;
				T result = caseAnnotableElement(annotableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.PACKAGE:
			{
				org.eclipse.sirius.components.papaya.Package package_ = (org.eclipse.sirius.components.papaya.Package)theEObject;
				T result = casePackage(package_);
				if (result == null) result = caseNamedElement(package_);
				if (result == null) result = caseAnnotableElement(package_);
				if (result == null) result = caseFolderElement(package_);
				if (result == null) result = caseModelElement(package_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.TYPE:
			{
				Type type = (Type)theEObject;
				T result = caseType(type);
				if (result == null) result = caseNamedElement(type);
				if (result == null) result = caseAnnotableElement(type);
				if (result == null) result = caseModelElement(type);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.TYPED_ELEMENT:
			{
				TypedElement typedElement = (TypedElement)theEObject;
				T result = caseTypedElement(typedElement);
				if (result == null) result = caseNamedElement(typedElement);
				if (result == null) result = caseAnnotableElement(typedElement);
				if (result == null) result = caseModelElement(typedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.GENERIC_TYPE:
			{
				GenericType genericType = (GenericType)theEObject;
				T result = caseGenericType(genericType);
				if (result == null) result = caseModelElement(genericType);
				if (result == null) result = caseAnnotableElement(genericType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.ANNOTATION:
			{
				Annotation annotation = (Annotation)theEObject;
				T result = caseAnnotation(annotation);
				if (result == null) result = caseType(annotation);
				if (result == null) result = caseNamedElement(annotation);
				if (result == null) result = caseAnnotableElement(annotation);
				if (result == null) result = caseModelElement(annotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.ANNOTATION_FIELD:
			{
				AnnotationField annotationField = (AnnotationField)theEObject;
				T result = caseAnnotationField(annotationField);
				if (result == null) result = caseTypedElement(annotationField);
				if (result == null) result = caseNamedElement(annotationField);
				if (result == null) result = caseAnnotableElement(annotationField);
				if (result == null) result = caseModelElement(annotationField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.CLASSIFIER:
			{
				Classifier classifier = (Classifier)theEObject;
				T result = caseClassifier(classifier);
				if (result == null) result = caseType(classifier);
				if (result == null) result = caseNamedElement(classifier);
				if (result == null) result = caseAnnotableElement(classifier);
				if (result == null) result = caseModelElement(classifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.TYPE_PARAMETER:
			{
				TypeParameter typeParameter = (TypeParameter)theEObject;
				T result = caseTypeParameter(typeParameter);
				if (result == null) result = caseNamedElement(typeParameter);
				if (result == null) result = caseModelElement(typeParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.INTERFACE:
			{
				Interface interface_ = (Interface)theEObject;
				T result = caseInterface(interface_);
				if (result == null) result = caseClassifier(interface_);
				if (result == null) result = caseType(interface_);
				if (result == null) result = caseNamedElement(interface_);
				if (result == null) result = caseAnnotableElement(interface_);
				if (result == null) result = caseModelElement(interface_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.INTERFACE_IMPLEMENTATION:
			{
				InterfaceImplementation interfaceImplementation = (InterfaceImplementation)theEObject;
				T result = caseInterfaceImplementation(interfaceImplementation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.CLASS:
			{
				org.eclipse.sirius.components.papaya.Class class_ = (org.eclipse.sirius.components.papaya.Class)theEObject;
				T result = caseClass(class_);
				if (result == null) result = caseClassifier(class_);
				if (result == null) result = caseInterfaceImplementation(class_);
				if (result == null) result = caseType(class_);
				if (result == null) result = caseNamedElement(class_);
				if (result == null) result = caseAnnotableElement(class_);
				if (result == null) result = caseModelElement(class_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.CONSTRUCTOR:
			{
				Constructor constructor = (Constructor)theEObject;
				T result = caseConstructor(constructor);
				if (result == null) result = caseAnnotableElement(constructor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.ATTRIBUTE:
			{
				Attribute attribute = (Attribute)theEObject;
				T result = caseAttribute(attribute);
				if (result == null) result = caseTypedElement(attribute);
				if (result == null) result = caseNamedElement(attribute);
				if (result == null) result = caseAnnotableElement(attribute);
				if (result == null) result = caseModelElement(attribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.OPERATION:
			{
				Operation operation = (Operation)theEObject;
				T result = caseOperation(operation);
				if (result == null) result = caseTypedElement(operation);
				if (result == null) result = caseNamedElement(operation);
				if (result == null) result = caseAnnotableElement(operation);
				if (result == null) result = caseModelElement(operation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.PARAMETER:
			{
				Parameter parameter = (Parameter)theEObject;
				T result = caseParameter(parameter);
				if (result == null) result = caseTypedElement(parameter);
				if (result == null) result = caseNamedElement(parameter);
				if (result == null) result = caseAnnotableElement(parameter);
				if (result == null) result = caseModelElement(parameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.RECORD:
			{
				org.eclipse.sirius.components.papaya.Record record = (org.eclipse.sirius.components.papaya.Record)theEObject;
				T result = caseRecord(record);
				if (result == null) result = caseClassifier(record);
				if (result == null) result = caseInterfaceImplementation(record);
				if (result == null) result = caseType(record);
				if (result == null) result = caseNamedElement(record);
				if (result == null) result = caseAnnotableElement(record);
				if (result == null) result = caseModelElement(record);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.RECORD_COMPONENT:
			{
				RecordComponent recordComponent = (RecordComponent)theEObject;
				T result = caseRecordComponent(recordComponent);
				if (result == null) result = caseTypedElement(recordComponent);
				if (result == null) result = caseNamedElement(recordComponent);
				if (result == null) result = caseAnnotableElement(recordComponent);
				if (result == null) result = caseModelElement(recordComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.DATA_TYPE:
			{
				DataType dataType = (DataType)theEObject;
				T result = caseDataType(dataType);
				if (result == null) result = caseType(dataType);
				if (result == null) result = caseNamedElement(dataType);
				if (result == null) result = caseAnnotableElement(dataType);
				if (result == null) result = caseModelElement(dataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.ENUM:
			{
				org.eclipse.sirius.components.papaya.Enum enum_ = (org.eclipse.sirius.components.papaya.Enum)theEObject;
				T result = caseEnum(enum_);
				if (result == null) result = caseType(enum_);
				if (result == null) result = caseNamedElement(enum_);
				if (result == null) result = caseAnnotableElement(enum_);
				if (result == null) result = caseModelElement(enum_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.ENUM_LITERAL:
			{
				EnumLiteral enumLiteral = (EnumLiteral)theEObject;
				T result = caseEnumLiteral(enumLiteral);
				if (result == null) result = caseNamedElement(enumLiteral);
				if (result == null) result = caseAnnotableElement(enumLiteral);
				if (result == null) result = caseModelElement(enumLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.APPLICATION_CONCERN:
			{
				ApplicationConcern applicationConcern = (ApplicationConcern)theEObject;
				T result = caseApplicationConcern(applicationConcern);
				if (result == null) result = caseNamedElement(applicationConcern);
				if (result == null) result = caseFolderElement(applicationConcern);
				if (result == null) result = caseModelElement(applicationConcern);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.CONTROLLER:
			{
				Controller controller = (Controller)theEObject;
				T result = caseController(controller);
				if (result == null) result = caseNamedElement(controller);
				if (result == null) result = caseMessageEmitter(controller);
				if (result == null) result = caseMessageListener(controller);
				if (result == null) result = caseModelElement(controller);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.DOMAIN:
			{
				Domain domain = (Domain)theEObject;
				T result = caseDomain(domain);
				if (result == null) result = caseNamedElement(domain);
				if (result == null) result = caseFolderElement(domain);
				if (result == null) result = caseModelElement(domain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.SERVICE:
			{
				Service service = (Service)theEObject;
				T result = caseService(service);
				if (result == null) result = caseNamedElement(service);
				if (result == null) result = caseMessageListener(service);
				if (result == null) result = caseMessageEmitter(service);
				if (result == null) result = caseModelElement(service);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.MESSAGE_LISTENER:
			{
				MessageListener messageListener = (MessageListener)theEObject;
				T result = caseMessageListener(messageListener);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.MESSAGE_EMITTER:
			{
				MessageEmitter messageEmitter = (MessageEmitter)theEObject;
				T result = caseMessageEmitter(messageEmitter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.MESSAGE:
			{
				Message message = (Message)theEObject;
				T result = caseMessage(message);
				if (result == null) result = caseNamedElement(message);
				if (result == null) result = caseModelElement(message);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.EVENT:
			{
				Event event = (Event)theEObject;
				T result = caseEvent(event);
				if (result == null) result = caseMessage(event);
				if (result == null) result = caseNamedElement(event);
				if (result == null) result = caseModelElement(event);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.COMMAND:
			{
				Command command = (Command)theEObject;
				T result = caseCommand(command);
				if (result == null) result = caseMessage(command);
				if (result == null) result = caseNamedElement(command);
				if (result == null) result = caseModelElement(command);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.QUERY:
			{
				Query query = (Query)theEObject;
				T result = caseQuery(query);
				if (result == null) result = caseMessage(query);
				if (result == null) result = caseNamedElement(query);
				if (result == null) result = caseModelElement(query);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.REPOSITORY:
			{
				Repository repository = (Repository)theEObject;
				T result = caseRepository(repository);
				if (result == null) result = caseNamedElement(repository);
				if (result == null) result = caseModelElement(repository);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.CHANNEL:
			{
				Channel channel = (Channel)theEObject;
				T result = caseChannel(channel);
				if (result == null) result = caseNamedElement(channel);
				if (result == null) result = caseFolderElement(channel);
				if (result == null) result = caseModelElement(channel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.SUBSCRIPTION:
			{
				Subscription subscription = (Subscription)theEObject;
				T result = caseSubscription(subscription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PapayaPackage.PUBLICATION:
			{
				Publication publication = (Publication)theEObject;
				T result = casePublication(publication);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Model Element</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseModelElement(ModelElement object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Tag</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tag</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseTag(Tag object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Link</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseLink(Link object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Referencing Link</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Referencing Link</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseReferencingLink(ReferencingLink object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Containing Link</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Containing Link</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseContainingLink(ContainingLink object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseNamedElement(NamedElement object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Container</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseContainer(Container object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Folder</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Folder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseFolder(Folder object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Folder Element</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Folder Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFolderElement(FolderElement object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Project</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseProject(Project object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operational Capability</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operational Capability</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperationalCapability(OperationalCapability object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operational Entity</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operational Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperationalEntity(OperationalEntity object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operational Actor</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operational Actor</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperationalActor(OperationalActor object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operational Process</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operational Process</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperationalProcess(OperationalProcess object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operational Activity</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operational Activity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperationalActivity(OperationalActivity object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operational Interaction</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operational Interaction</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperationalInteraction(OperationalInteraction object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Iteration</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iteration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseIteration(Iteration object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Task</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Task</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseTask(Task object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Contribution</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Contribution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseContribution(Contribution object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Component</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
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
	 * Returns the result of interpreting the object as an instance of '<em>Package</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Package</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T casePackage(org.eclipse.sirius.components.papaya.Package object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseType(Type object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Typed Element</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseTypedElement(TypedElement object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseGenericType(GenericType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Annotation</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
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
	 * Returns the result of interpreting the object as an instance of '<em>Classifier</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
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
	 * Returns the result of interpreting the object as an instance of '<em>Interface</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
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
	 * Returns the result of interpreting the object as an instance of '<em>Class</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseClass(org.eclipse.sirius.components.papaya.Class object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Constructor</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constructor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseConstructor(Constructor object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseAttribute(Attribute object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Operation</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseOperation(Operation object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseParameter(Parameter object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Record</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
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
	 * Returns the result of interpreting the object as an instance of '<em>Data Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseDataType(DataType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Enum</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseEnum(org.eclipse.sirius.components.papaya.Enum object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Literal</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseEnumLiteral(EnumLiteral object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Application Concern</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Application Concern</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseApplicationConcern(ApplicationConcern object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Controller</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Controller</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseController(Controller object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Domain</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseDomain(Domain object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Service</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseService(Service object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Message Listener</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Message Listener</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMessageListener(MessageListener object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Message Emitter</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Message Emitter</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMessageEmitter(MessageEmitter object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Message</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Message</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseMessage(Message object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Event</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseEvent(Event object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Command</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Command</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseCommand(Command object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Query</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseQuery(Query object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Repository</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Repository</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseRepository(Repository object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Channel</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Channel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseChannel(Channel object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Subscription</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subscription</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseSubscription(Subscription object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Publication</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Publication</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T casePublication(Publication object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
     * anyway. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
    @Override
    public T defaultCase(EObject object) {
		return null;
	}

} // PapayaSwitch
