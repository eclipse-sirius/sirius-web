/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.components.forms.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatObject;
import static org.eclipse.sirius.components.forms.tests.FormAssertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Checkbox;
import org.eclipse.sirius.components.forms.List;
import org.eclipse.sirius.components.forms.ListItem;
import org.eclipse.sirius.components.forms.Radio;
import org.eclipse.sirius.components.forms.RadioOption;
import org.eclipse.sirius.components.forms.Select;
import org.eclipse.sirius.components.forms.SelectOption;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.Textfield;

/**
 * Custom assertion class used to perform some tests on a widget.
 *
 * @author lfasani
 */
public class WidgetAssert extends AbstractAssert<WidgetAssert, AbstractWidget> {

    public WidgetAssert(AbstractWidget actual) {
        super(actual, WidgetAssert.class);
    }

    public WidgetAssert matches(AbstractWidget widget, IdPolicy idPolicy) {
        this.isNotNull();

        if (idPolicy == IdPolicy.WITH_ID) {
            assertThat(this.actual.getId()).isEqualTo(widget.getId());
        }

        if (this.actual instanceof List && widget instanceof List) {
            List actualList = (List) this.actual;
            List list = (List) widget;
            assertThat(actualList.getLabel()).isEqualTo(list.getLabel());
            assertThat(actualList.getItems().size()).isEqualTo(list.getItems().size());

            int size = actualList.getItems().size();
            for (int i = 0; i < size; i++) {
                ListItem actualListItem = actualList.getItems().get(i);
                ListItem listItem = list.getItems().get(i);
                assertThat(actualListItem).matches(listItem, idPolicy);
            }
        } else if (this.actual instanceof Checkbox && widget instanceof Checkbox) {
            Checkbox actualCheckbox = (Checkbox) this.actual;
            Checkbox checkbox = (Checkbox) widget;
            assertThat(actualCheckbox.getLabel()).isEqualTo(checkbox.getLabel());
            assertThat(actualCheckbox.isValue()).isEqualTo(checkbox.isValue());
        } else if (this.actual instanceof Radio && widget instanceof Radio) {
            this.assertRadio((Radio) this.actual, (Radio) widget, idPolicy);
        } else if (this.actual instanceof Select && widget instanceof Select) {
            this.assertSelect((Select) this.actual, (Select) widget, idPolicy);
        } else if (this.actual instanceof Textarea && widget instanceof Textarea) {
            Textarea actualTextarea = (Textarea) this.actual;
            Textarea textarea = (Textarea) widget;
            assertThat(actualTextarea.getLabel()).isEqualTo(textarea.getLabel());
            assertThat(actualTextarea.getValue()).isEqualTo(textarea.getValue());
        } else if (this.actual instanceof Textfield && widget instanceof Textfield) {
            Textfield actualTextfield = (Textfield) this.actual;
            Textfield textfield = (Textfield) widget;
            assertThat(actualTextfield.getLabel()).isEqualTo(textfield.getLabel());
            assertThat(actualTextfield.getValue()).isEqualTo(textfield.getValue());
        }

        return this;
    }

    private void assertSelect(Select actualSelect, Select select, IdPolicy idPolicy) {
        assertThat(actualSelect.getLabel()).isEqualTo(select.getLabel());
        assertThat(actualSelect.getOptions().size()).isEqualTo(select.getOptions().size());

        int size = actualSelect.getOptions().size();
        for (int i = 0; i < size; i++) {
            SelectOption actualSelectOption = actualSelect.getOptions().get(i);
            SelectOption selectOption = select.getOptions().get(i);

            assertThatObject(actualSelectOption).isNotNull();
            if (idPolicy == IdPolicy.WITH_ID) {
                assertThat(actualSelectOption.getId()).isEqualTo(selectOption.getId());
            }
            assertThat(actualSelectOption.getLabel()).isEqualTo(selectOption.getLabel());
        }
    }

    private void assertRadio(Radio actualRadio, Radio radio, IdPolicy idPolicy) {
        assertThat(actualRadio.getLabel()).isEqualTo(radio.getLabel());
        assertThat(actualRadio.getOptions().size()).isEqualTo(radio.getOptions().size());

        int size = actualRadio.getOptions().size();
        for (int i = 0; i < size; i++) {
            RadioOption actualRadioOption = actualRadio.getOptions().get(i);
            RadioOption radioOption = radio.getOptions().get(i);

            assertThatObject(actualRadioOption).isNotNull();
            if (idPolicy == IdPolicy.WITH_ID) {
                assertThat(actualRadioOption.getId()).isEqualTo(radioOption.getId());
            }
            assertThat(actualRadioOption.getLabel()).isEqualTo(radioOption.getLabel());
            assertThat(actualRadioOption.isSelected()).isEqualTo(radioOption.isSelected());
        }
    }

}
