/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.representations;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rendering service used to navigate recursively of on the elements to render.
 *
 * @author sbegaudeau
 */
public class BaseRenderer {

    private final IInstancePropsValidator instancePropsValidator;

    private final IComponentPropsValidator componentPropsValidator;

    private final IElementFactory elementFactory;

    private final Logger logger = LoggerFactory.getLogger(BaseRenderer.class);

    public BaseRenderer(IInstancePropsValidator instancePropsValidator, IComponentPropsValidator componentPropsValidator, IElementFactory elementFactory) {
        this.instancePropsValidator = Objects.requireNonNull(instancePropsValidator);
        this.componentPropsValidator = Objects.requireNonNull(componentPropsValidator);
        this.elementFactory = Objects.requireNonNull(elementFactory);
    }

    /**
     * This method contains the core of the refresh algorithm used to render a tree of components.
     * <p>
     * The following description will use various nouns which may not be familiar for the reader. As such, we will
     * describe some of those key terms here. Our rendering solution will use three may concepts, Elements, Components
     * and Instances.
     * </p>
     * <p>
     * The instances are the final object rendered by the renderer, they are specific to a representation and they do
     * not have any specific characteristics. In our case, they will be plain old Java objects which will mostly be sent
     * to the consumers of our applications.
     * </p>
     * <p>
     * The components are a way to dynamically render part of the representation to create. They can be used to render
     * final objects or new components alike. Components should implements the {@link IComponent} interface.
     * </p>
     * <p>
     * The elements are the building blocks of the rendering strategy. They are used to declaratively indicate what
     * should be rendered and the properties of the element to render. Using a property named type, they can indicate
     * that we should render an instance or a component. An element with a Class<? extends IComponent> as its type will
     * indicate that we should render a component while an element with a String as its type indicate that we should
     * render an instance. Elements are created as instances of the {@link Element} class.
     * </p>
     * <p>
     * On top of that, the elements contain a field named props which will contain the properties of the element to
     * render. Those props can either represent the properties of a component or the properties of the instance to
     * create.
     * </p>
     * <p>
     * For additional details regarding the role of each of those concepts, have a look at the
     * <a href="https://reactjs.org/blog/2015/12/18/react-components-elements-and-instances.html">React description on
     * the subject</a>.
     * </p>
     * <p>
     * On top of those concepts, we have a special type of element with a String type named Fragment. Thanks to its
     * String type, a fragment should be processed like an instance to create but instead it will be handled with a
     * dedicated logic. A fragment is used when a component wants to return not only a new subtree of elements to render
     * but instead a forest with multiple roots. In such situation, the fragment can encapsulate the list of elements to
     * render by the component.
     * </p>
     * <p>
     * Additional details regarding the role of the fragments can be found in the
     * <a href="https://reactjs.org/docs/fragments.html">React documentation</a>.
     * </p>
     * <p>
     * The goal of the renderer is to navigate the given tree of elements and render recursively each of them. This very
     * basic algorithm is currently matching a simplified version of the old React "Stack" algorithm. It does not match
     * the behavior of the new and improved React "Fiber" algorithm.
     * </p>
     * <p>
     * The creation of the instances, from the elements with a String type, will be done by the representation-specific
     * code from the subclass of the abstract renderer. Before the creation of an instance or the execution of a
     * component, their properties will be validated by the subclass of the abstract render too in order to validate the
     * structure of elements.
     * </p>
     * <p>
     * This code will be dramatically refactored and improved in the future!
     * </p>
     *
     * @param element
     *            The element to render
     * @return The instance created
     */
    public Object renderElement(Element element) {
        Object instance = null;

        Object type = element.getType();
        IProps props = element.getProps();
        if (type instanceof String) {
            String instanceType = (String) type;
            if (Fragment.TYPE.equals(instanceType)) {
                instance = this.renderFragment(props);
            } else {
                instance = this.renderInstance(instanceType, props);
            }
        } else if (type instanceof Class<?>) {
            Class<?> componentType = (Class<?>) type;
            instance = this.renderComponent(componentType, props);
        }
        return instance;
    }

    /**
     * Used to render an instance using its various properties.
     * <p>
     * For that we will delegate the validation of the properties of the instance to create to the subclass of the
     * abstract renderer along with the actual creation of the instance since it depends on the representation.
     * </p>
     *
     * @param type
     *            The type of the instance
     * @param props
     *            The properties of the instance
     * @return The rendered instance
     */
    private Object renderInstance(String type, IProps props) {
        Object instance = null;
        if (this.instancePropsValidator.validateInstanceProps(type, props)) {
            List<Object> childInstances = new ArrayList<>();
            this.renderChildren(props, childInstances);
            instance = this.elementFactory.instantiateElement(type, props, childInstances);
        }
        return instance;
    }

    /**
     * Used to render a fragment using its children property.
     * <p>
     * Rendering a fragment will return a list of instances which will be created by recursively navigate the child
     * elements of the fragment and rendering them.
     * </p>
     *
     * @param props
     *            The properties of the fragment
     * @return The rendered instances
     */
    private List<Object> renderFragment(IProps props) {
        List<Object> instances = new ArrayList<>();
        if (this.validateFragmentProps(props)) {
            this.renderChildren(props, instances);
        }
        return instances;
    }

    private void renderChildren(IProps props, List<Object> instances) {
        for (Element childElement : props.getChildren()) {
            Object renderedChildren = this.renderElement(childElement);
            if (renderedChildren instanceof List<?>) {
                // In case a fragment contains another fragment
                instances.addAll((List<?>) renderedChildren);
            } else if (renderedChildren != null) {
                // In case a fragment contains a regular element
                instances.add(renderedChildren);
            }
        }
    }

    private boolean validateFragmentProps(IProps props) {
        return props instanceof FragmentProps && ((FragmentProps) props).getChildren() != null;
    }

    /**
     * Used to render a component using its properties.
     *
     * @param type
     *            The type of the component
     * @param props
     *            The properties of the component
     * @return The rendered instance
     */
    private Object renderComponent(Class<?> type, IProps props) {
        Object instance = null;
        if (IComponent.class.isAssignableFrom(type) && this.componentPropsValidator.validateComponentProps(type, props)) {
            try {
                Constructor<?> constructor = type.getConstructor(props.getClass());
                IComponent component = (IComponent) constructor.newInstance(props);
                Element renderedElement = component.render();
                if (renderedElement != null) {
                    instance = this.renderElement(renderedElement);
                }
            } catch (ReflectiveOperationException | SecurityException | IllegalArgumentException exception) {
                this.logger.warn(exception.getMessage(), exception);
            }
        }
        return instance;
    }

}
