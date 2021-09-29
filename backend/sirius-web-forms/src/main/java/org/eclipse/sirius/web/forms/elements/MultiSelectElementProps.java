/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.forms.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.IProps;
import org.eclipse.sirius.web.forms.SelectOption;
import org.eclipse.sirius.web.representations.IStatus;

/**
 * The properties of the multi-select element.
 *
 * @author pcdavid
 * @author arichard
 */
@Immutable
public final class MultiSelectElementProps implements IProps {
    public static final String TYPE = "MultiSelect"; //$NON-NLS-1$

    private String id;

    private String label;

    private List<SelectOption> options;

    private List<String> values;

    private Function<List<String>, IStatus> newValuesHandler;

    private List<Element> children;

    private MultiSelectElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public List<SelectOption> getOptions() {
        return this.options;
    }

    public List<String> getValues() {
        return this.values;
    }

    public Function<List<String>, IStatus> getNewValuesHandler() {
        return this.newValuesHandler;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newMultiSelectElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, value: {3}, options:{4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.values, this.options);
    }

    /**
     * The builder of the select element props.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private List<SelectOption> options;

        private List<String> values;

        private Function<List<String>, IStatus> newValuesHandler;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder options(List<SelectOption> options) {
            this.options = Objects.requireNonNull(options);
            return this;
        }

        public Builder values(List<String> values) {
            this.values = Objects.requireNonNull(values);
            return this;
        }

        public Builder newValuesHandler(Function<List<String>, IStatus> newValuesHandler) {
            this.newValuesHandler = Objects.requireNonNull(newValuesHandler);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public MultiSelectElementProps build() {
            MultiSelectElementProps multiSelectElementProps = new MultiSelectElementProps();
            multiSelectElementProps.id = Objects.requireNonNull(this.id);
            multiSelectElementProps.label = Objects.requireNonNull(this.label);
            multiSelectElementProps.options = Objects.requireNonNull(this.options);
            multiSelectElementProps.values = List.copyOf(this.values);
            multiSelectElementProps.newValuesHandler = Objects.requireNonNull(this.newValuesHandler);
            multiSelectElementProps.children = Objects.requireNonNull(this.children);
            return multiSelectElementProps;
        }
    }
}
