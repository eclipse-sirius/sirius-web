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

import java.util.List;

import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.web.papaya.factories.api.IEObjectIndexer;

/**
 * Used to create the spring framework project.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class SpringFrameworkProjectFactory {

    public Project create(IEObjectIndexer eObjectIndexer) {
        var springFramework = PapayaFactory.eINSTANCE.createProject();
        springFramework.setName("Spring Framework");

        var springFrameworkComponents = List.of(this.springBeans(), this.springContext(), this.springCore(), this.springTx(), this.springWeb(), this.springWebsocket());
        springFramework.getComponents().addAll(springFrameworkComponents);

        eObjectIndexer.index(springFramework);

        return springFramework;
    }

    private Component springBeans() {
        var springBeans = PapayaFactory.eINSTANCE.createComponent();
        springBeans.setName("spring-beans");
        springBeans.getPackages().add(this.orgSpringFrameworkBeans());
        return springBeans;
    }

    private Package orgSpringFrameworkBeans() {
        var orgSpringFrameworkBeans = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkBeans.setName("org.springframework.beans");
        orgSpringFrameworkBeans.getPackages().add(this.orgSpringFrameworkBeansFactory());

        return orgSpringFrameworkBeans;
    }

    private Package orgSpringFrameworkBeansFactory() {
        var orgSpringFrameworkBeansFactory = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkBeansFactory.setName("factory");
        orgSpringFrameworkBeansFactory.getPackages().add(this.orgSpringFrameworkBeansFactoryAnnotation());

        return orgSpringFrameworkBeansFactory;
    }

    private Package orgSpringFrameworkBeansFactoryAnnotation() {
        var autowiredAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        autowiredAnnotation.setName("Autowired");

        var qualifierAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        qualifierAnnotation.setName("Qualifier");

        var valueAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        valueAnnotation.setName("Value");

        var types = List.of(autowiredAnnotation, qualifierAnnotation, valueAnnotation);

        var orgSpringFrameworkBeansFactoryAnnotation = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkBeansFactoryAnnotation.setName("annotation");
        orgSpringFrameworkBeansFactoryAnnotation.getTypes().addAll(types);
        return orgSpringFrameworkBeansFactoryAnnotation;
    }

    private Component springContext() {
        var springContext = PapayaFactory.eINSTANCE.createComponent();
        springContext.setName("spring-context");

        var orgSpringFramework = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFramework.setName("org.springframework");
        orgSpringFramework.getPackages().add(this.orgSpringFrameworkContext());
        orgSpringFramework.getPackages().add(this.orgSpringFrameworkStereotype());

        springContext.getPackages().add(orgSpringFramework);
        return springContext;
    }

    private Package orgSpringFrameworkContext() {
        var applicationContextInterface = PapayaFactory.eINSTANCE.createInterface();
        applicationContextInterface.setName("ApplicationContext");

        var applicationEventPublisherInterface = PapayaFactory.eINSTANCE.createInterface();
        applicationEventPublisherInterface.setName("ApplicationEventPublisher");

        var types = List.of(applicationContextInterface, applicationEventPublisherInterface);

        var orgSpringFrameworkContext = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkContext.setName("context");
        orgSpringFrameworkContext.getTypes().addAll(types);
        orgSpringFrameworkContext.getPackages().add(this.orgSpringFrameworkContextAnnotation());
        return orgSpringFrameworkContext;
    }

    private Package orgSpringFrameworkContextAnnotation() {
        var beanAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        beanAnnotation.setName("Bean");

        var conditionalAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        conditionalAnnotation.setName("Conditional");

        var configurationAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        configurationAnnotation.setName("Configuration");

        var types = List.of(beanAnnotation, conditionalAnnotation, configurationAnnotation);

        var orgSpringFrameworkContextAnnotation = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkContextAnnotation.setName("annotation");
        orgSpringFrameworkContextAnnotation.getTypes().addAll(types);

        return orgSpringFrameworkContextAnnotation;
    }

    private Package orgSpringFrameworkStereotype() {
        var componentAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        componentAnnotation.setName("Component");

        var controllerAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        controllerAnnotation.setName("Controller");

        var repositoryAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        repositoryAnnotation.setName("Repository");

        var serviceAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        serviceAnnotation.setName("Service");

        var types = List.of(componentAnnotation, controllerAnnotation, repositoryAnnotation, serviceAnnotation);

        var orgSpringFrameworkStereotype = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkStereotype.setName("stereotype");
        orgSpringFrameworkStereotype.getTypes().addAll(types);

        return orgSpringFrameworkStereotype;
    }

    private Component springCore() {
        var orgSpringFramework = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFramework.setName("org.springframework");
        orgSpringFramework.getPackages().add(this.orgSpringFrameworkCore());

        var springCore = PapayaFactory.eINSTANCE.createComponent();
        springCore.setName("spring-core");
        springCore.getPackages().add(orgSpringFramework);
        return springCore;
    }

    private Package orgSpringFrameworkCore() {
        var orgSpringFrameworkCore = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkCore.setName("core");
        orgSpringFrameworkCore.getPackages().add(this.orgSpringFrameworkCoreIo());
        return orgSpringFrameworkCore;
    }

    private Package orgSpringFrameworkCoreIo() {
        var inputStreamResourceInterface = PapayaFactory.eINSTANCE.createInterface();
        inputStreamResourceInterface.setName("InputStreamResource");

        var resourceInterface = PapayaFactory.eINSTANCE.createInterface();
        resourceInterface.setName("Resource");

        var abstractResourceClass = PapayaFactory.eINSTANCE.createClass();
        abstractResourceClass.setName("AbstractResource");
        abstractResourceClass.setAbstract(true);

        var abstractFileResolvingResourceClass = PapayaFactory.eINSTANCE.createClass();
        abstractFileResolvingResourceClass.setName("AbstractFileResolvingResource");
        abstractFileResolvingResourceClass.setAbstract(true);

        var classPathResourceClass = PapayaFactory.eINSTANCE.createClass();
        classPathResourceClass.setName("ClassPathResource");

        var types = List.of(inputStreamResourceInterface, resourceInterface, abstractResourceClass, abstractFileResolvingResourceClass, classPathResourceClass);

        var orgSpringFrameworkCoreIo = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkCoreIo.setName("io");
        orgSpringFrameworkCoreIo.getTypes().addAll(types);
        return orgSpringFrameworkCoreIo;
    }

    private Component springTx() {
        var orgSpringFramework = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFramework.setName("org.springframework");
        orgSpringFramework.getPackages().add(this.orgSpringFrameworkTransaction());

        var springTx = PapayaFactory.eINSTANCE.createComponent();
        springTx.setName("spring-tx");
        springTx.getPackages().add(orgSpringFramework);
        return springTx;
    }

    private Package orgSpringFrameworkTransaction() {
        var orgSpringFrameworkTransaction = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkTransaction.setName("transaction");
        orgSpringFrameworkTransaction.getPackages().addAll(List.of(this.orgSpringFrameworkTransactionAnnotation(), this.orgSpringFrameworkTransactionEvent()));
        return orgSpringFrameworkTransaction;
    }

    private Package orgSpringFrameworkTransactionAnnotation() {
        var transactionalAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        transactionalAnnotation.setName("Transactional");

        var types = List.of(transactionalAnnotation);

        var orgSpringFrameworkTransactionAnnotation = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkTransactionAnnotation.setName("annotation");
        orgSpringFrameworkTransactionAnnotation.getTypes().addAll(types);
        return orgSpringFrameworkTransactionAnnotation;
    }

    private Package orgSpringFrameworkTransactionEvent() {
        var transactionalEventListenerAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        transactionalEventListenerAnnotation.setName("TransactionalEventListener");

        var types = List.of(transactionalEventListenerAnnotation);

        var orgSpringFrameworkTransactionEvent = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkTransactionEvent.setName("event");
        orgSpringFrameworkTransactionEvent.getTypes().addAll(types);
        return orgSpringFrameworkTransactionEvent;
    }

    private Component springWeb() {
        var orgSpringFramework = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFramework.setName("org.springframework");
        orgSpringFramework.getPackages().addAll(List.of(this.orgSpringFrameworkHttp(), this.orgSpringFrameworkWeb()));

        var springWeb = PapayaFactory.eINSTANCE.createComponent();
        springWeb.setName("spring-web");
        springWeb.getPackages().add(orgSpringFramework);
        return springWeb;
    }

    private Package orgSpringFrameworkHttp() {
        var httpEntityTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        httpEntityTTypeParameter.setName("T");
        var httpEntityClass = PapayaFactory.eINSTANCE.createClass();
        httpEntityClass.setName("HttpEntity");
        httpEntityClass.getTypeParameters().add(httpEntityTTypeParameter);

        var httpHeadersClass = PapayaFactory.eINSTANCE.createClass();
        httpHeadersClass.setName("HttpHeaders");

        var httpStatusEnum = PapayaFactory.eINSTANCE.createEnum();
        httpStatusEnum.setName("HttpStatus");

        var httpStatusCodeInterface = PapayaFactory.eINSTANCE.createInterface();
        httpStatusCodeInterface.setName("HttpStatusCode");

        var mediaTypeClass = PapayaFactory.eINSTANCE.createClass();
        mediaTypeClass.setName("MediaType");

        var requestEntityTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        requestEntityTTypeParameter.setName("T");
        var requestEntityClass = PapayaFactory.eINSTANCE.createClass();
        requestEntityClass.setName("RequestEntity");
        requestEntityClass.getTypeParameters().add(requestEntityTTypeParameter);

        var responseEntityTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        responseEntityTTypeParameter.setName("T");
        var responseEntityClass = PapayaFactory.eINSTANCE.createClass();
        responseEntityClass.setName("ResponseEntity");
        responseEntityClass.getTypeParameters().add(responseEntityTTypeParameter);

        var types = List.of(httpEntityClass, httpHeadersClass, httpStatusEnum, httpStatusCodeInterface, mediaTypeClass, requestEntityClass, responseEntityClass);

        var orgSpringFrameworkHttp = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkHttp.setName("http");
        orgSpringFrameworkHttp.getTypes().addAll(types);
        return orgSpringFrameworkHttp;
    }

    private Package orgSpringFrameworkWeb() {
        var orgSpringFrameworkWeb = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkWeb.setName("web");
        orgSpringFrameworkWeb.getPackages().add(this.orgSpringFrameworkWebBind());
        return orgSpringFrameworkWeb;
    }

    private Package orgSpringFrameworkWebBind() {
        var orgSpringFrameworkWebBind = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkWebBind.setName("bind");
        orgSpringFrameworkWebBind.getPackages().add(this.orgSpringFrameworkWebBindAnnotation());
        return orgSpringFrameworkWebBind;
    }

    private Package orgSpringFrameworkWebBindAnnotation() {
        var getMappingAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        getMappingAnnotation.setName("GetMapping");

        var pathVariableAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        pathVariableAnnotation.setName("PathVariable");

        var requestMappingAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        requestMappingAnnotation.setName("RequestMapping");

        var responseBodyAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        responseBodyAnnotation.setName("ResponseBody");

        var types = List.of(getMappingAnnotation, pathVariableAnnotation, requestMappingAnnotation, responseBodyAnnotation);

        var orgSpringFrameworkWebBindAnnotation = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkWebBindAnnotation.setName("annotation");
        orgSpringFrameworkWebBindAnnotation.getTypes().addAll(types);
        return orgSpringFrameworkWebBindAnnotation;
    }

    private Component springWebsocket() {
        var webSocketHandlerInterface = PapayaFactory.eINSTANCE.createInterface();
        webSocketHandlerInterface.setName("WebSocketHandler");

        var webSocketMessageTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        webSocketMessageTTypeParameter.setName("T");
        var webSocketMessageInterface = PapayaFactory.eINSTANCE.createInterface();
        webSocketMessageInterface.setName("WebSocketMessage");
        webSocketMessageInterface.getTypeParameters().add(webSocketMessageTTypeParameter);

        var webSocketSessionInterface = PapayaFactory.eINSTANCE.createInterface();
        webSocketSessionInterface.setName("WebSocketSession");

        var types = List.of(webSocketHandlerInterface, webSocketMessageInterface, webSocketSessionInterface);

        var orgSpringFrameworkWebSocket = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkWebSocket.setName("org.springframework.web.socket");
        orgSpringFrameworkWebSocket.getTypes().addAll(types);

        var springWebsocket = PapayaFactory.eINSTANCE.createComponent();
        springWebsocket.setName("spring-websocket");
        springWebsocket.getPackages().add(orgSpringFrameworkWebSocket);
        return springWebsocket;
    }

    public void link(IEObjectIndexer eObjectIndexer) {
        var springBeansComponent = eObjectIndexer.getComponent("spring-beans");
        var springContextComponent = eObjectIndexer.getComponent("spring-context");
        var springCoreComponent = eObjectIndexer.getComponent("spring-core");
        var springTxComponent = eObjectIndexer.getComponent("spring-tx");
        var springWebComponent = eObjectIndexer.getComponent("spring-web");
        var springWebSocketComponent = eObjectIndexer.getComponent("spring-websocket");

        springBeansComponent.getDependencies().add(springCoreComponent);
        springWebComponent.getDependencies().addAll(List.of(springBeansComponent, springCoreComponent));
        springContextComponent.getDependencies().addAll(List.of(springBeansComponent, springCoreComponent));
        springTxComponent.getDependencies().addAll(List.of(springBeansComponent, springCoreComponent));
        springWebSocketComponent.getDependencies().addAll(List.of(springContextComponent, springCoreComponent, springWebComponent));
    }
}
